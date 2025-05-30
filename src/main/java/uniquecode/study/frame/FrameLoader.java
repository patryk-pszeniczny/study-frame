package uniquecode.study.frame;

import uniquecode.study.Main;
import uniquecode.study.api.service.ServiceInjector;
import uniquecode.study.frame.listener.*;
import uniquecode.study.util.JButtonUtil;
import uniquecode.study.util.JMenuUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class FrameLoader extends JFrame {
    private final Main main;
    private final ServiceInjector serviceInjector;
    private final FrameManager frameManager;
    private final FrameCache frameCache;
    public FrameLoader(Main main) {
        super("Moje Okno");
        this.main = main;
        this.serviceInjector = main.getServiceInjector();
        this.frameManager = (FrameManager) serviceInjector.get("frame-manager");
        this.frameCache = (FrameCache) serviceInjector.get("frame-cache");
        this.loadApplication();
    }
    public void loadApplication(){
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setJMenuBar(createMenuBar());
        add(createToolBar(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);
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
        JButtonUtil.registerButton(toolBar, "Sum", "sum.png", new SumListener(this.main, this.serviceInjector));
        JButtonUtil.registerButton(toolBar, "Average", "avg.png", new AvgListener(this.main, this.serviceInjector));
        JButtonUtil.registerButton(toolBar, "Min", "min.png", new MinimumListener(this.main, this.serviceInjector));
        JButtonUtil.registerButton(toolBar, "Max", "max.png", new MaximumListener(this.main, this.serviceInjector));
        toolBar.addSeparator(new Dimension(10, 0));
        JButtonUtil.registerButton(toolBar, "Help", "help.png");
        JButtonUtil.registerButton(toolBar, "About", "info.png");
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
        JTextField inputField = new JTextField("0");
        inputField.setPreferredSize(new Dimension(60, 20));
        inputPanel.add(inputField, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Numer wiersza:"), gbc);
        gbc.gridx = 3;
        JSpinner rowSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        rowSpinner.setPreferredSize(new Dimension(60, 20));
        inputPanel.add(rowSpinner, gbc);

        gbc.gridx = 4;
        inputPanel.add(new JLabel("Numer kolumny:"), gbc);
        gbc.gridx = 5;
        JSpinner colSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        colSpinner.setPreferredSize(new Dimension(60, 20));
        inputPanel.add(colSpinner, gbc);

        mainPanel.add(inputPanel);

        JPanel tableAndButtonsPanel = new JPanel(new BorderLayout());

        String[] cols = {"1", "2", "3", "4", "5"};
        Object[][] data = new Object[5][5];
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                data[i][j] = 0;
                //data[i][j] = ThreadLocalRandom.current().nextInt(0, 100);

        JTable table = new JTable(new DefaultTableModel(data, cols));
        this.frameCache.addComponent("table", table);
        table.setRowHeight(30);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(600, 150));
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JButtonUtil.registerButtonDefault(buttonPanel, "Dodaj", "add_16.png",
                new Dimension(150, 10), new SumListener(this.main, this.serviceInjector));
        JButtonUtil.registerButtonDefault(buttonPanel, "Wyzeruj", "delete.png",
                new Dimension(150, 10));
        JButtonUtil.registerButtonDefault( buttonPanel, "Wypełnij", "network.png",
                new Dimension(150, 10));
        JButtonUtil.registerButtonDefault(buttonPanel, "Zapisz", "save.png",
                new Dimension(150, 10));

        tableAndButtonsPanel.add(tableScrollPane, BorderLayout.CENTER);
        tableAndButtonsPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(tableAndButtonsPanel);

        JPanel operationsPanel = new JPanel();
        operationsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 10));
        operationsPanel.add(new JLabel("Obliczenia"));
        JComboBox jComboBox = new JComboBox<>(new String[]{"", "Suma", "Średnia", "Minimum", "Maksimum"});
        this.frameCache.addComponent("math-combo-box", jComboBox);
        operationsPanel.add(jComboBox);

        JButton buttonCombo = new JButton("Oblicz");
        buttonCombo.addActionListener(new ComboBoxListener(this.main, this.serviceInjector));
        operationsPanel.add(buttonCombo);

        mainPanel.add(operationsPanel);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        resultPanel.add(new JLabel("Uzyskany rezultat"), BorderLayout.NORTH);
        JTextArea resultArea = new JTextArea(4, 50);
        this.frameCache.addComponent("result-area", resultArea);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        mainPanel.add(resultPanel);

        return mainPanel;
    }

    private JPanel createStatusBar() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        statusPanel.add(new JLabel("Info   Start aplikacji"), BorderLayout.WEST);
        statusPanel.add(new JLabel("Status   ON"), BorderLayout.EAST);
        return statusPanel;
    }


}
