package rug4ru.sudoku.presentation;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import rug4ru.sudoku.App;
import rug4ru.sudoku.domain.Difficulty;
import rug4ru.sudoku.presentation.controllers.MainScreenController;

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

        FXMLLoader loader = getFXMLLoader(Screen.difficultySelector);
        scene = new Scene(loader.load());
        scene.getStylesheets().add("font.css");

        stage.setScene(scene);
        stage.show();

        stage.setResizable(false);

        updateStageSize();
    }

    public void openMainScreen(Difficulty.Level diffLevel) throws IOException {
        FXMLLoader loader = getFXMLLoader(Screen.mainScreen);
        Parent parent = loader.load();
        MainScreenController controller = loader.getController();
        controller.initData(diffLevel);
        scene.setRoot(parent);
    }

    private FXMLLoader getFXMLLoader(Screen screen) throws IOException {
        String screenName = screen.name();
        return new FXMLLoader(App.class.getResource(screenName + ".fxml"));
    }

    public void updateStageSize() {
        if (stage != null) {
            stage.sizeToScene();
        }
    }

    // private void setRoot(Screen screen) throws IOException {
    //     System.out.println("on setRoot, fxml = " + screen.name());
    //     scene.setRoot(loadFXML(screen));
    // }

    // singleton
    private static class SingletonHelper {
        private static GuiComposer instance = new GuiComposer();
    }
    public static GuiComposer getInstance() {
        return SingletonHelper.instance;
    }
}