package uniquecode.study.model.operation;

public class MinOperation extends AbstractReadOnlyOperation {
    @Override
    public String getName() {
        return "Minimum";
    }

    @Override
    public String calculate(int[][] matrix) {
        int min = matrix[0][0];
        for (int[] row : matrix)
            for (int val : row)
                if (val < min) min = val;
        return "Minimum: " + min;
    }
}
