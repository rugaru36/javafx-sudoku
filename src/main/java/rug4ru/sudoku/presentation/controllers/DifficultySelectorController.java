package rug4ru.sudoku.presentation.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import rug4ru.sudoku.App;
import rug4ru.sudoku.domain.Difficulty;

public class DifficultySelectorController {
    @FXML
    private VBox vbox;

    @FXML
    private Label messageLabel;

    @FXML
    protected void initialize() {
        //Setting size for the pane  
        GridPane gridPane = new GridPane();    
        gridPane.setHgap(10);
        // gridPane.setMinSize(400, 200);
        Difficulty.Level[] diffLevels = Difficulty.Level.values();
        
        for (int i = 0; i < diffLevels.length; i++) {
            Difficulty.Level diffLevel = diffLevels[i];
            String strName = diffLevel.name();
            Button btn = new Button(strName);
            btn.setOnAction(event -> {
                try {
                    onSelect(diffLevel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            btn.isFocusTraversable();
            btn.requestFocus();
            gridPane.add(btn, i, 0);
        }
        vbox.getChildren().add(gridPane);
        vbox.requestFocus();

        Platform.runLater(gridPane::requestFocus);
    }

    private void onSelect(Difficulty.Level diffLevel) throws IOException {
        MainScreenController.diffLevel = diffLevel;
        App.setRoot(App.Screen.mainScreen);
    }
}
