package uniquecode.study.view;

import com.l2fprod.common.swing.JOutlookBar;
import com.l2fprod.common.swing.tips.DefaultTip;
import com.l2fprod.common.swing.tips.DefaultTipModel;
import com.toedter.calendar.JDateChooser;
import com.l2fprod.common.swing.JTipOfTheDay;
import lombok.Getter;
import uniquecode.study.util.JButtonUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

@Getter
public class MainView extends JFrame {
    //Komponenty główne interfejsu
    private JTextField inputField;
    private JSpinner rowSpinner, colSpinner;
    private JTable table;
    private JTextArea resultArea;
    private JComboBox<String> comboBox;

    //Przyciski główne (UI)
    private JButton insertButton, clearButton, saveButton, calculateButton, fillButton;

    //Przyciski z ToolBara
    private JButton toolAddButton, toolClearButton, toolFillButton, toolSaveButton;
    private JButton toolSumButton, toolAvgButton, toolMinButton, toolMaxButton;
    private JButton printButton, closeButton, helpButton, aboutButton, toolChartButton;

    //Pasek menu (JMenuBar)
    private JMenu fileMenu, editMenu, viewMenu, calculationsMenu, helpMenu;

    //Elementy menu (JMenuItem)
    private JMenuItem openMenuItem, saveMenuItem, printMenuItem;
    private JMenuItem increaseMenuItem, decreaseMenuItem, fillMenuItem;
    private JMenuItem sumMenuItem, averageMenuItem, minMenuItem, maxMenuItem;
    private JMenuItem tipOfTheDayMenuItem, chartMenuItem;
    private JMenuItem aboutMenuItem, helpMenuItem;

    //Pasek statusu
    private JLabel statusLeft, statusRight;

    public MainView() {
        super("Moje Okno - Aplikacja Tablicowa");

        initializeGUI();
        this.showTipsOnStartup();
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

        this.fileMenu = new JMenu("Plik");
        openMenuItem = new JMenuItem("Otwórz");
        saveMenuItem = new JMenuItem("Zapisz");
        printMenuItem = new JMenuItem("Drukuj");
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(printMenuItem);
        menuBar.add(fileMenu);

        this.editMenu = new JMenu("Edycja");
        increaseMenuItem = new JMenuItem("Zwiększ");
        decreaseMenuItem = new JMenuItem("Zmniejsz");
        fillMenuItem = new JMenuItem("Wypełnij");
        editMenu.add(increaseMenuItem);
        editMenu.add(decreaseMenuItem);
        editMenu.add(fillMenuItem);
        menuBar.add(editMenu);

        this.viewMenu = new JMenu("Widok");
        tipOfTheDayMenuItem = new JMenuItem("Porada Dnia");
        chartMenuItem = new JMenuItem("Wykres");
        viewMenu.add(tipOfTheDayMenuItem);
        viewMenu.add(chartMenuItem);
        menuBar.add(viewMenu);

        this.calculationsMenu = new JMenu("Obliczenia");
        sumMenuItem = new JMenuItem("Suma");
        averageMenuItem = new JMenuItem("Średnia");
        minMenuItem = new JMenuItem("Minimum");
        maxMenuItem = new JMenuItem("Maksimum");
        calculationsMenu.add(sumMenuItem);
        calculationsMenu.add(averageMenuItem);
        calculationsMenu.add(minMenuItem);
        calculationsMenu.add(maxMenuItem);
        menuBar.add(calculationsMenu);

        this.helpMenu = new JMenu("Pomoc");
        aboutMenuItem = new JMenuItem("O aplikacji");
        helpMenuItem = new JMenuItem("Pomoc");
        helpMenu.add(aboutMenuItem);
        helpMenu.add(helpMenuItem);
        menuBar.add(helpMenu);

        return menuBar;
    }
    public void showTipsOnStartup() {
        try (InputStream input = getClass().getResourceAsStream("/tips/tips.properties")) {
            Properties props = new Properties();
            props.load(input);

            DefaultTipModel model = new DefaultTipModel();
            for (String key : props.stringPropertyNames()) {
                model.add(new DefaultTip(key, props.getProperty(key)));
            }
            JTipOfTheDay tip = new JTipOfTheDay(model);
            tip.setToolTipText("Porady");
            tip.showDialog(this);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Nie udało się załadować porad.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolSaveButton = JButtonUtil.registerButton("Save", "images/save.png");
        printButton = JButtonUtil.registerButton("Print", "images/print.png");
        closeButton = JButtonUtil.registerButton("Open", "images/open.png");
        toolAddButton = JButtonUtil.registerButton("Add", "images/add.png");
        toolClearButton = JButtonUtil.registerButton("Paste", "images/delete.png");
        toolFillButton = JButtonUtil.registerButton("Fill", "images/network.png");
        toolSumButton = JButtonUtil.registerButton("Sum", "images/sum.png");
        toolAvgButton = JButtonUtil.registerButton("Average", "images/avg.png");
        toolMinButton = JButtonUtil.registerButton("Min", "images/min.png");
        toolMaxButton = JButtonUtil.registerButton("Max", "images/max.png");
        helpButton = JButtonUtil.registerButton("Help", "images/help.png");
        aboutButton = JButtonUtil.registerButton("About", "images/info.png");
        toolChartButton = JButtonUtil.registerButton("Chart", "images/chart.png");
        toolBar.add(toolSaveButton);
        toolBar.add(printButton);
        toolBar.add(closeButton);
        toolBar.addSeparator(new Dimension(15, 0));
        toolBar.add(toolAddButton);
        toolBar.add(toolClearButton);
        toolBar.add(toolFillButton);
        toolBar.addSeparator(new Dimension(15, 0));
        toolBar.add(toolSumButton);
        toolBar.add(toolAvgButton);
        toolBar.add(toolMinButton);
        toolBar.add(toolMaxButton);
        toolBar.addSeparator(new Dimension(15, 0));
        toolBar.add(toolChartButton);
        toolBar.add(helpButton);
        toolBar.add(aboutButton);

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


        insertButton = JButtonUtil.registerButton(
                JButtonUtil.ButtonType.DEFAULT, "Dodaj", "images/add.png", new Dimension(120, 24));
        clearButton = JButtonUtil.registerButton(
                JButtonUtil.ButtonType.DEFAULT, "Wyzeruj", "images/delete.png", new Dimension(120, 24));
        fillButton = JButtonUtil.registerButton(
                JButtonUtil.ButtonType.DEFAULT, "Wypełnij", "images/network.png", new Dimension(120, 24));
        saveButton = JButtonUtil.registerButton(
                JButtonUtil.ButtonType.DEFAULT, "Zapisz", "images/save.png", new Dimension(120, 24));

        JOutlookBar bar = new JOutlookBar();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(insertButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(fillButton);
        buttonPanel.add(saveButton);
        bar.addTab("Zarządzanie", null, buttonPanel);

        tableAndButtonsPanel.add(tableScrollPane, BorderLayout.CENTER);
        tableAndButtonsPanel.add(bar, BorderLayout.EAST);

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

        JPanel calendarPanel = new JPanel();
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 10));
        calendarPanel.add(new JLabel("Wybierz datę:"));

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd"); // format: rrrr-mm-dd
        calendarPanel.add(dateChooser);

        dateChooser.getDateEditor().addPropertyChangeListener("date", e -> {
            java.util.Date selectedDate = dateChooser.getDate();
            if (selectedDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(selectedDate);
                resultArea.append("Wybrano datę: " + formattedDate + "\n");
                updateStatus("Wybrano datę: " + formattedDate);
            }
        });

        mainPanel.add(calendarPanel);
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
