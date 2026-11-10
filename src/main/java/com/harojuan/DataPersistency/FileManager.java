package com.harojuan.DataPersistency;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;


import com.harojuan.QuotationModel.QuotationTable;

public final class FileManager{
    
    private static FileManager instance;
    private static String path = "";
    public final String FILE_EXTENSION = ".qte";

    private JFileChooser fileChooser;

    boolean wasEdited = false;
    


    private FileManager(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        fileChooser = new JFileChooser();
    }

    public static FileManager getInstance(){
        if(instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    public void markEdit(){
        wasEdited = true;
    }

    public void markNotEdited(){
        wasEdited = false;
    }

    public boolean getEdit(){
        return wasEdited;
    }

    public void Save(QuotationTable entryToSave){
        if(path == ""){
            SaveAs(entryToSave);
        }
        else{
            try{
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path));
                outputStream.writeObject(entryToSave);
                wasEdited = false;
                outputStream.close();
                JOptionPane.showMessageDialog(null, "Archivo guardado", "Guardado",JOptionPane.INFORMATION_MESSAGE);
            }
            catch(IOException ioe){
                JOptionPane.showMessageDialog(null, "Error al guardar el archivo", "No se pudo guardar el archivo",JOptionPane.ERROR_MESSAGE);
            }     
        }
    }

    public void SaveAs(QuotationTable entryToSave){
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int response = fileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            path = fileChooser.getSelectedFile().getAbsolutePath();
            if(!path.endsWith(FILE_EXTENSION)){
                path += FILE_EXTENSION;
            }
            Save(entryToSave);
        }
    }

    public QuotationTable Load(QuotationTable tableEntry)
    {
        QuotationTable table;
        try{
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = fileChooser.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                SaveBeforeClosing(tableEntry);
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()));
                table = (QuotationTable)inputStream.readObject();
                inputStream.close();
                path = fileChooser.getSelectedFile().getAbsolutePath();
                fileChooser.setCurrentDirectory(fileChooser.getSelectedFile().getParentFile());
                wasEdited = false;
                return table;
            }
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "No se puede acceder al archivo", "Error al abrir el archivo", JOptionPane.ERROR_MESSAGE);
        }
        catch(ClassNotFoundException cnfe){
            JOptionPane.showMessageDialog(null, "El tipo de archivo es incorrecto o no se puede leer", "Error al abrir el archivo", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void Close(DefaultTableModel tableModel, QuotationTable tableEntry, boolean keepPath){
        SaveBeforeClosing(tableEntry);
        for(int i = tableModel.getRowCount() - 1; i>=0;i--){
            tableModel.removeRow(i);
            tableEntry.getEntries().remove(i);
        }
        wasEdited = false;
        if(!keepPath){
            path = "";
        }
    }

    public void SaveBeforeClosing(QuotationTable tableEntry){
        if(wasEdited){
            int response = JOptionPane.showConfirmDialog(null, "¿Guardar antes de cerrar?", "Cerrar el archivo", JOptionPane.YES_NO_CANCEL_OPTION);
            if(response == JOptionPane.YES_OPTION){
                Save(tableEntry);
            }
        }
    }
}
