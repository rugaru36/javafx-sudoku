package rug4ru.sudoku.presentation;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import rug4ru.sudoku.App;
import rug4ru.sudoku.domain.Difficulty;
import rug4ru.sudoku.presentation.controllers.MainScreenController;

// handles cross-scene logic
public class GuiComposer {
  private Stage rootStage = null;
  private Scene rootScene = null;
  private FXMLLoader currentRootLoader = null;
  private Screen currentScreen = null;

  public static enum Screen {
    difficultySelector,
    mainScreen,
    inputValue
  }

  public void initGui(Stage _stage) throws IOException {
    rootStage = _stage;
    rootStage.setTitle("Sudoku");

    openDifficultySelector();
    rootScene.getStylesheets().add("font.css");

    rootStage.setScene(rootScene);
    rootStage.show();

    rootStage.setResizable(false);
  }

  public void openDifficultySelector() throws IOException {
    setRootScene(Screen.difficultySelector);
  }

  public void openMainScreen(Difficulty.Level diffLevel) throws IOException {
    setRootScene(Screen.mainScreen);
    MainScreenController mainScreenController = currentRootLoader.getController();
    mainScreenController.initGameProcess(diffLevel);
  }

  public void openInputScreen() throws IOException {
    FXMLLoader loader = getFXMLLoader(Screen.inputValue);
    Stage stage = new Stage();
    stage.setOnCloseRequest(event -> {
      onInputOver(null);
    });
    stage.setResizable(false);
    stage.setAlwaysOnTop(true);
    stage.setScene(new Scene(loader.load()));
    stage.show();
  }

  public void onInputOver(Integer value) {
    if (currentScreen != Screen.mainScreen) {
      throw new RuntimeException("Current screen must be " + Screen.mainScreen.name());
    }
    MainScreenController mainScreenController = currentRootLoader.getController();
    if (value != null) {
      mainScreenController.onNewValue(value);
    }
    mainScreenController.dropSelection();
  }

  private FXMLLoader getFXMLLoader(Screen screen) throws IOException {
    String screenName = screen.name();
    return new FXMLLoader(App.class.getResource(screenName + ".fxml"));
  }

  private void setRootScene(Screen screen) throws IOException {
    FXMLLoader loader = getFXMLLoader(screen);
    if (rootScene == null) {
      rootScene = new Scene(loader.load());
    } else {
      rootScene.setRoot(loader.load());
    }
    currentRootLoader = loader;
    currentScreen = screen;
    Platform.runLater(() -> {
      rootStage.sizeToScene();
    });
  }

  // singleton
  private static class SingletonHelper {
    private static GuiComposer instance = new GuiComposer();
  }

  public static GuiComposer getInstance() {
    return SingletonHelper.instance;
  }
}