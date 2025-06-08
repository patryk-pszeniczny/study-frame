package uniquecode.study.model.operation;

public class SumOperation extends AbstractReadOnlyOperation {
    @Override
    public String getName() {
        return "Suma";
    }

    @Override
    public String calculate(int[][] matrix) {
        int sum = 0;
        for (int[] row : matrix) {
            for (int value : row) sum += value;
        }
        return "Suma: " + sum;
    }

}
