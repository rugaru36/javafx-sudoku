package rug4ru.sudoku.presentation.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import rug4ru.sudoku.domain.Difficulty;
import rug4ru.sudoku.domain.GameProcess;
import rug4ru.sudoku.presentation.GuiComposer;

public class MainScreenController {
  public GuiComposer guiComposer = GuiComposer.getInstance();
  private boolean isGameInProgress = true;
  // public Difficulty.Level diffLevel = null;

  private GameProcess gameProcess = null;
  private List<List<Button>> buttonRowsList = null;

  private Integer selectedRow = null;
  private Integer selectedCol = null;
  GridPane numFieldGridPane = null;

  @FXML
  private Label statusLabel;
  @FXML
  private VBox vbox;

  @FXML
  protected void initialize() {
    buttonRowsList = new ArrayList<List<Button>>();
    Platform.runLater(() -> {
      updateStatus();
      drawNumField();
      dropSelection();
    });
  }

  public void initGameProcess(Difficulty.Level diffLevel) {
    if (diffLevel == null) {
      throw new NullPointerException("MainScreenController: diffLevel is null");
    }
    gameProcess = new GameProcess(diffLevel);
  }

  public void restart() throws IOException {
    guiComposer.openDifficultySelector();
  }

  public void dropSelection() {
    selectedCol = null;
    selectedRow = null;
    if (numFieldGridPane != null) {
      Platform.runLater(numFieldGridPane::requestFocus);
    }
  }

  public void onNewValue(int value) {
    boolean isValueCorrect = gameProcess.onNewValue(value, selectedRow, selectedCol);
    if (isValueCorrect) {
      Button selectedBtn = buttonRowsList.get(selectedRow).get(selectedCol);
      selectedBtn.setText(String.valueOf(gameProcess.getNumFieldValue(selectedRow, selectedCol)));
    }
    updateStatus();
  }

  private void updateStatus() {
    Difficulty.DifficultyData status = gameProcess.getCurrentStatus();
    isGameInProgress = status.unknownElements > 0 && status.mistakes > 0;
    String level = "Difficulty level: " + status.name;
    String toFill = "Left to fill: " + status.unknownElements;
    String mistakes = "Left mistakes: " + status.mistakes;
    statusLabel.setText(level + "\n" + toFill + "\n" + mistakes);
  }

  private void drawNumField() {
    int fieldSize = gameProcess.getNumFieldSize();
    int blocksNum = (int) Math.sqrt(fieldSize);
    int blocksSize = (int) fieldSize / blocksNum;

    numFieldGridPane = new GridPane();

    numFieldGridPane.setHgap(5);
    numFieldGridPane.setVgap(5);

    // generating blocks
    List<List<GridPane>> blocksGridPanes = new ArrayList<List<GridPane>>();
    for (int blockRow = 0; blockRow < blocksNum; blockRow++) {
      List<GridPane> rowGridPanes = new ArrayList<GridPane>();
      for (int blockCol = 0; blockCol < blocksNum; blockCol++) {
        GridPane blockGridPane = new GridPane();
        rowGridPanes.add(blockGridPane);
        numFieldGridPane.add(blockGridPane, blockCol, blockRow);
      }
      blocksGridPanes.add(rowGridPanes);
    }

    for (int elementRow = 0; elementRow < fieldSize; elementRow++) {
      int blockRow = (int) elementRow / blocksNum;
      List<GridPane> gridPanesRow = blocksGridPanes.get(blockRow);
      List<Button> rowButtons = new ArrayList<Button>();

      for (int elementCol = 0; elementCol < fieldSize; elementCol++) {
        int blockCol = (int) elementCol / blocksNum;
        GridPane blockGridPane = gridPanesRow.get(blockCol);

        Integer value = gameProcess.getNumFieldValue(elementRow, elementCol);
        String buttonText = value == null ? "  " : String.valueOf(value);
        Button newBtn = new Button(buttonText);

        final int r = elementRow;
        final int c = elementCol;
        newBtn.setOnAction(event -> {
          try {
            selectElement(r, c);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
        blockGridPane.add(newBtn, elementCol % blocksSize, elementRow % blocksSize);
        rowButtons.add(newBtn);
      }
      buttonRowsList.add(rowButtons);
    }
    vbox.getChildren().add(numFieldGridPane);
  }

  private void selectElement(int row, int col) throws IOException {
    boolean isActuallyUnknown = gameProcess.checkIsActuallyUnknown(row, col);
    if (!isActuallyUnknown || !isGameInProgress) {
      return;
    } else if (selectedRow != null && selectedCol != null) {
      return;
    }
    selectedRow = row;
    selectedCol = col;
    guiComposer.openInputScreen();
  }
}
