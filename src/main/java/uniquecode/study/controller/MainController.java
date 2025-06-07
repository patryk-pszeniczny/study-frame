package uniquecode.study.controller;

import uniquecode.study.model.MatrixModel;
import uniquecode.study.view.MainView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MainController {
    private final MatrixModel model;
    private final MainView view;

    public MainController(MatrixModel model, MainView view) {
        this.model = model;
        this.view = view;

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
        view.getCalculateButton().addActionListener(e -> calculateFromCombo());


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
            String wynik = "Suma: " + model.getSum();
            view.getResultArea().append(wynik + "\n");
            view.updateStatus("Obliczono sumę");
        });

        view.getToolAvgButton().addActionListener(e -> {
            String wynik = "Średnia: " + model.getAverage();
            view.getResultArea().append(wynik + "\n");
            view.updateStatus("Obliczono średnią");
        });

        view.getToolMinButton().addActionListener(e -> {
            String wynik = "Minimum: " + model.getMin();
            view.getResultArea().append(wynik + "\n");
            view.updateStatus("Obliczono minimum");
        });

        view.getToolMaxButton().addActionListener(e -> {
            String wynik = "Maksimum: " + model.getMax();
            view.getResultArea().append(wynik + "\n");
            view.updateStatus("Obliczono maksimum");
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
                model.saveToFile(file);
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
                this.model.printToFile(file);
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
                int[][] matrix = new int[5][5];
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (scanner.hasNextInt()) {
                            matrix[i][j] = scanner.nextInt();
                        }
                    }
                }
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

    private void calculateFromCombo() {
        String selected = (String) view.getComboBox().getSelectedItem();
        String wynik = "";

        if (selected.equals("Suma")) {
            wynik = "Suma: " + model.getSum();
        } else if (selected.equals("Średnia")) {
            wynik = "Średnia: " + model.getAverage();
        } else if (selected.equals("Minimum")) {
            wynik = "Minimum: " + model.getMin();
        } else if (selected.equals("Maksimum")) {
            wynik = "Maksimum: " + model.getMax();
        }

        if (!wynik.isEmpty()) {
            view.getResultArea().append(wynik + "\n");
            view.updateStatus("Wybrano: " + selected);
        }
    }
}