package uniquecode.study.controller;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import uniquecode.study.model.MatrixModel;
import uniquecode.study.model.operation.MatrixOperation;
import uniquecode.study.model.storage.MatrixStorage;
import uniquecode.study.model.factory.OperationFactory;
import uniquecode.study.view.MainView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MainController {
    private final MatrixModel model;
    private final MainView view;
    private final MatrixStorage storage;
    private final OperationFactory so;

    public MainController(MatrixModel model, MainView view, MatrixStorage storage, OperationFactory operationFactory) {
        this.model = model;
        this.view = view;
        this.storage = storage;
        this.so = operationFactory;

        // Przycisk Dodaj
        view.getInsertButton().addActionListener(e -> insertValue());
        // Przycisk Wyczyść
        view.getClearButton().addActionListener(e -> clearMatrix());

        view.getCloseButton().addActionListener(e -> loadMatrix());

        view.getPrintButton().addActionListener(e -> printMatrix());

        // Przycisk Zapisz
        view.getSaveButton().addActionListener(e -> saveMatrix());
        view.getToolSaveButton().addActionListener(e -> saveMatrix());

        // Przycisk oblicz osobno
        view.getCalculateButton().addActionListener(e -> calculate((String) view.getComboBox().getSelectedItem()));

        //Wykres
        view.getToolChartButton().addActionListener(e -> showPieChart());

        view.getTable().getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if(row >= 0 && column >= 0) {
                Object value = view.getTable().getValueAt(row, column);
                try {
                    int intValue = Integer.parseInt(value.toString());
                    model.setValue(row, column, intValue);
                    view.updateStatus("Zmieniono [" + row + "," + column + "] na " + intValue);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Błąd: wpisz liczbę całkowitą", "Błąd danych", JOptionPane.ERROR_MESSAGE);
                    view.getTable().setValueAt(model.getMatrix()[row][column], row, column);
                }
            }
        });

        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getTable().getSelectedRow();
                int selectedCol = view.getTable().getSelectedColumn();
                if (selectedRow != -1) {
                    view.getRowSpinner().setValue(selectedRow + 1);
                }
                if (selectedCol != -1) {
                    view.getColSpinner().setValue(selectedCol + 1);
                }
            }
        });

        view.getTable().getColumnModel().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedCol = view.getTable().getSelectedColumn();
                if (selectedCol != -1) {
                    view.getColSpinner().setValue(selectedCol + 1);
                }
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow != -1) {
                    view.getRowSpinner().setValue(selectedRow + 1);
                }
            }
        });


        // Pasek narzędziowy: suma, średnia, min, max
        view.getToolSumButton().addActionListener(e -> {
            calculate("Suma");
        });

        view.getToolAvgButton().addActionListener(e -> {
            calculate("Średnia");
        });

        view.getToolMinButton().addActionListener(e -> {
            calculate("Minimum");
        });

        view.getToolMaxButton().addActionListener(e -> {
            calculate("Maksimum");
        });
        view.getToolFillButton().addActionListener(e -> {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    model.setValue(i, j, ThreadLocalRandom.current().nextInt(0, 100));
                }
            }
            view.updateTable(model.getMatrix());
            view.updateStatus("Tablica wypełniona losowymi wartościami");
        });
        view.getFillButton().addActionListener(e -> {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    model.setValue(i, j, ThreadLocalRandom.current().nextInt(0, 100));
                }
            }
            view.updateTable(model.getMatrix());
            view.updateStatus("Tablica wypełniona losowymi wartościami");
        });
        view.getToolAddButton().addActionListener(e ->{
            for (int i = 0; i < 5; i++) {
                for(int j = 0; j < 5; j++){
                    model.setValue(i, j, model.getValue(i, j) + 1);
                }
            }
            view.updateTable(model.getMatrix());
            view.updateStatus("Wszystkie wartości zwiększone o 1");
        });
        view.getToolClearButton().addActionListener(e -> {
            for (int i = 0; i < 5; i++) {
                for(int j = 0; j < 5; j++){
                    model.setValue(i, j, model.getValue(i, j) - 1);
                }
            }
            view.updateTable(model.getMatrix());
            view.updateStatus("Wszystkie wartości zmniejszono o 1");
        });
    }

    private void showPieChart() {
        int[][] matrix = model.getMatrix();
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (int i = 0; i < 5; i++) {
            int sum = 0;
            for (int j = 0; j < 5; j++) {
                sum += matrix[i][j];
            }
            dataset.setValue("Wiersz " + (i + 1), sum);
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Udział wartości wierszy", dataset, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame chartFrame = new JFrame("Wykres kołowy");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(600, 400);
        chartFrame.add(chartPanel);
        chartFrame.setLocationRelativeTo(view);
        chartFrame.setVisible(true);

        view.updateStatus("Wyświetlono wykres kołowy");
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
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                this.storage.save(this.model.getMatrix(), file);
                view.updateStatus("Zapisano do pliku: " + file.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Błąd zapisu do pliku", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void printMatrix() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                this.storage.save(this.model.getMatrix(), file);
                view.updateStatus("Wydruk zapisany do: " + file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, "Błąd wydruku do pliku", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadMatrix(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (Scanner scanner = new Scanner(file)) {
                int[][] matrix = this.storage.load(file, scanner);
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        model.setValue(i, j, matrix[i][j]);
                    }
                }
                view.updateTable(model.getMatrix());
                view.updateStatus("Wczytano dane z pliku: " + file.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Błąd odczytu pliku", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void calculate(String selected) {
        MatrixOperation operation = so.getByName(selected);
        if (operation != null) {
            String wynik = operation.calculate(model.getMatrix());
            view.getResultArea().append(wynik + "\n");
            view.updateStatus("Wybrano: " + selected);
        }

    }
}