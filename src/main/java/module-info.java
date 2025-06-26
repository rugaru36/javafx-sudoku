module rug4ru.sudoku {
  requires javafx.controls;
  requires javafx.fxml;

  opens rug4ru.sudoku to javafx.fxml;
  opens rug4ru.sudoku.presentation.controllers to javafx.fxml;

  exports rug4ru.sudoku;
}
