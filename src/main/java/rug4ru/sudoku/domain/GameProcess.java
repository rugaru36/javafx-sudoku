package rug4ru.sudoku.domain;

public class GameProcess {
    private NumField numField;
    private FillState fillState;
    private Difficulty.DifficultyData difficultyData;

    public GameProcess(Difficulty.Level diffLevel) {
        difficultyData = Difficulty.getDifficultyName(diffLevel);
        numField = new NumField(true);
        fillState = new FillState(difficultyData.unknownElements, numField.size, true);
    }

    public boolean onNewValue(int value, int row, int col) {
        boolean isActuallyUnknown = fillState.checkIsUnknownElement(row, col);
        if (!isActuallyUnknown) {
            return false;
        }
        boolean isValueCorrect = value == numField.getValue(row, col);
        if (isValueCorrect) {
            difficultyData.reduceUnknownElements();
            fillState.removeUnknownElement(row, col);
        } else {
            difficultyData.reduceMistakes();
        }
        return isValueCorrect;
    }

    public int getNumFieldSize() {
        return numField.size;
    }

    public Integer getNumFieldValue(int row, int col) {
        boolean isUnknown = fillState.checkIsUnknownElement(row, col);
        return isUnknown ? null : numField.getValue(row, col);
    }
}
