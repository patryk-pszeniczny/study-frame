package uniquecode.study.model.operation;

public interface MatrixOperation {
    String getName();
    int[][] apply(int[][] matrix);
    String calculate(int[][] matrix);
}
