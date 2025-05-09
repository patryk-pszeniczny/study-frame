package uniquecode.study.util;

import javax.swing.*;

public class JMenuUtil {
    public static void registerMenuItem(JMenuBar jMenuBar, String display){
        jMenuBar.add(new JMenu(display));
    }
}
