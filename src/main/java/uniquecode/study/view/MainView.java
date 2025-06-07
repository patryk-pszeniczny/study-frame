package uniquecode.study.view;

import lombok.Getter;
import uniquecode.study.model.MatrixModel;
import uniquecode.study.util.JButtonUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
@Getter
public class MainView extends JFrame {

    // Komponenty
    private JTextField inputField;
    private JSpinner rowSpinner;
    private JSpinner colSpinner;
    private JTable table;
    private JTextArea resultArea;
    private JComboBox<String> comboBox;
    private JButton insertButton;
    private JButton clearButton;
    private JButton saveButton;
    private JButton calculateButton;
    private JButton fillButton;

    // Przyciski z toolbara
    private JButton toolAddButton;
    private JButton toolClearButton;
    private JButton toolFillButton;
    private JButton toolSaveButton;
    private JButton toolSumButton;
    private JButton toolAvgButton;
    private JButton toolMinButton;
    private JButton toolMaxButton;
    private JButton printButton;
    private JButton closeButton;
    private JButton helpButton;
    private JButton aboutButton;
    // Statusy
    private JLabel statusLeft;
    private JLabel statusRight;

    public MainView() {
        super("Moje Okno - Aplikacja Tablicowa");

        initializeGUI();
    }

    private void initializeGUI() {
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setJMenuBar(createMenuBar());
        add(createToolBar(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("Plik"));
        menuBar.add(new JMenu("Edycja"));
        menuBar.add(new JMenu("Widok"));
        menuBar.add(new JMenu("Obliczenia"));
        menuBar.add(new JMenu("Pomoc"));
        return menuBar;
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolSaveButton = JButtonUtil.registerButton("Save", "save.png");
        printButton = JButtonUtil.registerButton("Print", "print.png");
        closeButton = JButtonUtil.registerButton("Open", "open.png");
        toolAddButton = JButtonUtil.registerButton("Add", "add.png");
        toolClearButton = JButtonUtil.registerButton("Paste", "delete.png");
        toolFillButton = JButtonUtil.registerButton("Fill", "network.png");
        toolSumButton = JButtonUtil.registerButton("Sum", "sum.png");
        toolAvgButton = JButtonUtil.registerButton("Average", "avg.png");
        toolMinButton = JButtonUtil.registerButton("Min", "min.png");
        toolMaxButton = JButtonUtil.registerButton("Max", "max.png");
        helpButton = JButtonUtil.registerButton("Help", "help.png");
        aboutButton = JButtonUtil.registerButton("About", "info.png");

        toolBar.add(toolSaveButton);
        toolBar.add(printButton);
        toolBar.add(closeButton);
        toolBar.addSeparator(new Dimension(10, 0));
        toolBar.add(toolAddButton);
        toolBar.add(toolClearButton);
        toolBar.add(toolFillButton);
        toolBar.addSeparator(new Dimension(10, 0));
        toolBar.add(toolSumButton);
        toolBar.add(toolAvgButton);
        toolBar.add(toolMinButton);
        toolBar.add(toolMaxButton);
        toolBar.addSeparator(new Dimension(10, 0));
        toolBar.add(helpButton);
        toolBar.add(aboutButton);;

        return toolBar;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        inputPanel.add(new JLabel("Wprowadź liczbę:"), gbc);
        gbc.gridx = 1;
        inputField = new JTextField("0");
        inputField.setPreferredSize(new Dimension(60, 20));
        inputPanel.add(inputField, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Numer wiersza:"), gbc);
        gbc.gridx = 3;
        rowSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        rowSpinner.setPreferredSize(new Dimension(60, 20));
        inputPanel.add(rowSpinner, gbc);

        gbc.gridx = 4;
        inputPanel.add(new JLabel("Numer kolumny:"), gbc);
        gbc.gridx = 5;
        colSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        colSpinner.setPreferredSize(new Dimension(60, 20));
        inputPanel.add(colSpinner, gbc);

        mainPanel.add(inputPanel);

        JPanel tableAndButtonsPanel = new JPanel(new BorderLayout());
        String[] cols = {"1", "2", "3", "4", "5"};
        Object[][] data = new Object[5][5];
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                data[i][j] = 0;

        table = new JTable(new DefaultTableModel(data, cols));
        table.setRowHeight(30);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(600, 150));

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        insertButton = JButtonUtil.registerButton(
                JButtonUtil.ButtonType.DEFAULT, "Dodaj", "add.png", new Dimension(120, 24));
        clearButton = JButtonUtil.registerButton(
                JButtonUtil.ButtonType.DEFAULT, "Wyzeruj", "delete.png", new Dimension(120, 24));
        fillButton = JButtonUtil.registerButton(
                JButtonUtil.ButtonType.DEFAULT, "Wypełnij", "network.png", new Dimension(120, 24));
        saveButton = JButtonUtil.registerButton(
                JButtonUtil.ButtonType.DEFAULT, "Zapisz", "save.png", new Dimension(120, 24));

        buttonPanel.add(insertButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(fillButton);
        buttonPanel.add(saveButton);

        tableAndButtonsPanel.add(tableScrollPane, BorderLayout.CENTER);
        tableAndButtonsPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(tableAndButtonsPanel);

        JPanel operationsPanel = new JPanel();
        operationsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 10));
        operationsPanel.add(new JLabel("Obliczenia"));
        comboBox = new JComboBox<>(new String[]{"", "Suma", "Średnia", "Minimum", "Maksimum"});
        operationsPanel.add(comboBox);

        calculateButton = new JButton("Oblicz");
        operationsPanel.add(calculateButton);

        mainPanel.add(operationsPanel);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        resultPanel.add(new JLabel("Uzyskany rezultat"), BorderLayout.NORTH);
        resultArea = new JTextArea(4, 50);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        mainPanel.add(resultPanel);
        return mainPanel;
    }

    private JPanel createStatusBar() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        statusLeft = new JLabel("Info   Start aplikacji");
        statusRight = new JLabel("Status   ON");
        statusPanel.add(statusLeft, BorderLayout.WEST);
        statusPanel.add(statusRight, BorderLayout.EAST);
        return statusPanel;
    }



    public void updateTable(int[][] matrix) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                table.setValueAt(matrix[i][j], i, j);
            }
        }
    }
    public int getSelectedRow() {
        return (Integer) rowSpinner.getValue() - 1;
    }

    public int getSelectedColumn() {
        return (Integer) colSpinner.getValue() - 1;
    }

    public void updateStatus(String message) {
        statusLeft.setText("Info   " + message);
    }
}
