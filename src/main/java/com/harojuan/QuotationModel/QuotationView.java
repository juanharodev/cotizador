package com.harojuan.QuotationModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.harojuan.Components.WrapTextCellRenderer;
import com.harojuan.Utils.MultilineCellEditor;

public class QuotationView extends JFrame{
    public JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem jmiNewItem;
    JMenuItem jmiLoadItem;
    JMenuItem jmiCloseItem;
    JMenuItem jmiSaveItem;
    JMenuItem jmiSaveItemAs;
    JMenu exportMenu;
    JMenuItem jmiExportPDF;
    JMenuItem jmiExportAttachments;
    JPanel inputPanel;
    JLabel lblCantidad;
    JTextField tfCantidad;
    JLabel lblUM;
    JTextField tfUM;
    JLabel lblName;
    JTextField tfName;
    JLabel lblDescripcion;
    JTextArea tfDescripcion;
    JLabel lblPU;
    JTextField tfPU;
    JLabel lblPU1;
    JTextField tfPU1;
    JLabel lblPU2;
    JTextField tfPU2;
    JLabel lblPU3;
    JTextField tfPU3;
    JButton btnSubmit;
    Vector<Object> colums;
    DefaultTableModel tableModel;
    JTable contentTable;
    JScrollPane tableScrollPane;
    Vector<String> totalColumns;
    DefaultTableModel totalTableModel;
    JTable totalTable;
    JScrollPane totalPanel;
    
    JPanel northPanel;
    
    
    JPanel quoatationDataPanel;
    JLabel lblQuotationNumber;
    JTextField  tfQuotationNumber;
    JLabel lblDate;
    DatePicker datePicker;
    JLabel lblReceiver;
    JTextArea tfReceiver;
   
    JPanel deletePanel;
    JTextField inputDeleteRow;
    JButton btnDelete;
    JLabel lblDelete;


    BevelBorder border;

    public QuotationView(){
        this.setTitle("Cotizador");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());

        border= new BevelBorder(BevelBorder.RAISED);

        menuBar = new JMenuBar();
        
        fileMenu = new JMenu("Archivo");
        fileMenu.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            
        jmiNewItem = new JMenuItem("Nuevo");
        fileMenu.add(jmiNewItem);

        jmiLoadItem = new JMenuItem("Abrir");
        fileMenu.add(jmiLoadItem);

        jmiCloseItem = new JMenuItem("Cerrar");
        fileMenu.add(jmiCloseItem);

        jmiSaveItem = new JMenuItem("Guardar");
        fileMenu.add(jmiSaveItem);

        jmiSaveItemAs = new JMenuItem("Guardar Como");
        fileMenu.add(jmiSaveItemAs);
        
        menuBar.add(fileMenu);

        exportMenu = new JMenu("Exportar");
        exportMenu.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        jmiExportPDF = new JMenuItem("Exportar PDF");
        jmiExportAttachments = new JMenuItem("Exportar Anexos");
        exportMenu.add(jmiExportPDF);
        exportMenu.add(jmiExportAttachments);
        
        menuBar.add(exportMenu);
        menuBar.setBorder(new EtchedBorder(EtchedBorder.RAISED));

        northPanel = new JPanel(new GridLayout(3,1));

        quoatationDataPanel =new JPanel(new FlowLayout(FlowLayout.CENTER));
        quoatationDataPanel.setPreferredSize(new Dimension(200,125));
        
        lblQuotationNumber = new JLabel("Numero de cotizacion");
        quoatationDataPanel.add(lblQuotationNumber);

        tfQuotationNumber = new JTextField();
        tfQuotationNumber.setPreferredSize(new Dimension(70,20));
        tfQuotationNumber.setBorder(border); 
        tfQuotationNumber.setBackground(Color.WHITE);
        quoatationDataPanel.add(tfQuotationNumber);
        
        lblDate = new JLabel("Fecha de cotizacion");
        quoatationDataPanel.add(lblDate);

        Locale locale = new Locale( "es");
        DatePickerSettings settings = new DatePickerSettings(locale);
        settings.setAllowKeyboardEditing(false);
        datePicker = new DatePicker(settings);
        datePicker.setDateToToday();
        quoatationDataPanel.add(datePicker);

        lblReceiver = new JLabel("Destinatario");
        quoatationDataPanel.add(lblReceiver);

        tfReceiver = new JTextArea(3,20);
        // tfReceiver.setPreferredSize(new Dimension(550,40));
        tfReceiver.setBorder(border); 
        tfReceiver.setBackground(Color.WHITE);
        JScrollPane receiverScrollPane = new JScrollPane(tfReceiver, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        receiverScrollPane.setPreferredSize(new Dimension(300, 100));
        quoatationDataPanel.add(receiverScrollPane);

        inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBackground(Color.LIGHT_GRAY);
        inputPanel.setPreferredSize(new Dimension(100,60));
        
        lblCantidad = new JLabel("Cantidad");
        inputPanel.add(lblCantidad);
        tfCantidad = new JTextField();
        tfCantidad.setPreferredSize(new Dimension(70,20));
        tfCantidad.setBorder(border);
        tfCantidad.setBackground(Color.WHITE);
        inputPanel.add(tfCantidad);
        
        lblUM = new JLabel("UM");
        inputPanel.add(lblUM);
        
        tfUM = new JTextField();
        tfUM.setPreferredSize(new Dimension(70,20));
        tfUM.setBorder(border);
        tfUM.setBackground(Color.WHITE);
        inputPanel.add(tfUM);

        lblName= new JLabel("Articulo");
        inputPanel.add(lblName);
        tfName = new JTextField();
        tfName.setPreferredSize(new Dimension(150,20));
        tfName.setBorder(border);
        inputPanel.add(tfName);

        lblDescripcion= new JLabel("Descripcion");
        inputPanel.add(lblDescripcion);
        tfDescripcion = new JTextArea(3,30);
        tfDescripcion.setLineWrap(true);
        tfDescripcion.setWrapStyleWord(true);
        //tfDescripcion.setPreferredSize(new Dimension(350,50));
        tfDescripcion.setBorder(border);
        JScrollPane jspDescripcion = new JScrollPane(tfDescripcion);
        jspDescripcion.setBorder(null);
        inputPanel.add(jspDescripcion);


        lblPU= new JLabel("PU");
        inputPanel.add(lblPU);
        tfPU = new JTextField();
        tfPU.setPreferredSize(new Dimension(70,20));
        tfPU.setBorder(border);
        inputPanel.add(tfPU);


        lblPU1= new JLabel("PU 1");
        inputPanel.add(lblPU1);
        tfPU1 = new JTextField();
        tfPU1.setPreferredSize(new Dimension(70,20));
        tfPU1.setBorder(border);
        inputPanel.add(tfPU1);

        lblPU2= new JLabel("PU 2");
        inputPanel.add(lblPU2);
        tfPU2 = new JTextField();
        tfPU2.setPreferredSize(new Dimension(70,20));
        tfPU2.setBorder(border);
        inputPanel.add(tfPU2);

        lblPU3= new JLabel("PU 3");
        inputPanel.add(lblPU3);
        tfPU3 = new JTextField();
        tfPU3.setPreferredSize(new Dimension(70,20));
        tfPU3.setBorder(border);
        inputPanel.add(tfPU3);

        btnSubmit = new JButton("Agregar");
        btnSubmit.setBackground(Color.WHITE);
        btnSubmit.setBorder(border);
        inputPanel.add(btnSubmit);

        //Delete panel 
        deletePanel = new JPanel(new FlowLayout());
        deletePanel.setPreferredSize(new Dimension(100,30));
        deletePanel.setBackground(Color.gray);
        lblDelete = new JLabel("Fila a borrar");
        lblDelete.setForeground(Color.WHITE);
        deletePanel.add(lblDelete);
        inputDeleteRow = new JTextField();
        inputDeleteRow.setPreferredSize(new Dimension(60,20));
        deletePanel.add(inputDeleteRow);
        btnDelete = new JButton("BORRAR");
        btnDelete.setPreferredSize(new Dimension(60,20));
        btnDelete.setBorder(border);
        deletePanel.add(btnDelete);

        //Products table 
        colums = new Vector<Object>();
        colums.add("Partida");
        colums.add("Cantidad");
        colums.add("UM");
        colums.add("Articulo");
        colums.add("Descripcion");
        colums.add("PU");
        colums.add("Monto");
        colums.add("PU 1");
        colums.add("Monto 1"); 
        colums.add("PU 2");
        colums.add("Monto 2");
        colums.add("PU 3");
        colums.add("Monto 3");
        colums.add("PU promedio");
        colums.add("Monto promedio");

        tableModel = new DefaultTableModel(colums,0){
            @Override 
            public boolean isCellEditable(int row, int column)
            {
                if(column == 0 || column == 6 || column == 8 || column == 10 || column == 12 ||column == 13 || column == 14){
                    return false;
                }
                return true;
            }
        };
        
        contentTable = new JTable(tableModel);
        contentTable.getColumnModel().getColumn(3).setCellEditor(new MultilineCellEditor());
        contentTable.getColumnModel().getColumn(4).setCellEditor(new MultilineCellEditor());
        contentTable.getColumnModel().getColumn(3).setCellRenderer(new WrapTextCellRenderer());
        contentTable.getColumnModel().getColumn(3).setPreferredWidth(300);
        contentTable.getColumnModel().getColumn(4).setCellRenderer(new WrapTextCellRenderer());
        contentTable.getColumnModel().getColumn(4).setPreferredWidth(500);
        contentTable.setAlignmentX(JTable.CENTER_ALIGNMENT);
        contentTable.setAlignmentY(JTable.CENTER_ALIGNMENT);
        contentTable.getTableHeader().setReorderingAllowed(false);
        tableScrollPane = new JScrollPane(contentTable);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        totalColumns = new Vector<String>();
        totalColumns.add("PU");
        totalColumns.add("Monto");
        totalColumns.add("PU 1");
        totalColumns.add("Monto 1");
        totalColumns.add("PU 2");
        totalColumns.add("Monto 2");
        totalColumns.add("PU 3");
        totalColumns.add("Monto 3");
        totalColumns.add("PU Promedio");
        totalColumns.add("Monto Promedio");
        
        totalTableModel = new DefaultTableModel(totalColumns, 3){
            @Override 
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        for(int i = 0; i < totalColumns.size(); i += 2){
            totalTableModel.setValueAt("Subtotal", 0, i);
            totalTableModel.setValueAt("IVA16%", 1, i);
            totalTableModel.setValueAt("Total", 2, i);
        }
        totalTable = new JTable(totalTableModel);
        totalTable.getTableHeader().setReorderingAllowed(false);
        totalPanel = new JScrollPane(totalTable);
        totalPanel.setPreferredSize(new Dimension(100,75));

        this.setJMenuBar(menuBar);

        
        this.add(inputPanel, BorderLayout.NORTH);
        
        northPanel.add(quoatationDataPanel);
        northPanel.add(inputPanel);
        northPanel.add(deletePanel);
        

        this.add(northPanel, BorderLayout.NORTH);
        this.add(tableScrollPane, BorderLayout.CENTER);
        this.add(totalPanel,BorderLayout.SOUTH);

        this.setVisible(true);
    }
}
