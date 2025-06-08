package uniquecode.study.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class JButtonUtil {
    public enum ButtonType {
        DEFAULT, ICON
    }
    public static JButton registerButton(ButtonType buttonType, String display, String iconSource,
                                        Dimension dimension) {
        Icon icon = getIcon(iconSource, dimension.height);
        JButton button;
        if(buttonType==ButtonType.ICON){
            button=new JButton(icon);
        }else {
            button = new JButton(display, icon);
        }
        button.setSize(dimension);
        button.setPreferredSize(dimension);
        button.setToolTipText(display);
        return button;
    }
    public static JButton registerButton(ButtonType buttonType, String display, String iconSource) {
        return registerButton(buttonType, display, iconSource, new Dimension(64, 64));
    }
    public static JButton registerButtonDefault(String display, String iconSource, Dimension dimension) {
        return registerButton(ButtonType.DEFAULT, display, iconSource, dimension);
    }
    public static JButton registerButton(String display, String iconSource) {
        return registerButton(ButtonType.ICON, display, iconSource, new Dimension(48, 48));
    }
    public static JButton registerButton(ButtonType buttonType, String display, String iconSource, int width, int height) {
        return registerButton(ButtonType.DEFAULT, display, iconSource, new Dimension(width, height));
    }
    public static Icon getIcon(String iconSource, int height) {
        if (!iconSource.matches("(?i)^.+\\.(jpg|jpeg|png|gif|bmp|webp|tiff)$")) {
            return UIManager.getIcon(iconSource);
        }

        URL resource = JButtonUtil.class.getResource("/" + iconSource);
        if (resource == null) {
            System.err.println("Nie znaleziono ikony: " + iconSource);
            return null;
        }

        ImageIcon icon = new ImageIcon(resource);
        Image scaled = icon.getImage().getScaledInstance((int) (height*0.8), (int) (height*0.8), Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

}
