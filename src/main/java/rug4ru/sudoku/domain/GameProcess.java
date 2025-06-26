package rug4ru.sudoku.domain;

public class GameProcess {
  private NumField numField;
  private FillState fillState;
  private Difficulty.DifficultyData currentStatus;

  public GameProcess(Difficulty.Level diffLevel) {
    currentStatus = Difficulty.getDifficultyName(diffLevel);
    numField = new NumField(true);
    fillState = new FillState(currentStatus.unknownElements, numField.size, true);
  }

  public boolean onNewValue(int value, int row, int col) {
    boolean isActuallyUnknown = fillState.checkIsUnknownElement(row, col);
    if (!isActuallyUnknown) {
      return false;
    }
    boolean isValueCorrect = value == numField.getValue(row, col);
    if (isValueCorrect) {
      currentStatus.reduceUnknownElements();
      fillState.removeUnknownElement(row, col);
    } else {
      currentStatus.reduceMistakes();
    }
    return isValueCorrect;
  }

  public Difficulty.DifficultyData getCurrentStatus() {
    return currentStatus;
  }

  public int getNumFieldSize() {
    return numField.size;
  }

  public boolean checkIsActuallyUnknown(int row, int col) {
    return fillState.checkIsUnknownElement(row, col);
  }

  public Integer getNumFieldValue(int row, int col) {
    boolean isUnknown = fillState.checkIsUnknownElement(row, col);
    return isUnknown ? null : numField.getValue(row, col);
  }
}
