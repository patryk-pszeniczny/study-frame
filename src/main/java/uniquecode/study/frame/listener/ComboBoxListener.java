package uniquecode.study.frame.listener;

import uniquecode.study.Main;
import uniquecode.study.api.service.ServiceInjector;
import uniquecode.study.frame.FrameCache;
import uniquecode.study.util.JTableUtil;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboBoxListener implements ActionListener {
    private final Main main;
    private final ServiceInjector serviceInjector;
    private final FrameCache frameCache;
    public ComboBoxListener(Main main, ServiceInjector serviceInjector) {
        this.main = main;
        this.serviceInjector = serviceInjector;
        this.frameCache = (FrameCache) serviceInjector.get("frame-cache");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox comboBox = (JComboBox) this.frameCache.getComponents("math-combo-box").getFirst();
        if(comboBox==null)return;
        Object o = comboBox.getSelectedItem();
        if(o == null)return;
        String selectedItem = o.toString();
        if(selectedItem.isBlank())return;
        JTable jTable = (JTable) this.frameCache.getComponents("table").getFirst();
        JTextArea jTextArea = (JTextArea) this.frameCache.getComponents("result-area").getFirst();
        switch (selectedItem){
            case "Suma"-> jTextArea.append("Suma: "+JTableUtil.sum(jTable)+"\n");
            case "Średnia" -> jTextArea.append("Średnia: "+JTableUtil.average(jTable)+"\n");
            case "Maximum" -> jTextArea.append("Maximum: "+JTableUtil.max(jTable)+"\n");
            case "Minimum" -> jTextArea.append("Minimum: "+JTableUtil.min(jTable)+"\n");
        }
    }
}
