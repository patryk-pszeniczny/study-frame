package uniquecode.study;


import uniquecode.study.controller.MainController;
import uniquecode.study.model.MatrixModel;
import uniquecode.study.view.MainView;

public class Main {
    public static void main(String[] args) {
        MatrixModel matrixModel = new MatrixModel();
        MainView mainView = new MainView();
        MainController mainController = new MainController(
                matrixModel,
                mainView
        );
    }
}