package rug4ru.sudoku.presentation;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import rug4ru.sudoku.App;

public class GuiComposer {
    private Stage stage = null;
    private Scene scene = null;

    public static enum Screen {
        difficultySelector,
        mainScreen,
        inputValue
    }

    public void initGui(Stage _stage) throws IOException {
        stage = _stage;

        scene = new Scene(loadFXML(Screen.difficultySelector));
        scene.getStylesheets().add("font.css");

        stage.setScene(scene);
        stage.show();

        stage.setResizable(false);

        updateStageSize();
    }

    private Parent loadFXML(Screen screen) throws IOException {
        String screenName = screen.name();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(screenName + ".fxml"));
        return fxmlLoader.load();
    }

    private void updateStageSize() {
        if (stage != null) {
            stage.sizeToScene();
        }
    }

    // singleton
    private static class SingletonHelper {
        private static GuiComposer instance = new GuiComposer();
    }
    public static GuiComposer getInstance() {
        return SingletonHelper.instance;
    }
}