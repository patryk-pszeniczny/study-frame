package uniquecode.study.model.operation;

public abstract class AbstractReadOnlyOperation implements MatrixOperation {
    @Override
    public int[][] apply(int[][] matrix) {
        return matrix;
    }
}