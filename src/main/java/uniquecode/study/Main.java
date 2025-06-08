package uniquecode.study;


import uniquecode.study.controller.MainController;
import uniquecode.study.model.MatrixModel;
import uniquecode.study.model.storage.MatrixStorage;
import uniquecode.study.model.factory.OperationFactory;
import uniquecode.study.model.storage.TextMatrixStorage;
import uniquecode.study.view.MainView;

public class Main {
    public static void main(String[] args) {
        OperationFactory operationFactory = new OperationFactory();
        MatrixModel matrixModel = new MatrixModel(5, 5);
        MainView mainView = new MainView();
        MatrixStorage matrixStorage = new TextMatrixStorage();
        MainController mainController = new MainController(
                matrixModel,
                mainView,
                matrixStorage,
                operationFactory
        );
    }
}