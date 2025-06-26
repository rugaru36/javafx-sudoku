package rug4ru.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static enum Screen {
        difficultySelector,
        mainScreen,
        inputValue
    }

    private static Scene scene;
    private static Stage stage = null;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage _stage) throws IOException {
        stage = _stage;

        scene = new Scene(loadFXML(Screen.difficultySelector));
        scene.getStylesheets().add("font.css");

        stage.setScene(scene);
        stage.show();

        stage.setResizable(false);

        updateStageSize();
    }

    public static void setRoot(Screen screen) throws IOException {
        System.out.println("on setRoot, fxml = " + screen.name());
        scene.setRoot(loadFXML(screen));
    }

    private static Parent loadFXML(Screen screen) throws IOException {
        String screenName = screen.name();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(screenName + ".fxml"));
        return fxmlLoader.load();
    }

    public static void updateStageSize() {
        if (stage != null) {
            stage.sizeToScene();
        }
    }
}