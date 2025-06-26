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

    public void reduceMistakes() {
      if (mistakes > 0) {
        mistakes--;
      }
    }

    public void reduceUnknownElements() {
      if (unknownElements > 0) {
        unknownElements--;
      }
    }
  }

  public static DifficultyData getDifficultyName(Level name) {
    switch (name) {
      case Easy:
        return new DifficultyData(10, 15, name.name());
      case Mid:
        return new DifficultyData(7, 25, name.name());
      case Hard:
        return new DifficultyData(4, 35, name.name());
      default:
        return null;
    }
  }
}
