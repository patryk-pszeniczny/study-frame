package uniquecode.study.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class JButtonUtil {
    public enum ButtonType {
        DEFAULT, ICON
    }
    public static void registerButton(ButtonType buttonType, JComponent jToolBar, String display, String iconSource, Dimension dimension,
                                      ActionListener actionListener) {
        Icon icon = getIcon(iconSource);
        JButton button;
        if(buttonType==ButtonType.ICON){
            button=new JButton(icon);
        }else {
            button = new JButton(display, icon);
        }
        button.setSize(dimension);
        button.setPreferredSize(dimension);
        button.setToolTipText(display);
        if(actionListener!=null){
            button.addActionListener(actionListener);
        }
        jToolBar.add(button);
    }
    public static void registerButton(ButtonType buttonType, JComponent jToolBar, String display, String iconSource) {
        registerButton(buttonType, jToolBar, display, iconSource, new Dimension(64, 64), null);
    }
    public static void registerButtonDefault(JComponent jToolBar, String display, String iconSource, Dimension dimension) {
        registerButton(ButtonType.DEFAULT, jToolBar, display, iconSource, dimension, null);
    }
    public static void registerButtonDefault(JComponent jToolBar, String display, String iconSource, Dimension dimension,
                                             ActionListener actionListener) {
        registerButton(ButtonType.DEFAULT, jToolBar, display, iconSource, dimension, actionListener);
    }
    public static void registerButton(JComponent jToolBar, String display, String iconSource) {
        registerButton(ButtonType.ICON, jToolBar, display, iconSource, new Dimension(64, 64), null);
    }
    public static void registerButton(JComponent jToolBar, String display, String iconSource, ActionListener actionListener) {
        registerButton(ButtonType.ICON, jToolBar, display, iconSource, new Dimension(64, 64), actionListener);
    }
    public static void registerButton(ButtonType buttonType, JComponent jToolBar, String display, String iconSource, int width, int height) {
        registerButton(buttonType, jToolBar, display, iconSource, new Dimension(width, height), null);
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
