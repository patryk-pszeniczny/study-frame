package uniquecode.study.frame;

import uniquecode.study.Main;
import uniquecode.study.api.service.ServiceInjector;
import uniquecode.study.util.JButtonUtil;
import uniquecode.study.util.JMenuUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class FrameLoader extends JFrame {
    private final Main main;
    private final ServiceInjector serviceInjector;
    private final FrameManager frameManager;
    public FrameLoader(Main main) {
        super("Moje Okno");
        this.main = main;
        this.serviceInjector = main.getServiceInjector();
        this.frameManager = (FrameManager) serviceInjector.get("frame-manager");
        this.loadApplication();
        System.out.println("test");
    }
    public void loadApplication(){
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setJMenuBar(createMenuBar());
        this.add(createToolBar(), BorderLayout.NORTH);
        this.add(createMainPanel(), BorderLayout.CENTER);
        this.setVisible(true);

    }
    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenuUtil.registerMenuItem(menuBar, "Plik");
        JMenuUtil.registerMenuItem(menuBar, "Edycja");
        JMenuUtil.registerMenuItem(menuBar, "Widok");
        JMenuUtil.registerMenuItem(menuBar, "Obliczenia");
        JMenuUtil.registerMenuItem(menuBar, "Pomoc");
        return menuBar;
    }
    private JToolBar createToolBar(){
        JToolBar toolBar = new JToolBar();
        JButtonUtil.registerButton(toolBar, "Save", "save.png");
        JButtonUtil.registerButton(toolBar, "Print", "print.png");
        JButtonUtil.registerButton(toolBar, "Close", "open.png");
        toolBar.addSeparator(new Dimension(10, 0));
        JButtonUtil.registerButton(toolBar, "Copy", "add.png");
        JButtonUtil.registerButton(toolBar, "Paste", "delete.png");
        JButtonUtil.registerButton(toolBar, "Cut", "network.png");
        toolBar.addSeparator(new Dimension(10, 0));
        JButtonUtil.registerButton(toolBar, "Sum", "sum.png");
        JButtonUtil.registerButton(toolBar, "Average", "avg.png");
        JButtonUtil.registerButton(toolBar, "Min", "min.png");
        JButtonUtil.registerButton(toolBar, "Max", "max.png");
        toolBar.addSeparator(new Dimension(10, 0));
        JButtonUtil.registerButton(toolBar, "Help", "help.png");
        JButtonUtil.registerButton(toolBar, "About", "info.png");
        return toolBar;
    }
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));

        JPanel row1 = new JPanel();
        row1.add(new JLabel("Wprowadź liczbę:"));
        JTextField inputField = new JTextField("0", 5);
        inputField.setPreferredSize(new Dimension(50, 20));
        row1.add(inputField);

        row1.add(new JLabel("Numer wiersza:"));
        JSpinner rowSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        rowSpinner.setPreferredSize(new Dimension(100, 20));
        row1.add(rowSpinner);

        row1.add(new JLabel("Numer kolumny:"));
        JSpinner colSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        colSpinner.setPreferredSize(new Dimension(100, 20));
        row1.add(colSpinner);

        inputPanel.add(row1);

        JPanel row2 = new JPanel();
        JButton addButton = new JButton("Dodaj");
        JButton clearButton = new JButton("Wyczyść");
        JButton fillButton = new JButton("Wypełnij");
        JButton saveButton = new JButton("Zapisz");

        //addButton.addActionListener(e -> addValue());
        //clearButton.addActionListener(e -> clearTable());

        row2.add(addButton);
        row2.add(clearButton);
        row2.add(fillButton);
        row2.add(saveButton);

        inputPanel.add(row2);

        // Tabela
        String[] columns = {"1", "2", "3", "4", "5"};
        Object[][] data = new Object[5][5];
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                data[i][j] = 0;

        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        JTable table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        JTextArea resultArea = new JTextArea(5, 50);
        resultArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(resultArea);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScroll, BorderLayout.CENTER);
        panel.add(resultScroll, BorderLayout.SOUTH);

        return panel;
    }

}
