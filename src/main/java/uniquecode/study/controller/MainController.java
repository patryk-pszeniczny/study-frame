package uniquecode.study.controller;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import uniquecode.study.model.MatrixModel;
import uniquecode.study.model.operation.MatrixOperation;
import uniquecode.study.model.storage.MatrixStorage;
import uniquecode.study.model.factory.OperationFactory;
import uniquecode.study.view.AboutWindow;
import uniquecode.study.view.MainView;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MainController {
    private final MatrixModel model;
    private final MainView view;
    private final MatrixStorage storage;
    private final OperationFactory operationFactory;

    public MainController(MatrixModel model, MainView view,
                          MatrixStorage storage, OperationFactory operationFactory) {
        this.model = model;
        this.view = view;
        this.storage = storage;
        this.operationFactory = operationFactory;

        initButtonActions();
        initTableListeners();
        initMenuActions();
        initToolbarActions();
    }

    private void initButtonActions() {
        view.getInsertButton().addActionListener(e -> insertValue());
        view.getClearButton().addActionListener(e -> clearMatrix());
        view.getCloseButton().addActionListener(e -> loadMatrix());
        view.getPrintButton().addActionListener(e -> printMatrix());
        view.getSaveButton().addActionListener(e -> saveMatrix());
        view.getToolSaveButton().addActionListener(e -> saveMatrix());
        view.getCalculateButton().addActionListener(e -> calculate((String) view.getComboBox().getSelectedItem()));
        view.getToolChartButton().addActionListener(e -> showPieChart());
        view.getFillButton().addActionListener(e -> fillMatrixWithRandomValues());
        view.getHelpButton().addActionListener(e -> openHelpPageInFrame());
        view.getAboutButton().addActionListener(e -> openAboutWindow());
    }
    public void openAboutWindow() {
        AboutWindow aboutWindow = new AboutWindow();
        aboutWindow.setVisible(true);
        aboutWindow.setLocationRelativeTo(view);
        view.updateStatus("Otworzono okno 'O aplikacji'");
    }
    private void initTableListeners() {
        view.getTable().getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int col = e.getColumn();
            if (row >= 0 && col >= 0) {
                try {
                    int value = Integer.parseInt(view.getTable().getValueAt(row, col).toString());
                    model.setValue(row, col, value);
                    view.updateStatus("Zmieniono [" + row + "," + col + "] na " + value);
                } catch (NumberFormatException ex) {
                    showError("Błąd: wpisz liczbę całkowitą", row, col);
                }
            }
        });

        ListSelectionListener selectionUpdater = e -> {
            if (!e.getValueIsAdjusting()) {
                int row = view.getTable().getSelectedRow();
                int col = view.getTable().getSelectedColumn();
                if (row != -1) view.getRowSpinner().setValue(row + 1);
                if (col != -1) view.getColSpinner().setValue(col + 1);
            }
        };

        view.getTable().getSelectionModel().addListSelectionListener(selectionUpdater);
        view.getTable().getColumnModel().getSelectionModel().addListSelectionListener(selectionUpdater);
    }

    private void initMenuActions() {
        view.getOpenMenuItem().addActionListener(e -> loadMatrix());
        view.getSaveMenuItem().addActionListener(e -> saveMatrix());
        view.getPrintMenuItem().addActionListener(e -> printMatrix());

        view.getIncreaseMenuItem().addActionListener(e -> modifyMatrix(1, "Wszystkie wartości zwiększone o 1"));
        view.getDecreaseMenuItem().addActionListener(e -> modifyMatrix(-1, "Wszystkie wartości zmniejszono o 1"));
        view.getFillMenuItem().addActionListener(e -> fillMatrixWithRandomValues());

        view.getTipOfTheDayMenuItem().addActionListener(e -> view.showTipsOnStartup());
        view.getChartMenuItem().addActionListener(e -> showPieChart());

        view.getSumMenuItem().addActionListener(e -> calculate("Suma"));
        view.getAverageMenuItem().addActionListener(e -> calculate("Średnia"));
        view.getMinMenuItem().addActionListener(e -> calculate("Minimum"));
        view.getMaxMenuItem().addActionListener(e -> calculate("Maksimum"));

        view.getHelpMenuItem().addActionListener(e -> openHelpPageInFrame());
        view.getAboutMenuItem().addActionListener(e -> openAboutWindow());
    }

    private void initToolbarActions() {
        view.getToolSumButton().addActionListener(e -> calculate("Suma"));
        view.getToolAvgButton().addActionListener(e -> calculate("Średnia"));
        view.getToolMinButton().addActionListener(e -> calculate("Minimum"));
        view.getToolMaxButton().addActionListener(e -> calculate("Maksimum"));
        view.getToolFillButton().addActionListener(e -> fillMatrixWithRandomValues());
        view.getToolAddButton().addActionListener(e -> modifyMatrix(1, "Wszystkie wartości zwiększone o 1"));
        view.getToolClearButton().addActionListener(e -> modifyMatrix(-1, "Wszystkie wartości zmniejszono o 1"));
    }

    private void insertValue() {
        view.getStatusRight().setText("Status: Oblicza");
        try {
            int value = Integer.parseInt(view.getInputField().getText());
            int row = view.getSelectedRow();
            int col = view.getSelectedColumn();
            model.setValue(row, col, value);
            view.updateTable(model.getMatrix());
            view.updateStatus("Wstawiono " + value + " do [" + (row + 1) + "," + (col + 1) + "]");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Nieprawidłowa liczba!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        view.getStatusRight().setText("Status: Gotowe");
    }

    private void clearMatrix() {
        model.clear();
        view.updateTable(model.getMatrix());
        view.updateStatus("Tablica wyczyszczona");
    }

    private void saveMatrix() {
        saveOrPrintMatrix("Zapisano do pliku: ", false);
    }

    private void printMatrix() {
        saveOrPrintMatrix("Wydruk zapisany do: ", true);
    }

    private void saveOrPrintMatrix(String successMessage, boolean print) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                storage.save(model.getMatrix(), file);
                view.updateStatus(successMessage + file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, print ? "Błąd wydruku do pliku" : "Błąd zapisu do pliku", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadMatrix() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (Scanner scanner = new Scanner(file)) {
                int[][] matrix = storage.load(file, scanner);
                for (int i = 0; i < 5; i++)
                    for (int j = 0; j < 5; j++)
                        model.setValue(i, j, matrix[i][j]);
                view.updateTable(model.getMatrix());
                view.updateStatus("Wczytano dane z pliku: " + file.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Błąd odczytu pliku", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showPieChart() {
        int[][] matrix = model.getMatrix();
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (int i = 0; i < 5; i++) {
            int sum = 0;
            for (int j = 0; j < 5; j++) sum += matrix[i][j];
            dataset.setValue("Wiersz " + (i + 1), sum);
        }

        JFreeChart chart = ChartFactory.createPieChart("Udział wartości wierszy", dataset, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);

        JFrame chartFrame = new JFrame("Wykres kołowy");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(600, 400);
        chartFrame.add(chartPanel);
        chartFrame.setLocationRelativeTo(view);
        chartFrame.setVisible(true);

        view.updateStatus("Wyświetlono wykres kołowy");
    }

    private void calculate(String operationName) {
        MatrixOperation operation = operationFactory.getByName(operationName);
        if (operation != null) {
            String result = operation.calculate(model.getMatrix());
            view.getResultArea().append(result + "\n");
            view.updateStatus("Wybrano: " + operationName);
        }
    }

    private void fillMatrixWithRandomValues() {
        int confirm = JOptionPane.showConfirmDialog(view,
                "Czy na pewno chcesz nadpisać bieżącą macierz?",
                "Potwierdzenie", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                model.setValue(i, j, ThreadLocalRandom.current().nextInt(0, 100));
        view.updateTable(model.getMatrix());
        view.updateStatus("Tablica wypełniona losowymi wartościami");
    }

    private void modifyMatrix(int delta, String statusMessage) {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                model.setValue(i, j, model.getValue(i, j) + delta);
        view.updateTable(model.getMatrix());
        view.updateStatus(statusMessage);
    }

    private void showError(String message, int row, int col) {
        JOptionPane.showMessageDialog(view, message, "Błąd danych", JOptionPane.ERROR_MESSAGE);
        view.getTable().setValueAt(model.getMatrix()[row][col], row, col);
    }
    private void openHelpPageInFrame() {
        try {
            URL resource = getClass().getResource("/help/index.html");
            if (resource == null) {
                JOptionPane.showMessageDialog(view, "Nie znaleziono pliku pomocy.", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JEditorPane editorPane = new JEditorPane(resource);
            editorPane.setEditable(false);
            editorPane.setContentType("text/html");

            JScrollPane scrollPane = new JScrollPane(editorPane);

            JFrame helpFrame = new JFrame("Pomoc");
            helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            helpFrame.setSize(800, 800);
            helpFrame.add(scrollPane);
            helpFrame.setLocationRelativeTo(view);
            helpFrame.setVisible(true);

            view.updateStatus("Otworzono pomoc");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Błąd podczas ładowania pliku pomocy: " + e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

}
