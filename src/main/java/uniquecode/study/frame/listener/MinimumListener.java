package uniquecode.study.frame.listener;

import uniquecode.study.Main;
import uniquecode.study.api.service.ServiceInjector;
import uniquecode.study.frame.FrameCache;
import uniquecode.study.util.JTableUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinimumListener implements ActionListener {
    private final Main main;
    private final ServiceInjector serviceInjector;
    private final FrameCache frameCache;
    public MinimumListener(Main main, ServiceInjector serviceInjector) {
        this.main = main;
        this.serviceInjector = serviceInjector;
        this.frameCache = (FrameCache) serviceInjector.get("frame-cache");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JTable jTable = (JTable) this.frameCache.getComponents("table").getFirst();
        JTextArea jTextArea = (JTextArea) this.frameCache.getComponents("result-area").getFirst();
        jTextArea.append("Minimum: "+ JTableUtil.min(jTable)+"\n");
    }
}
