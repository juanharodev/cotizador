package com.harojuan.Utils;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MultilineCellEditor extends AbstractCellEditor implements TableCellEditor {

    private final JTextArea textArea;
    private final JScrollPane scrollPane;

    public MultilineCellEditor() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMinimumSize(new Dimension(100,150));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);

        // ENTER → finish editing
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        textArea.getInputMap().put(enter, "finishEditing");
        textArea.getActionMap().put("finishEditing", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCellEditing();
            }
        });

        // CTRL + ENTER → insert newline
        KeyStroke ctrlEnter = KeyStroke.getKeyStroke("ctrl ENTER");
        textArea.getInputMap().put(ctrlEnter, "insertNewLine");
        textArea.getActionMap().put("insertNewLine", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("\n");
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return textArea.getText();
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {

        textArea.setText(value == null ? "" : value.toString());
        textArea.setCaretPosition(textArea.getText().length());
        return scrollPane;
    }
}
