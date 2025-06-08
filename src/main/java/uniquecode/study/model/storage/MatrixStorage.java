package uniquecode.study.model.storage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public interface MatrixStorage {
    void save(int[][] matrix, File file) throws IOException;
    int[][] load(File file, Scanner scanner) throws IOException;
}
