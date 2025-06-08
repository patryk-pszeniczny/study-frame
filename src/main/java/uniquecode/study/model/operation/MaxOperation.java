package uniquecode.study.model.operation;

public class MaxOperation extends AbstractReadOnlyOperation {
    @Override
    public String getName() {
        return "Maksimum";
    }

    @Override
    public String calculate(int[][] matrix) {
        int max = matrix[0][0];
        for (int[] row : matrix)
            for (int val : row)
                if (val > max) max = val;
        return "Maksimum: " + max;
    }
}
