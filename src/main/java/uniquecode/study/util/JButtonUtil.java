package uniquecode.study.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class JButtonUtil {
    public static void registerButton(JToolBar jToolBar, String display, String iconSource) {
        Icon icon = getIcon(iconSource);
        JButton button = new JButton(icon);
        button.setSize(new Dimension(64, 64));
        button.setToolTipText(display);
        jToolBar.add(button);
    }
    public static Icon getIcon(String iconSource) {
        if(!iconSource.matches("(?i)^.+\\.(jpg|jpeg|png|gif|bmp|webp|tiff)$")){
            return UIManager.getIcon(iconSource);
        }
        URL resource = JButtonUtil.class.getResource("/" + iconSource);
        if (resource == null) {
            System.err.println("Nie znaleziono ikony: " + iconSource);
            return null;
        }
        ImageIcon icon = new ImageIcon(resource);
        icon.setImage(icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        return icon;
    }
}
