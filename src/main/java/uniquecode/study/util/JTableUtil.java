package uniquecode.study.util;

import javax.swing.*;

public class JTableUtil {
    public static int sum(JTable jTable) {
        int sum = 0;
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            for (int j = 0; j < jTable.getRowCount(); j++) {
                int value = getInteger(jTable.getValueAt(j, i));
                sum += value;
            }
        }
        return sum;
    }

    public static double average(JTable jTable) {
        int sum = 0;
        int count = 0;
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            for (int j = 0; j < jTable.getRowCount(); j++) {
                int value = getInteger(jTable.getValueAt(j, i));
                sum += value;
                count++;
            }
        }
        return count > 0 ? (double) sum / count : 0;
    }

    public static int max(JTable jTable) {
        Integer maxValue = null;
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            for (int j = 0; j < jTable.getRowCount(); j++) {
                int max = getInteger(jTable.getValueAt(j, i));
                if (maxValue == null || max > maxValue) {
                    maxValue = max;
                }
            }
        }
        return maxValue;
    }

    public static int min(JTable jTable) {
        Integer minValue = null;
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            for (int j = 0; j < jTable.getRowCount(); j++) {
                int number = getInteger(jTable.getValueAt(j, i));
                if (minValue == null || number < minValue) {
                    minValue = number;
                }
            }
        }
        return minValue;
    }

    public static double getDouble(Object o) {
        try {
            return Double.parseDouble(o.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getInteger(Object o) {
        try {
            return Integer.parseInt(o.toString());
        } catch (Exception e) {
            return 0;
        }
    }

}
