package rug4ru.sudoku.lib;

public class Randomizer {
  public static int randomIntInRange(int min, int max) {
    return (int) (Math.random() * (max - min) + min);
  }
}
