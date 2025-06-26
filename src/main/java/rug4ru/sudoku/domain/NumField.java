package rug4ru.sudoku.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import rug4ru.sudoku.lib.Randomizer;

public class NumField {
    public final int size = 9;
    private int[][] matrix = new int[size][size];
    private List<Integer> dictionary = new ArrayList<Integer>();

    public NumField(boolean needToPrint) {
        generateDictionary();
        initMatrixWithDictionary();
        System.out.println("NumField: Matrix initiated successfully!");
        if (needToPrint) {
            printMatrix();
        }
        randomizeMatrix();
        System.out.println("NumField: Matrix randomized successfully!");
        if (needToPrint) {
            printMatrix();
        }
    }

    public int getValue(int row, int col) {
        return matrix[row][col];
    }

    private void generateDictionary() {
        dictionary = new ArrayList<Integer>();
        for (int num : IntStream.rangeClosed(1,9).toArray()) {
            dictionary.add((num));
        }
        Collections.shuffle(dictionary);
    }

    private void randomizeMatrix() {
        int blockSize = (int) Math.floor(Math.sqrt(size));
        int blockIndex = 0;
        for (int diagIndex = 0; diagIndex < size; diagIndex++) {
            if (diagIndex > 0 && diagIndex % blockSize == 0) {
                blockIndex++;
            }
            int minToSwap = blockSize * blockIndex;
            int maxToSwap = blockSize * (blockIndex + 1) - 1;
            
            int randomColPairToSwap = Randomizer.randomIntInRange(minToSwap, maxToSwap);
            int randomRowPairToSwap = Randomizer.randomIntInRange(minToSwap, maxToSwap);
            swapMatrixColumns(diagIndex, randomColPairToSwap);
            swapMatrixRows(diagIndex, randomRowPairToSwap);
        }
    }

    private void initMatrixWithDictionary() {
        int blockSize = (int) Math.floor(Math.sqrt(size));
        int rowBlockNum = 0;
        for (int row = 0; row < size; row++) {
            shiftDictionary(blockSize);
            if (row > 0 && row % blockSize == 0) {
                rowBlockNum++;
            }
            shiftDictionary(blockSize + rowBlockNum);
            matrix[row] = dictionary.stream().mapToInt(i->i).toArray();
        }
    }

    private void swapMatrixColumns(int colA, int colB) {
        for (int row = 0; row < size; row++) {
            int buff = matrix[row][colA];
            matrix[row][colA] = matrix[row][colB];
            matrix[row][colB] = buff;
        }
    }

    private void swapMatrixRows(int rowA, int rowB) {
        for (int col = 0; col < size; col++) {
            int buff = matrix[rowA][col];
            matrix[rowA][col] = matrix[rowB][col];
            matrix[rowB][col] = buff;
        }
    }

    private void shiftDictionary(int shiftSize) {
        if (shiftSize == 0) return;
        List<Integer> firstPart = dictionary.subList(0, shiftSize);
        List<Integer> secondPart = dictionary.subList(shiftSize, dictionary.size());
        secondPart.addAll(firstPart);
        dictionary = secondPart;
    }

    private void printMatrix() {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
