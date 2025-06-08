package uniquecode.study.model.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextMatrixStorage implements MatrixStorage {
    @Override
    public int[][] load(File file, Scanner scanner) {
        int[][] matrix = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (scanner.hasNextInt()) {
                    matrix[i][j] = scanner.nextInt();
                }
            }
        }
        return matrix;
    }


    @Override
    public void save(int[][] matrix, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    writer.write(matrix[i][j] + " ");
                }
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }
}
