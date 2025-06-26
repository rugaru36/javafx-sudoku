package rug4ru.sudoku.domain;

public class Difficulty {
    public static enum Level {
        Easy,
        Mid,
        Hard,
    }

    public static class DifficultyData {
        public int mistakes;
        public int unknownElements;
        public String name; 

        public DifficultyData(int _mistakes, int _unknownElements, String _name) {
            mistakes = _mistakes;
            unknownElements = _unknownElements;
            name = _name;
        }
    }

    public static DifficultyData getDifficultyName(Level name) {
        switch (name) {
            case Easy:
                return new DifficultyData(10, 10, name.name());
            case Mid:
                return new DifficultyData(7, 20, name.name());
            case Hard:
                return new DifficultyData(4, 30, name.name());
            default:
                return null;
        }
    }
}
