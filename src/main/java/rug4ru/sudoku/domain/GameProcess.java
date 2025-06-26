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

    public int getNumFieldSize() {
        return numField.size;
    }

    public Integer getNumFieldValue(int row, int col) {
        boolean isUnknown = fillState.checkIsUnknownElement(row, col);
        return isUnknown ? null : numField.getValue(row, col);
    }
}
