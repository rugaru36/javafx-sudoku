package rug4ru.sudoku.presentation.controllers;

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
  // public Difficulty.Level diffLevel = null;

  private GameProcess gameProcess = null;
  private List<List<Button>> buttonsList;

  private Integer selectedRow = null;
  private Integer selectedCol = null;
  GridPane numFieldGridPane = null;

  @FXML
  private Label statusLabel;
  @FXML
  private VBox vbox;

  @FXML
  protected void initialize() {
    Platform.runLater(() -> {
      updateStatusLabel();
      drawNumField();
    });
  }

  public void initGameProcess(Difficulty.Level diffLevel) {
    if (diffLevel == null) {
      throw new NullPointerException("MainScreenController: diffLevel is null");
    }
    gameProcess = new GameProcess(diffLevel);
  }

  private void updateStatusLabel() {
    Difficulty.DifficultyData status = gameProcess.getCurrentStatus();
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
        numFieldGridPane.add(blockGridPane, blockRow, blockCol);
      }
      blocksGridPanes.add(rowGridPanes);
    }

    for (int elementRow = 0; elementRow < fieldSize; elementRow++) {
      int blockRow = (int) elementRow / blocksNum;
      List<GridPane> gridPanesRow = blocksGridPanes.get(blockRow);

      for (int elementCol = 0; elementCol < fieldSize; elementCol++) {
        int blockCol = (int) elementCol / blocksNum;
        GridPane blockGridPane = gridPanesRow.get(blockCol);

        Integer value = gameProcess.getNumFieldValue(elementRow, elementCol);
        String buttonText = value == null ? "  " : String.valueOf(value);
        Button newBtn = new Button(buttonText);

        final int r = elementRow;
        final int c = elementCol;
        newBtn.setOnAction(event -> {
          selectElement(r, c);
        });
        blockGridPane.add(newBtn, elementRow % blocksSize, elementCol % blocksSize);
      }
    }
    vbox.getChildren().add(numFieldGridPane);
    Platform.runLater(guiComposer::updateStageSize);
    Platform.runLater(numFieldGridPane::requestFocus);
  }

  private void selectElement(int row, int col) {
    boolean isActuallyUnknown = gameProcess.checkIsActuallyUnknown(row, col);
    if (!isActuallyUnknown) {
      return;
    } else if (selectedRow != null && selectedCol != null) {
      return;
    }
    selectedRow = row;
    selectedCol = col;
  }
}
