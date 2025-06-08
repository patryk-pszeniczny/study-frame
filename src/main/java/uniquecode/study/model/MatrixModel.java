package uniquecode.study.model;

import lombok.Getter;

@Getter
public class MatrixModel {
    private final int[][] matrix;
    private final int rowCount;
    private final int colCount;
    public MatrixModel(int row, int col){
        this.rowCount = row;
        this.colCount = col;
        this.matrix = new int[this.rowCount][this.colCount];

    }
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

}
