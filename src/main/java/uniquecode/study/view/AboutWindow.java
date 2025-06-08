package uniquecode.study.view;

import javax.swing.*;
import java.awt.*;

public class AboutWindow extends JFrame {

    public AboutWindow() {
        setTitle("O aplikacji");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setBackground(new Color(248, 248, 248));
        textArea.setMargin(new Insets(20, 20, 20, 20));
        textArea.setText(
                "Aplikacja Macierzowa\n\n" +
                        "To narzędzie edukacyjne do pracy z macierzą 5x5.\n" +
                        "Pozwala wprowadzać dane, wykonywać operacje matematyczne\n" +
                        "oraz tworzyć wykresy w sposób wizualny i przejrzysty.\n\n" +
                        "Funkcjonalności:\n" +
                        "- Wstawianie wartości\n" +
                        "- Czyszczenie danych\n" +
                        "- Operacje: suma, średnia, minimum, maksimum\n" +
                        "- Wypełnianie losowe\n" +
                        "- Wykresy kołowe (JFreeChart)\n" +
                        "- Tip of the Day\n" +
                        "- Zapis/Odczyt danych\n\n" +
                        "Technologie:\n" +
                        "- Java 17+\n" +
                        "- Swing (GUI)\n" +
                        "- JFreeChart\n" +
                        "- Architektura MVC\n\n" +
                        "Autor: Patryk Pszeniczny\n" +
                        "Semestr: 4\n" +
                        "Rok: 2025\n" +
                        "Przedmiot: Projektowanie Aplikacji (proj)"
        );

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }
}
