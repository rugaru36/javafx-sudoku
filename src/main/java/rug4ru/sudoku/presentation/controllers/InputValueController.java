package rug4ru.sudoku.presentation.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import rug4ru.sudoku.presentation.GuiComposer;

public class InputValueController {
  GuiComposer guiComposer = GuiComposer.getInstance();
  private boolean isValidValue = false;

  @FXML
  Label messageLabel;
  @FXML
  TextField valueTextField;
  @FXML
  Button okButton;

  @FXML
  protected void initialize() {
    final Pattern pattern = Pattern.compile("^[0-9]{0,1}$");
    valueTextField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, change -> {
      final Matcher matcher = pattern.matcher(change.getControlNewText());
      isValidValue = matcher.matches() || matcher.hitEnd();
      return (matcher.matches() || matcher.hitEnd()) ? change : null;
    }));
  }

  public void onOk() {
    String textValue = valueTextField.getText();
    if (isValidValue && !textValue.isBlank()) {
      Stage stage = (Stage) okButton.getScene().getWindow();
      int value = Integer.parseInt(textValue);
      guiComposer.onInputOver(value);
      stage.close();
    } else {
      messageLabel.setText("Invalid value");
    }
  }
}
