package rug4ru.sudoku.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rug4ru.sudoku.lib.Randomizer;

public class FillState {
    private List<int[]> unknownElementsCoordinates = new ArrayList<int[]>();

    public FillState(int numOfUnknown, int max, boolean needToPrint) {
        generateRandomCoordinatesPairs(numOfUnknown, max);
        if (unknownElementsCoordinates.size() == numOfUnknown) {
            System.out.println("FillState: Unknown element coordinate pairs generated!");
        } else {
            System.out.println("FillState: Something gone wrong!");
        }
        if (needToPrint) {
            print();
        }
    }

    public boolean checkIsUnknownElement(int row, int col) {
        int index = getIndexOfCoordinates(row, col);
        return index >= 0;
    }

    public void removeUnknownElement(int row, int col) {
        unknownElementsCoordinates.removeIf(pair -> pair[0] == row && pair[1] == col);
    }

    private void generateRandomCoordinatesPairs(int numOfUnknown, int max) {
        for (int i = 0; i < numOfUnknown; i++) {
            int row = 0;
            int col = 0;
            do {
                row = Randomizer.randomIntInRange(0, max);
                col = Randomizer.randomIntInRange(0, max);
            } while (checkIsUnknownElement(row, col));
            unknownElementsCoordinates.add(new int[] { row, col });
        }
    }

    private int getIndexOfCoordinates(int row, int col) {
        for (int i = 0; i < unknownElementsCoordinates.size(); i++) {
            int[] coordinatesPair = unknownElementsCoordinates.get(i);
            if (coordinatesPair[0] == row && coordinatesPair[1] == col) {
                return i;
            }
        }
        return -1;
    }

    private void print() {
        for (int[] pair : unknownElementsCoordinates) {
            System.out.println(Arrays.toString(pair));
        }
    }
}
