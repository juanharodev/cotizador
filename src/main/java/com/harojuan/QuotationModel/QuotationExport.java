package com.harojuan.QuotationModel;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;

import com.harojuan.ExportFormats.Companies;
import com.harojuan.ExportFormats.QuotationExporter;
import com.harojuan.ExportFormats.ConcreteFormats.QuotationCALIMEO;
import com.harojuan.ExportFormats.ConcreteFormats.QuotationDDV;
import com.harojuan.ExportFormats.ConcreteFormats.QuotationENVI;
import com.harojuan.ExportFormats.ConcreteFormats.QuotationMETRIK;
import com.harojuan.ExportFormats.ConcreteFormats.QuotationOFFSET;
import com.harojuan.ExportFormats.ConcreteFormats.QuotationPATSSIE;
import com.harojuan.ExportFormats.ConcreteFormats.QuotationPINESGDL;
import com.harojuan.ExportFormats.ConcreteFormats.QuotationREYNA;
import com.harojuan.ExportFormats.ConcreteFormats.QuotationWIX;

public class QuotationExport extends JFrame {

    private String exportPath;
    private JFileChooser fileChooser;

    public QuotationExport(QuotationTable table){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setTitle("Exportar PDF");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(500,250);
        this.setLayout(new GridLayout(5,1));
        this.setResizable(false);

        fileChooser = new JFileChooser();

        JPanel firstCompany = new JPanel();
        JLabel firstCompanyName = new JLabel("Compañia 1:");
        firstCompany.add(firstCompanyName);
        JComboBox firstCompanySelection = new JComboBox<>(Companies.values());
        firstCompanySelection.setSelectedIndex(0);
        firstCompany.add(firstCompanySelection);
        JTextField firstFilename = new JTextField();
        JScrollPane firstFilePane = new JScrollPane(firstFilename);
        firstFilePane.setPreferredSize(new Dimension(300,40));
        firstCompany.add(firstFilePane);
        this.add(firstCompany);

        JPanel secondCompany = new JPanel();
        JLabel secondCompanyName = new JLabel("Compañia 2:");
        secondCompany.add(secondCompanyName);
        JComboBox secondCompanySelection = new JComboBox<>(Companies.values());
        secondCompanySelection.setSelectedIndex(1);
        secondCompany.add(secondCompanySelection);
        JTextField secondFilename = new JTextField();
        JScrollPane secondFilePane = new JScrollPane(secondFilename);
        secondFilePane.setPreferredSize(new Dimension(300,40));
        secondCompany.add(secondFilePane);
        this.add(secondCompany);
        
        JPanel thirdCompany = new JPanel();
        JLabel thirdCompanyName = new JLabel("Compañia 3:");
        thirdCompany.add(thirdCompanyName);
        JComboBox thirdCompanySelection = new JComboBox<>(Companies.values());
        thirdCompanySelection.setSelectedIndex(2);
        thirdCompany.add(thirdCompanySelection);
        JTextField thirdFilename = new JTextField();
        JScrollPane thirdFilePane = new JScrollPane(thirdFilename);
        thirdFilePane.setPreferredSize(new Dimension(300,40));
        thirdCompany.add(thirdFilePane);        
        this.add(thirdCompany);

        JPanel exportSettings = new JPanel();
        JLabel saveDirectory = new JLabel();
        saveDirectory.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JScrollPane pathScroll = new JScrollPane(saveDirectory);
        pathScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pathScroll.setPreferredSize(new Dimension(300,40));
        exportSettings.add(pathScroll);
        JButton chooseDirectory = new JButton("Directorio");
        chooseDirectory.setBorder(new BevelBorder(BevelBorder.RAISED));
        chooseDirectory.setPreferredSize(new Dimension(100,40));
        chooseDirectory.addActionListener(e->{
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int response = fileChooser.showOpenDialog(this);
            if(response == JFileChooser.APPROVE_OPTION){
                exportPath = fileChooser.getSelectedFile().getAbsolutePath();
                fileChooser.setCurrentDirectory(fileChooser.getSelectedFile());
                saveDirectory.setText(exportPath);
            }
        }); 
        exportSettings.add(chooseDirectory);
        this.add(exportSettings);

        JButton exportButton = new JButton("Exportar");
        exportButton.setPreferredSize(new Dimension(200, 40));
        exportButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        exportButton.addActionListener(e->{
            
            if(exportPath == null || exportPath.isEmpty()){
                JOptionPane.showMessageDialog(this, "Seleccione una ubicacion para guardar los archivos");
                return;
            }
            if(!firstFilename.getText().trim().isEmpty()){
                String fileName1 = firstFilename.getText().trim();
                QuotationExporter quotationExporter1 = GetQuotatation(firstCompanySelection.getSelectedIndex());
                if(quotationExporter1 == null){return;}

                quotationExporter1.ExporQuotation(table, 1, exportPath, fileName1);

                JOptionPane.showMessageDialog(this, "Archivo 1 exportado");
            }
            else{
                JOptionPane.showMessageDialog(this, "Nombre de archivo 1 es invalido");
            }
            if(!secondFilename.getText().trim().isEmpty()){
                String fileName2 = secondFilename.getText().trim();
                QuotationExporter quotationExporter2 = GetQuotatation(secondCompanySelection.getSelectedIndex());
                if(quotationExporter2 == null){return;}
                quotationExporter2.ExporQuotation(table, 2, exportPath, fileName2);

                JOptionPane.showMessageDialog(this, "Archivo 2 exportado");
            }
            else{
                JOptionPane.showMessageDialog(this, "Nombre de archivo 2 es invalido");
            }
            if(!secondFilename.getText().trim().isEmpty()){
                String fileName3 = thirdFilename.getText().trim();
                QuotationExporter quotationExporter3 = GetQuotatation(thirdCompanySelection.getSelectedIndex());
                if(quotationExporter3 == null){return;}
                quotationExporter3.ExporQuotation(table, 3, exportPath, fileName3);

                JOptionPane.showMessageDialog(this, "Archivo 3 exportado");
            }
            else{
                JOptionPane.showMessageDialog(this, "Nombre de archivo 3 es invalido");
            }
        });
        this.add(exportButton);

        this.pack();
        this.setVisible(true);
    }

    private QuotationExporter GetQuotatation(int type){
                
        switch (type) {
            case 0:
                return new QuotationDDV();
            case 1:
                return new QuotationENVI();
            case 2:
                return new QuotationMETRIK();
            case 3:
                return new QuotationWIX();
            case 4:
                return new QuotationPATSSIE();
            case 5:
                return new QuotationOFFSET();
            case 6:
                return new QuotationCALIMEO();
                case 7:
                return new QuotationPINESGDL();
            case 8:
                return new QuotationREYNA();   
            default:
                JOptionPane.showMessageDialog(this, "No se puede obtener la plantilla del archivo");
                return null;
        }           
    }
        private String GetQuotatationName(int type){
                
        switch (type) {
            case 0:
                return "DDV";
            case 1:
                return "ENVI";
            case 2:
                return "METRIK";
            case 3:
                return "WIX";
            case 4:
                return "PATSSIE";
            case 5:
                return "OFFSET";
            case 6:
                return "CALIMEO";
                case 7:
                return "PINESGDL";
            case 8:
                return "REYNA";   
            default:
                JOptionPane.showMessageDialog(this, "No se puede obtener la plantilla del archivo");
                return null;
        }           
    }
}
