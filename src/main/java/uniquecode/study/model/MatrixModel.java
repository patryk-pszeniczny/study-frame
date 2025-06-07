package uniquecode.study.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MatrixModel {
    private int[][] matrix = new int[5][5];
    private int rowCount = 5;
    private int colCount = 5;

    public void setValue(int row, int col, int value) {
        matrix[row][col] = value;
    }

    public void clear() {
        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < colCount; j++)
                matrix[i][j] = 0;
    }

    public int getValue(int row, int col) {
        if (row >= 0 && row < rowCount && col >= 0 && col < colCount) {
            return matrix[row][col];
        }
        return 0;
    }

    public int getSum() {
        int sum = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public double getAverage() {
        return (double) getSum() / 25.0;
    }

    public int getMax() {
        int max = matrix[0][0];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        return max;
    }

    public int getMin() {
        int min = matrix[0][0];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
        }
        return min;
    }

    public void printToFile(File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        try {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    writer.write(matrix[i][j] + " ");
                }
                writer.write("\n");
            }
        }finally {
            writer.close();
        }
    }
    public void saveToFile(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < 5; j++) {
                    writer.write(matrix[i][j] + " ");
                }
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
