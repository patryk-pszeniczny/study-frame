package uniquecode.study.model.operation;

public class AverageOperation extends AbstractReadOnlyOperation {
    @Override
    public String getName() {
        return "Średnia";
    }

    @Override
    public String calculate(int[][] matrix) {
        int max = matrix[0][0];
        for (int[] row : matrix)
            for (int val : row)
                if (val > max) max = val;
        return "Średnia: " + max / 25;
    }
}
