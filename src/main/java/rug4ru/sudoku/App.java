package rug4ru.sudoku;

import javafx.application.Application;
import javafx.stage.Stage;
import rug4ru.sudoku.presentation.GuiComposer;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private GuiComposer guiComposer = GuiComposer.getInstance();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        guiComposer.initGui(stage);
    }
}