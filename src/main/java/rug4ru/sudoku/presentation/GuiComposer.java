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
  private Scene rootScene = null;

  MainScreenController mainScreenController = null;

  public static enum Screen {
    difficultySelector,
    mainScreen,
    inputValue
  }

  public void initGui(Stage _stage) throws IOException {
    stage = _stage;

    FXMLLoader loader = getFXMLLoader(Screen.difficultySelector);
    rootScene = new Scene(loader.load());
    rootScene.getStylesheets().add("font.css");

    stage.setScene(rootScene);
    stage.show();

    stage.setResizable(false);

    updateStageSize();
  }

  public void openMainScreen(Difficulty.Level diffLevel) throws IOException {
    FXMLLoader loader = getFXMLLoader(Screen.mainScreen);
    Parent parent = loader.load();
    mainScreenController = loader.getController();
    mainScreenController.initGameProcess(diffLevel);
    rootScene.setRoot(parent);
  }

  public void openInputScreen() throws IOException {
    FXMLLoader loader = getFXMLLoader(Screen.inputValue);
    Stage stage = new Stage();
    stage.setScene(new Scene(loader.load(), 150, 150));
    stage.show();
    stage.setOnCloseRequest(event -> {
      onInputOver(null);
    });
  }

  public void onInputOver(Integer value) {
    if (value != null) {
      mainScreenController.onNewValue(value);
    }
    mainScreenController.dropSelection();
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

  // singleton
  private static class SingletonHelper {
    private static GuiComposer instance = new GuiComposer();
  }

  public static GuiComposer getInstance() {
    return SingletonHelper.instance;
  }
}