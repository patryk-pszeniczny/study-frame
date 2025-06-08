package uniquecode.study.model.factory;

import uniquecode.study.model.operation.*;

import java.util.List;

public class OperationFactory {
    private final List<MatrixOperation> operations = List.of(
            new SumOperation(),
            new AverageOperation(),
            new MinOperation(),
            new MaxOperation()
    );

    public List<MatrixOperation> getAll() {
        return operations;
    }

    public MatrixOperation getByName(String name) {
        return operations.stream()
                .filter(op -> op.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }
}
