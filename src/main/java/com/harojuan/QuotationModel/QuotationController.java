package com.harojuan.QuotationModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.harojuan.Attachments.AttachmentExport;
import com.harojuan.DataPersistency.FileManager;

public class QuotationController {
    private QuotationTable model;
    private QuotationView view;
    boolean isUpdatingTable = false;

    public QuotationTable getModel(){return model;}

    public QuotationController(QuotationTable model, QuotationView view){
        this.model = model;
        this.view = view;

        view.btnSubmit.addActionListener(e->{
            TryCreateEntry(view.tfCantidad.getText(), view.tfUM.getText(), view.tfName.getText(), view.tfDescripcion.getText(),
             view.tfPU.getText(), view.tfPU1.getText(),view.tfPU2.getText(), view.tfPU3.getText());
        });

        view.jmiNewItem.addActionListener(e->{
            int result = JOptionPane.showOptionDialog(null, "¿Cerrar archivo actual y abrir nuevo?", "Crear nuevo archivo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);
            if(result == JOptionPane.YES_OPTION){
                FileManager.getInstance().Close(view.tableModel, model, false);
                view.datePicker.setDate(LocalDate.now());
                view.tfQuotationNumber.setText("");
                result = JOptionPane.showOptionDialog(null, "¿Guardar nuevo archivo", "Nuevo archivo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);
                if(result == JOptionPane.YES_OPTION){
                    FileManager.getInstance().Save(model);
                }
                UpdateTotals();
            }
        });

        view.jmiExportPDF.addActionListener(e->{
            QuotationExport exportWindow = new QuotationExport(model);
        });

        view.jmiExportAttachments.addActionListener(e->{
            AttachmentExport attachmentExport = new AttachmentExport(this);
        });

        view.jmiSaveItem.addActionListener(e->{
            FileManager.getInstance().Save(model);
        });  
        
        view.jmiSaveItemAs.addActionListener(e->{
            FileManager.getInstance().SaveAs(model);
        });

        view.jmiCloseItem.addActionListener(e->{
                int result = JOptionPane.showOptionDialog(null, "¿Cerrar archivo actual?", "Cerrar archivo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);
            if(result == JOptionPane.YES_OPTION){
                FileManager.getInstance().Close(view.tableModel, model, false);
                view.datePicker.setDate(LocalDate.now());
                view.tfQuotationNumber.setText("");
                UpdateTotals();
            }
        });

        view.jmiLoadItem.addActionListener(e->{
            QuotationTable loadingTable = FileManager.getInstance().Load(model);

            if( loadingTable == null){
                return;
            }

            if(loadingTable.entries.isEmpty() || loadingTable.getEntries() == null){
                int response = JOptionPane.showOptionDialog(null, "¿El archivo esta vacio, desea cargar?", "Archivo vacio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);
                if(response != JOptionPane.YES_OPTION){
                    return;
                }
            }
                FileManager.getInstance().Close(view.tableModel, model,true);
                isUpdatingTable = true;
                for(int i = 0; i<loadingTable.entries.size(); i++){
                    AddEntry(loadingTable.getEntries().get(i));
                }
                isUpdatingTable = false;
                UpdateTotals();
                model.date = loadingTable.date;
                model.quotationNumber = loadingTable.quotationNumber;

                view.datePicker.setDate(model.date);
                view.tfQuotationNumber.setText(model.quotationNumber);
                FileManager.getInstance().markNotEdited();
        });

        view.datePicker.addDateChangeListener(e->{
            model.date = view.datePicker.getDate();
            FileManager.getInstance().markEdit();
        });

        view.tfReceiver.getDocument().addDocumentListener(new DocumentListener() { 
            @Override
            public void insertUpdate(DocumentEvent e) {
                model.receiver = view.tfReceiver.getText();
                FileManager.getInstance().markEdit();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                model.receiver = view.tfReceiver.getText();
                FileManager.getInstance().markEdit();                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        
        view.tfQuotationNumber.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                model.quotationNumber = view.tfQuotationNumber.getText();
                FileManager.getInstance().markEdit();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                model.quotationNumber = view.tfQuotationNumber.getText();
                FileManager.getInstance().markEdit();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        
        view.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e){
                if(FileManager.getInstance().getEdit()){
                    int result = JOptionPane.showConfirmDialog(null, "El archivo ah sido modificado ¿Guardar antes de salir?", "Saliendo", JOptionPane.YES_NO_CANCEL_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        FileManager.getInstance().Save(model);
                        view.dispose();
                    }
                    else if(result == JOptionPane.NO_OPTION){
                        view.dispose();
                    }
                } 
                else{
                    int result = JOptionPane.showConfirmDialog(null, "¿Desea cerrar la aplicacion?", "Saliendo", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        view.dispose();
                    }
                }
            } 
        });

        view.tableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e){ 
                if(e.getType() != TableModelEvent.UPDATE || isUpdatingTable ){
                    UpdateTotals();
                    return;
                }
                //if(view.contentTable.isEditing()){return;}
                float floatParser = 0;
                String cellValue = view.tableModel.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                switch (e.getColumn()) {
                    case 1://Amount case
                        try{
                            floatParser = Float.parseFloat(cellValue);
                            model.entries.get(e.getFirstRow()).amount = floatParser;
                        }
                        catch(Exception ie){
                            view.contentTable.getCellEditor().cancelCellEditing();
                            view.contentTable.setValueAt(model.entries.get(e.getFirstRow()).baseUnitPrice, e.getFirstRow(), e.getColumn());
                            JOptionPane.showMessageDialog(null, "El valor de cantidad debe ser un numero","Valor invalido",JOptionPane.ERROR_MESSAGE);
                            return;
                        } 
                        
                        break;
                    case 2: //U.M case 
                        model.entries.get(e.getFirstRow()).measureUnit = cellValue;
                        break;
                    case 3://Article name case
                        model.entries.get(e.getFirstRow()).name = cellValue;
                        break;
                    case 4://Description case
                        model.entries.get(e.getFirstRow()).description = cellValue;
                        break;
                    case 5://PU
                        try{
                            floatParser = Float.parseFloat(cellValue);
                            model.entries.get(e.getFirstRow()).baseUnitPrice = floatParser;
                        }
                        catch(Exception ie){
                            view.contentTable.getCellEditor().cancelCellEditing();
                            view.contentTable.setValueAt(model.entries.get(e.getFirstRow()).baseUnitPrice, e.getFirstRow(), e.getColumn());
                            JOptionPane.showMessageDialog(null, "El valor de PU debe ser un numero","Valor invalido",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        break;
                    case 7://PU 1
                        try{
                            floatParser = Float.parseFloat(cellValue);
                            model.entries.get(e.getFirstRow()).unitPrice1 = floatParser;
                        }
                        catch(Exception ie){
                              view.contentTable.getCellEditor().cancelCellEditing();
                            view.contentTable.setValueAt(model.entries.get(e.getFirstRow()).unitPrice1, e.getFirstRow(), e.getColumn());
                            JOptionPane.showMessageDialog(null, "El valor de PU 1 debe ser un numero","Valor invalido",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        break;
                    case 9://PU 2
                        try{
                            floatParser = Float.parseFloat(cellValue);
                            model.entries.get(e.getFirstRow()).unitPrice2 = floatParser;
                        }
                        catch(Exception ie){
                            view.contentTable.getCellEditor().cancelCellEditing();
                            view.contentTable.setValueAt(model.entries.get(e.getFirstRow()).unitPrice2, e.getFirstRow(), e.getColumn());
                            JOptionPane.showMessageDialog(null, "El valor de PU 2 debe ser un numero","Valor invalido",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        break;
                      case 11://PU 3
                        try{
                            floatParser = Float.parseFloat(cellValue);
                            model.entries.get(e.getFirstRow()).unitPrice3 = floatParser;
                        }
                        catch(Exception ie){
                            view.contentTable.getCellEditor().cancelCellEditing();
                            view.contentTable.setValueAt(model.entries.get(e.getFirstRow()).unitPrice3, e.getFirstRow(), e.getColumn());
                            JOptionPane.showMessageDialog(null, "El valor de PU 3 debe ser un numero","Valor invalido",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        break;
                    default:
                        break;
                }
                FileManager.getInstance().markEdit();
                UpdateTable();
                UpdateTotals();
            }
        });


        //Delete operations 
        view.btnDelete.addActionListener(e->{
            DeleteEntry();
        });

        UpdateTotals();
    }

   

    void TryCreateEntry(String amount, String measureUnit,String name, String description, 
        String baseUnitPrice, String unitPrice1, String unitPrice2,String unitPrice3){
        

        if(!amount.trim().isEmpty() &&
            !measureUnit.trim().isEmpty() &&
            !name.trim().isEmpty() &&
            !description.trim().isEmpty() &&
            !baseUnitPrice.trim().isEmpty() &&
            !unitPrice1.trim().isEmpty() &&
            !unitPrice2.trim().isEmpty() &&
            !unitPrice3.trim().isEmpty()){

        }else{
            JOptionPane.showMessageDialog(null, "Informacion incompleta, no se puede generar entrada", "Entrada incompleta", JOptionPane.ERROR_MESSAGE);
            return;
        }

        float fAmount = 0;
        float fBaseUnitPrice = 0;
        float fUnitPrice1 = 0;
        float fUnitPrice2 = 0;
        float fUnitPrice3 = 0;
        boolean isEntryReady = true;
        
        try {
            fAmount = Float.parseFloat(amount);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "La cantidad no es un numero", "Entrada no generada", JOptionPane.ERROR_MESSAGE);
            isEntryReady = false;
        }
        try {
            fBaseUnitPrice = Float.parseFloat(baseUnitPrice);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El precio base no es un numero", "Entrada no generada", JOptionPane.ERROR_MESSAGE);
            isEntryReady = false;
        }
        try {
            fUnitPrice1 = Float.parseFloat(unitPrice1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El precio 1 no es un numero", "Entrada no generada", JOptionPane.ERROR_MESSAGE);
            isEntryReady = false;
        }
        try {
            fUnitPrice2 = Float.parseFloat(unitPrice2);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El precio 2 no es un numero", "Entrada no generada", JOptionPane.ERROR_MESSAGE);
            isEntryReady = false;
        }
        try {
            fUnitPrice3 = Float.parseFloat(unitPrice3);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El precio 3 no es un numero", "Entrada no generada", JOptionPane.ERROR_MESSAGE);
            isEntryReady = false;
        }

        if(isEntryReady){
            QuotationEntry modelEntry = new QuotationEntry(view.tableModel.getRowCount() + 1, fAmount, measureUnit,name, description, 
                                                            fBaseUnitPrice, fUnitPrice1, fUnitPrice2, fUnitPrice3);
            AddEntry(modelEntry);
        }
    }


    void AddEntry(QuotationEntry newEntry){
        FileManager.getInstance().markEdit(); 
        //Add view entry
        Vector<String> entry = new Vector<String>();
        entry.add(Integer.toString(newEntry.partida));
        entry.add(Float.toString(newEntry.amount));
        entry.add(newEntry.measureUnit);
        entry.add(newEntry.name);
        entry.add(newEntry.description);
        entry.add(String.format("%.2f",newEntry.baseUnitPrice));
        entry.add(String.format("%.2f",newEntry.getTotal(QuotationEntry.BASE)));
        entry.add(String.format("%.2f",newEntry.unitPrice1));
        entry.add(String.format("%.2f",newEntry.getTotal(QuotationEntry.PRICE_1)));
        entry.add(String.format("%.2f",newEntry.unitPrice2));
        entry.add(String.format("%.2f",newEntry.getTotal(QuotationEntry.PRICE_2)));
        entry.add(String.format("%.2f",newEntry.unitPrice3));
        entry.add(String.format("%.2f",newEntry.getTotal(QuotationEntry.PRICE_3)));
        entry.add(String.format("%.2f",newEntry.getAveragePrice()));
        entry.add(String.format("%.2f",newEntry.getTotal(QuotationEntry.AVERAGE)));

        view.tableModel.addRow(entry);
        view.tfCantidad.setText("");
        view.tfUM.setText("");
        view.tfName.setText("");
        view.tfDescripcion.setText("");
        view.tfPU.setText("");
        view.tfPU1.setText("");
        view.tfPU2.setText("");
        view.tfPU3.setText("");

        //Add model entry
        model.addEntry(newEntry);
        UpdateTotals();
    }

    void DeleteEntry(){
        if(model.entries.isEmpty()){ JOptionPane.showMessageDialog(null, "La tabla esta vacia, nada que eliminar", "Tabla vacia",JOptionPane.INFORMATION_MESSAGE);return;}
        try {
            int parser = Integer.parseInt(view.inputDeleteRow.getText());
            parser -= 1;
            int totalRows =  view.tableModel.getRowCount();
            view.inputDeleteRow.setText("");
            if(0 <= parser && parser < totalRows){
                int response = JOptionPane.showOptionDialog(null, "Se borrara la fila " + Integer.toString(parser + 1) + " ¿Desea continuar?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);
                if(response == JOptionPane.YES_OPTION){
                    isUpdatingTable = true;
                    view.tableModel.removeRow(parser);
                    model.entries.remove(parser);
                    UpdateTable();
                    UpdateTotals();
                    isUpdatingTable = false;
                    FileManager.getInstance().markEdit();
                    JOptionPane.showMessageDialog(null, "Fila borrada correctamente", "Borrado", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
            else{
                JOptionPane.showMessageDialog(null, "La fila a borrar debe estar entre 1 y " + Integer.toString(totalRows), "Error al borrar", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "La fila a borrar debe ser su numero de partida", "Error al borrar", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    void UpdateTable(){
        isUpdatingTable = true;
        QuotationEntry entry;
        for(int i = 0; i < model.getEntries().size(); i++){
            entry = model.getEntries().get(i);
            view.tableModel.setValueAt(i + 1, i, 0);
            view.tableModel.setValueAt(entry.amount, i, 1);
            view.tableModel.setValueAt(entry.measureUnit, i, 2);
            view.tableModel.setValueAt(entry.name, i, 3);
            view.tableModel.setValueAt(entry.description, i, 4);
            view.tableModel.setValueAt(entry.baseUnitPrice, i, 5);
            view.tableModel.setValueAt(entry.getTotal(QuotationEntry.BASE), i, 6);
            view.tableModel.setValueAt(entry.unitPrice1, i, 7);
            view.tableModel.setValueAt(entry.getTotal(QuotationEntry.PRICE_1), i, 8);
            view.tableModel.setValueAt(entry.unitPrice2, i, 9);
            view.tableModel.setValueAt(entry.getTotal(QuotationEntry.PRICE_2), i, 10);
            view.tableModel.setValueAt(entry.unitPrice3, i, 11);
            view.tableModel.setValueAt(entry.getTotal(QuotationEntry.PRICE_3), i, 12);
            view.tableModel.setValueAt(entry.getAveragePrice(), i, 13);
            view.tableModel.setValueAt(entry.getTotal(QuotationEntry.AVERAGE), i, 14);
        }
        isUpdatingTable = false;
    }

    void UpdateTotals(){
        //Base
        view.totalTableModel.setValueAt(String.format("%.2f",model.getSubtotal(QuotationEntry.BASE)), 0, 1);
        view.totalTableModel.setValueAt(String.format("%.2f",model.getIVA(QuotationEntry.BASE)), 1, 1);
        view.totalTableModel.setValueAt(String.format("%.2f",model.getGrandTotal(QuotationEntry.BASE)), 2, 1);

        //Price 1
        view.totalTableModel.setValueAt(String.format("%.2f",model.getSubtotal(QuotationEntry.PRICE_1)), 0, 3);
        view.totalTableModel.setValueAt(String.format("%.2f",model.getIVA(QuotationEntry.PRICE_1)), 1, 3);
        view.totalTableModel.setValueAt(String.format("%.2f",model.getGrandTotal(QuotationEntry.PRICE_1)), 2, 3);

        //Price 2
        view.totalTableModel.setValueAt(String.format("%.2f",model.getSubtotal(QuotationEntry.PRICE_2)), 0, 5);
        view.totalTableModel.setValueAt(String.format("%.2f",model.getIVA(QuotationEntry.PRICE_2)), 1, 5);
        view.totalTableModel.setValueAt(String.format("%.2f",model.getGrandTotal(QuotationEntry.PRICE_2)), 2, 5);

        //Price 3
        view.totalTableModel.setValueAt(String.format("%.2f",model.getSubtotal(QuotationEntry.PRICE_3)), 0, 7);
        view.totalTableModel.setValueAt(String.format("%.2f",model.getIVA(QuotationEntry.PRICE_3)), 1,7);
        view.totalTableModel.setValueAt(String.format("%.2f",model.getGrandTotal(QuotationEntry.PRICE_3)), 2, 7);

        //Average
        view.totalTableModel.setValueAt(String.format("%.2f",model.getSubtotal(QuotationEntry.AVERAGE)), 0, 9);
        view.totalTableModel.setValueAt(String.format("%.2f",model.getIVA(QuotationEntry.AVERAGE)), 1,9);
        view.totalTableModel.setValueAt(String.format("%.2f",model.getGrandTotal(QuotationEntry.AVERAGE)), 2, 9);
    }


    void onClose(WindowEvent e){
        if(FileManager.getInstance().getEdit()){
            JOptionPane.showMessageDialog(view, e, null, 0);
        }
    }
}
