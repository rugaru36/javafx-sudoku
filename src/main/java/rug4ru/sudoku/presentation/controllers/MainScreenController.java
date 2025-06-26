package rug4ru.sudoku.presentation.controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import rug4ru.sudoku.App;
import rug4ru.sudoku.domain.Difficulty;
import rug4ru.sudoku.domain.GameProcess;

public class MainScreenController {
    public static Difficulty.Level diffLevel = null;

    private GameProcess gameProcess = null;
    private List<List<Button>> buttonsList;

    private Integer selectedRow = null;
    private Integer selectedCol = null;

    @FXML
    private VBox vbox;

    @FXML
    protected void initialize() {
        initGameProcess();

        int fieldSize = gameProcess.getNumFieldSize();
        int blocksNum = (int) Math.sqrt(fieldSize);
        int blocksSize = (int) fieldSize / blocksNum;

        GridPane mainGridPane = new GridPane();

        mainGridPane.setHgap(5);
        mainGridPane.setVgap(5);

        // generating blocks
        List<List<GridPane>> blocksGridPanes = new ArrayList<List<GridPane>>();
        for (int blockRow = 0; blockRow < blocksNum; blockRow++) {
            List<GridPane> rowGridPanes = new ArrayList<GridPane>();
            for (int blockCol = 0; blockCol < blocksNum; blockCol++) {
                GridPane blockGridPane = new GridPane();
                rowGridPanes.add(blockGridPane);
                mainGridPane.add(blockGridPane, blockRow, blockCol);
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
        vbox.getChildren().add(mainGridPane);
        Platform.runLater(App::updateStageSize);
        Platform.runLater(mainGridPane::requestFocus);
    }

    private void selectElement(int row, int col) {
        selectedRow = row;
        selectedCol = col;
    }

    private void initGameProcess() {
        Difficulty.Level diffLevel = MainScreenController.diffLevel;
        if (diffLevel == null) {
            throw new NullPointerException();
        }
        gameProcess = new GameProcess(diffLevel);
    }
}
