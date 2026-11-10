package com.harojuan.Attachments;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
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
import javax.swing.filechooser.FileNameExtensionFilter;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.harojuan.QuotationModel.QuotationController;
import com.harojuan.Utils.PdfMerger;
import com.harojuan.Utils.PdfNumbering;
import com.lowagie.text.pdf.PdfReader;


public class AttachmentExport extends JFrame {

    private String exportPath;
    private String validationFile;
    private String csfFile;
    private String imssOcofFile;
    private String imssAcuseFile;
    private String infonavitFile;
    private String rupcFile;
    private String satFile;
    private String powerOfAttorneyFile;
    private JFileChooser fileChooser;
    private JFileChooser validationFileChooser;
    private JFileChooser csfFileChooser;
    private JFileChooser imssOcofFileChooser;
    private JFileChooser imssAcuseFileChooser;
    private JFileChooser infonavitFileChooser;
    private JFileChooser rupcFileChooser;
    private JFileChooser satFileChooser;
    private JFileChooser powerOfAttorneyFileChooser;
    boolean isBusy = false;


    JTextField tfNumber;
    JTextField tfType; 
    JTextField tfName;
    JTextField tfCommittee;
    JTextField tfResponsible;
    JTextField tfObligations;
    JTextField tfDeliveryPlace;
    JTextField tfWarranties;
    JTextField tfCertifications;
    JTextField tfPaymentConditions;
    JTextField tfSignPlace;
    JTextField tfPlace;
    JTextField tfPresent;
    JTextField tfReceiver;
    DatePicker datePicker;
    DatePicker deliveryDatePicker;


    public AttachmentExport(QuotationController controller){
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
        this.setTitle("Anexos");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(10,2));
        this.setResizable(false);

        //Data input
        JPanel pnlNumber = new JPanel();
        JLabel lblNumber = new JLabel("Número");
        lblNumber.setPreferredSize(new Dimension(150,50));
        tfNumber = new JTextField();
        tfNumber.setPreferredSize(new Dimension(300,40));
        pnlNumber.add(lblNumber);
        pnlNumber.add(tfNumber);
        this.add(pnlNumber);

        JPanel pnlType = new JPanel();
        JLabel lblType = new JLabel("Tipo");
        lblType.setPreferredSize(new Dimension(150,50));
        tfType = new JTextField();
        tfType.setPreferredSize(new Dimension(300,40));
        pnlType.add(lblType);
        pnlType.add(tfType);
        this.add(pnlType);

        JPanel pnlName = new JPanel();
        JLabel lblName = new JLabel("Licitación");
        lblName.setPreferredSize(new Dimension(150,50));
        tfName = new JTextField();
        tfName.setPreferredSize(new Dimension(300,40));
        pnlName.add(lblName);
        pnlName.add(tfName);
        this.add(pnlName);

        JPanel pnlCommittee = new JPanel();
        JLabel lblCommittee = new JLabel("Comité");
        lblCommittee.setPreferredSize(new Dimension(150,50));
        tfCommittee = new JTextField();
        tfCommittee.setPreferredSize(new Dimension(300,40));
        pnlCommittee.add(lblCommittee);
        pnlCommittee.add(tfCommittee);
        this.add(pnlCommittee);
        
        JPanel pnlResponsible = new JPanel();
        JLabel lblResponsible = new JLabel("Responsable");
        lblResponsible.setPreferredSize(new Dimension(150,50));
        tfResponsible = new JTextField();
        tfResponsible.setPreferredSize(new Dimension(300,40));
        pnlResponsible.add(lblResponsible);
        pnlResponsible.add(tfResponsible);
        this.add(pnlResponsible);

        JPanel pnlObligations = new JPanel();
        JLabel lblObligations = new JLabel("Obligaciones");
        lblObligations.setPreferredSize(new Dimension(150,50));
        tfObligations = new JTextField();
        tfObligations.setPreferredSize(new Dimension(300,40));
        pnlObligations.add(lblObligations);
        pnlObligations.add(tfObligations);
        this.add(pnlObligations);

        JPanel pnldeliveryPlace = new JPanel();
        JLabel lbldeliveryPlace = new JLabel("Lugar de entrega");
        lbldeliveryPlace.setPreferredSize(new Dimension(150,50));
        tfDeliveryPlace = new JTextField();
        tfDeliveryPlace.setPreferredSize(new Dimension(300,40));
        pnldeliveryPlace.add(lbldeliveryPlace);
        pnldeliveryPlace.add(tfDeliveryPlace);
        this.add(pnldeliveryPlace);

        JPanel pnlWarranties = new JPanel();
        JLabel lblWarranties = new JLabel("Garantias");
        lblWarranties.setPreferredSize(new Dimension(150,50));
        tfWarranties = new JTextField();
        tfWarranties.setPreferredSize(new Dimension(300,40));
        pnlWarranties.add(lblWarranties);
        pnlWarranties.add(tfWarranties);
        this.add(pnlWarranties);

        JPanel pnlCertifications = new JPanel();
        JLabel lblCertifications = new JLabel("Certificaciones");
        lblCertifications.setPreferredSize(new Dimension(150,50));
        tfCertifications = new JTextField();
        tfCertifications.setPreferredSize(new Dimension(300,40));
        pnlCertifications.add(lblCertifications);
        pnlCertifications.add(tfCertifications);
        this.add(pnlCertifications);

        JPanel pnlPaymentConditions = new JPanel();
        JLabel lblPaymentConditions = new JLabel("Condiciones de pago");
        lblPaymentConditions.setPreferredSize(new Dimension(150,50));
        tfPaymentConditions = new JTextField();
        tfPaymentConditions.setPreferredSize(new Dimension(300,40));
        pnlPaymentConditions.add(lblPaymentConditions);
        pnlPaymentConditions.add(tfPaymentConditions);
        this.add(pnlPaymentConditions);

        JPanel pnlSignPlace = new JPanel();
        JLabel lblSignPlace = new JLabel("Lugar de firma");
        lblSignPlace.setPreferredSize(new Dimension(150,50));
        tfSignPlace = new JTextField();
        tfSignPlace.setPreferredSize(new Dimension(300,40));
        pnlSignPlace.add(lblSignPlace);
        pnlSignPlace.add(tfSignPlace);
        this.add(pnlSignPlace);

        JPanel pnlPlace = new JPanel();
        JLabel lblPlace = new JLabel("Lugar");
        lblPlace.setPreferredSize(new Dimension(150,50));
        tfPlace = new JTextField();
        tfPlace.setPreferredSize(new Dimension(300,40));
        pnlPlace.add(lblPlace);
        pnlPlace.add(tfPlace);
        this.add(pnlPlace);

        JPanel pnlPresent = new JPanel();
        JLabel lblPresent = new JLabel("Presente");
        lblPresent.setPreferredSize(new Dimension(150,50));
        tfPresent = new JTextField();
        tfPresent.setPreferredSize(new Dimension(300,40));
        pnlPresent.add(lblPresent);
        pnlPresent.add(tfPresent);
        this.add(pnlPresent);

        JPanel pnlReceiver = new JPanel();
        JLabel lblReceiver = new JLabel("Dirigido a");
        lblReceiver.setPreferredSize(new Dimension(150,50));
        tfReceiver = new JTextField();
        tfReceiver.setPreferredSize(new Dimension(300,40));
        pnlReceiver.add(lblReceiver);
        pnlReceiver.add(tfReceiver);
        this.add(pnlReceiver);


        JPanel pnlDate = new JPanel();
        JLabel lblDate = new JLabel("Fecha");
        lblDate.setPreferredSize(new Dimension(150,50));
        Locale locale = new Locale( "es");
        DatePickerSettings settings = new DatePickerSettings(locale);
        settings.setAllowKeyboardEditing(false);
        settings.setSizeDatePanelMinimumHeight(200);
        settings.setSizeDatePanelMinimumWidth(200);
        datePicker = new DatePicker(settings);
        datePicker.setSize(new Dimension(400,40));
        datePicker.setDateToToday();
        pnlDate.add(lblDate);
        pnlDate.add(datePicker);
        this.add(pnlDate);

        JPanel pnlDeliveryDate = new JPanel();
        JLabel lblDeliveryDate = new JLabel("Fecha de entrega");
        lblDeliveryDate.setPreferredSize(new Dimension(150,50));
        DatePickerSettings settings2 = new DatePickerSettings(locale);
        settings2.setAllowKeyboardEditing(false);
        settings2.setSizeDatePanelMinimumHeight(200);
        settings2.setSizeDatePanelMinimumWidth(200);
        settings2.setAllowKeyboardEditing(false);
        settings2.setSizeDatePanelMinimumHeight(200);
        settings2.setSizeDatePanelMinimumWidth(200);
        deliveryDatePicker = new DatePicker(settings2);
        deliveryDatePicker.setSize(new Dimension(400,40));
        deliveryDatePicker.setDateToToday();
        pnlDeliveryDate.add(lblDeliveryDate);
        pnlDeliveryDate.add(deliveryDatePicker);
        this.add(pnlDeliveryDate);


        FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDFs (*.pdf)","pdf");
        
        //Validation file selection 
        validationFileChooser = new JFileChooser();

        validationFileChooser.addChoosableFileFilter(pdfFilter);
        validationFileChooser.setFileFilter(pdfFilter);



        
        JPanel validationSettings = new JPanel();
        JLabel validationDirectory = new JLabel();
        validationDirectory.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JScrollPane pathScrollValidation = new JScrollPane(validationDirectory);
        pathScrollValidation.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pathScrollValidation.setPreferredSize(new Dimension(300,40));
        validationSettings.add(pathScrollValidation);
        JButton btnValidation = new JButton("Validaciones");
        btnValidation.setBorder(new BevelBorder(BevelBorder.RAISED));
        btnValidation.setPreferredSize(new Dimension(100,40));
        btnValidation.addActionListener(e->{
            validationFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = validationFileChooser.showOpenDialog(this);
            if(response == JFileChooser.APPROVE_OPTION){
                    validationFile = validationFileChooser.getSelectedFile().getAbsolutePath();
                    validationFileChooser.setCurrentDirectory(validationFileChooser.getSelectedFile());
                    validationDirectory.setText(validationFile);
            }
        }); 
        validationSettings.add(btnValidation);
        this.add(validationSettings);

        rupcFileChooser = new JFileChooser();
        rupcFileChooser.addChoosableFileFilter(pdfFilter);
        rupcFileChooser.setFileFilter(pdfFilter);

        JPanel rupcSettings = new JPanel();
        JLabel rupcDirectory = new JLabel();
        rupcDirectory.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JScrollPane pathScrollrupc = new JScrollPane(rupcDirectory);
        pathScrollrupc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pathScrollrupc.setPreferredSize(new Dimension(300,40));
        rupcSettings.add(pathScrollrupc);
        JButton btnrupc = new JButton("RUPC");
        btnrupc.setBorder(new BevelBorder(BevelBorder.RAISED));
        btnrupc.setPreferredSize(new Dimension(100,40));
        btnrupc.addActionListener(e->{
            rupcFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = rupcFileChooser.showOpenDialog(this);
            if(response == JFileChooser.APPROVE_OPTION){
                    rupcFile = rupcFileChooser.getSelectedFile().getAbsolutePath();
                    rupcFileChooser.setCurrentDirectory(rupcFileChooser.getSelectedFile());
                    rupcDirectory.setText(rupcFile);
            }
        }); 
        rupcSettings.add(btnrupc);
        this.add(rupcSettings);

         imssOcofFileChooser = new JFileChooser();
        imssOcofFileChooser.addChoosableFileFilter(pdfFilter);
        imssOcofFileChooser.setFileFilter(pdfFilter);

        JPanel imssOcofSettings = new JPanel();
        JLabel imssOcofDirectory = new JLabel();
        imssOcofDirectory.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JScrollPane pathScrollimssOcof = new JScrollPane(imssOcofDirectory);
        pathScrollimssOcof.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pathScrollimssOcof.setPreferredSize(new Dimension(300,40));
        imssOcofSettings.add(pathScrollimssOcof);
        JButton btnimssOcof = new JButton("Opinión de cumplimiento imss");
        btnimssOcof.setBorder(new BevelBorder(BevelBorder.RAISED));
        btnimssOcof.setPreferredSize(new Dimension(100,40));
        btnimssOcof.addActionListener(e->{
            imssOcofFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = imssOcofFileChooser.showOpenDialog(this);
            if(response == JFileChooser.APPROVE_OPTION){
                    imssOcofFile = imssOcofFileChooser.getSelectedFile().getAbsolutePath();
                    imssOcofFileChooser.setCurrentDirectory(imssOcofFileChooser.getSelectedFile());
                    imssOcofDirectory.setText(imssOcofFile);
            }
        }); 
        imssOcofSettings.add(btnimssOcof);
        this.add(imssOcofSettings);

        imssAcuseFileChooser = new JFileChooser();
        imssAcuseFileChooser.addChoosableFileFilter(pdfFilter);
        imssAcuseFileChooser.setFileFilter(pdfFilter);

        JPanel imssAcuseSettings = new JPanel();
        JLabel imssAcuseDirectory = new JLabel();
        imssAcuseDirectory.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JScrollPane pathScrollimssAcuse = new JScrollPane(imssAcuseDirectory);
        pathScrollimssAcuse.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pathScrollimssAcuse.setPreferredSize(new Dimension(300,40));
        imssAcuseSettings.add(pathScrollimssAcuse);
        JButton btnimssAcuse = new JButton("IMSS Acuse");
        btnimssAcuse.setBorder(new BevelBorder(BevelBorder.RAISED));
        btnimssAcuse.setPreferredSize(new Dimension(100,40));
        btnimssAcuse.addActionListener(e->{
            imssAcuseFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = imssAcuseFileChooser.showOpenDialog(this);
            if(response == JFileChooser.APPROVE_OPTION){
                    imssAcuseFile = imssAcuseFileChooser.getSelectedFile().getAbsolutePath();
                    imssAcuseFileChooser.setCurrentDirectory(imssAcuseFileChooser.getSelectedFile());
                    imssAcuseDirectory.setText(imssAcuseFile);
            }
        }); 
        imssAcuseSettings.add(btnimssAcuse);
        this.add(imssAcuseSettings);

                
        satFileChooser = new JFileChooser();
        satFileChooser.addChoosableFileFilter(pdfFilter);
        satFileChooser.setFileFilter(pdfFilter);

        JPanel satSettings = new JPanel();
        JLabel satDirectory = new JLabel();
        satDirectory.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JScrollPane pathScrollsat = new JScrollPane(satDirectory);
        pathScrollsat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pathScrollsat.setPreferredSize(new Dimension(300,40));
        satSettings.add(pathScrollsat);
        JButton btnsat = new JButton("Opinión cumplimiento sat");
        btnsat.setBorder(new BevelBorder(BevelBorder.RAISED));
        btnsat.setPreferredSize(new Dimension(100,40));
        btnsat.addActionListener(e->{
            satFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = satFileChooser.showOpenDialog(this);
            if(response == JFileChooser.APPROVE_OPTION){
                    satFile = satFileChooser.getSelectedFile().getAbsolutePath();
                    satFileChooser.setCurrentDirectory(satFileChooser.getSelectedFile());
                    satDirectory.setText(satFile);
            }
        }); 
        satSettings.add(btnsat);
        this.add(satSettings);

        csfFileChooser = new JFileChooser();
        csfFileChooser.addChoosableFileFilter(pdfFilter);
        csfFileChooser.setFileFilter(pdfFilter);

        JPanel csfSettings = new JPanel();
        JLabel csfDirectory = new JLabel();
        csfDirectory.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JScrollPane pathScrollcsf = new JScrollPane(csfDirectory);
        pathScrollcsf.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pathScrollcsf.setPreferredSize(new Dimension(300,40));
        csfSettings.add(pathScrollcsf);
        JButton btncsf = new JButton("Constancia de situación fiscal");
        btncsf.setBorder(new BevelBorder(BevelBorder.RAISED));
        btncsf.setPreferredSize(new Dimension(100,40));
        btncsf.addActionListener(e->{
            csfFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = csfFileChooser.showOpenDialog(this);
            if(response == JFileChooser.APPROVE_OPTION){
                    csfFile = csfFileChooser.getSelectedFile().getAbsolutePath();
                    csfFileChooser.setCurrentDirectory(csfFileChooser.getSelectedFile());
                    csfDirectory.setText(csfFile);
            }
        }); 
        csfSettings.add(btncsf);
        this.add(csfSettings);

        infonavitFileChooser = new JFileChooser();
        infonavitFileChooser.addChoosableFileFilter(pdfFilter);
        infonavitFileChooser.setFileFilter(pdfFilter);

        JPanel infonavitSettings = new JPanel();
        JLabel infonavitDirectory = new JLabel();
        infonavitDirectory.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JScrollPane pathScrollinfonavit = new JScrollPane(infonavitDirectory);
        pathScrollinfonavit.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pathScrollinfonavit.setPreferredSize(new Dimension(300,40));
        infonavitSettings.add(pathScrollinfonavit);
        JButton btninfonavit = new JButton("Constancia de obligaciones INFONAVIT");
        btninfonavit.setBorder(new BevelBorder(BevelBorder.RAISED));
        btninfonavit.setPreferredSize(new Dimension(100,40));
        btninfonavit.addActionListener(e->{
            infonavitFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = infonavitFileChooser.showOpenDialog(this);
            if(response == JFileChooser.APPROVE_OPTION){
                    infonavitFile = infonavitFileChooser.getSelectedFile().getAbsolutePath();
                    infonavitFileChooser.setCurrentDirectory(infonavitFileChooser.getSelectedFile());
                    infonavitDirectory.setText(infonavitFile);
            }
        }); 
        infonavitSettings.add(btninfonavit);
        this.add(infonavitSettings);

        powerOfAttorneyFileChooser= new JFileChooser();
        powerOfAttorneyFileChooser.addChoosableFileFilter(pdfFilter);
        powerOfAttorneyFileChooser.setFileFilter(pdfFilter);

        JPanel powerOfAttorneySettings = new JPanel();
        JLabel powerOfAttorneyDirectory = new JLabel();
        powerOfAttorneyDirectory.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JScrollPane pathScrollpowerOfAttorney = new JScrollPane(powerOfAttorneyDirectory);
        pathScrollpowerOfAttorney.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pathScrollpowerOfAttorney.setPreferredSize(new Dimension(300,40));
        powerOfAttorneySettings.add(pathScrollpowerOfAttorney);
        JButton btnpowerOfAttorney = new JButton("Poder");
        btnpowerOfAttorney.setBorder(new BevelBorder(BevelBorder.RAISED));
        btnpowerOfAttorney.setPreferredSize(new Dimension(100,40));
        btnpowerOfAttorney.addActionListener(e->{
            powerOfAttorneyFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = powerOfAttorneyFileChooser.showOpenDialog(this);
            if(response == JFileChooser.APPROVE_OPTION){
                    powerOfAttorneyFile = powerOfAttorneyFileChooser.getSelectedFile().getAbsolutePath();
                    powerOfAttorneyFileChooser.setCurrentDirectory(powerOfAttorneyFileChooser.getSelectedFile());
                    powerOfAttorneyDirectory.setText(powerOfAttorneyFile);
            }
        }); 
        powerOfAttorneySettings.add(btnpowerOfAttorney);
        this.add(powerOfAttorneySettings);


        
        

        //File selection
        fileChooser = new JFileChooser();

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
            if(!allFieldsFilled()){JOptionPane.showMessageDialog(this, "Rellene todos los campos antes de exportar"); return;}
            if(isBusy){ JOptionPane.showMessageDialog(this, "Exportando, porfavor espere"); return;}
            try {
            isBusy = true;
            AttachmentData data = getData();
            FrontPage frontPage = new FrontPage();
            frontPage.Export(controller.getModel(), data, exportPath, "front-page.pdf");
            TechnicalAttachement anexo1 = new TechnicalAttachement();
            anexo1.Export(controller.getModel(), data, exportPath, "anexo1.pdf");
            PersonalityManifesto anexo2 = new PersonalityManifesto();
            anexo2.Export(controller.getModel(), data, exportPath, "anexo2.pdf");
            ParticipantAcreditation anexo4 = new ParticipantAcreditation();
            anexo4.Export(controller.getModel(), data, exportPath, "anexo4.pdf");
            EconomicProposal anexo5 = new EconomicProposal();
            anexo5.Export(controller.getModel(), data, exportPath, "anexo5.pdf");
            ParticipantDeclaration anexo6 = new ParticipantDeclaration();
            anexo6.Export(controller.getModel(), data, exportPath, "anexo6.pdf");
            AcceptanceLetter anexo7 = new AcceptanceLetter();
            anexo7.Export(controller.getModel(), data, exportPath, "anexo7.pdf");
            Stratification anexo8 = new Stratification();
            anexo8.Export(controller.getModel(), data, exportPath, "anexo8.pdf");
            Members anexo9 = new Members();
            anexo9.Export(controller.getModel(), data, exportPath, "anexo9.pdf");
            FiscalObligation anexo10 = new FiscalObligation();
            anexo10.Export(controller.getModel(), data, exportPath,"anexo10.pdf");
            SocialSecurityManifesto anexo11 = new SocialSecurityManifesto();
            anexo11.Export(controller.getModel(), data, exportPath, "anexo11.pdf");
            SamplesFormat anexo12 = new SamplesFormat();
            anexo12.Export(controller.getModel(), data, exportPath, "anexo12.pdf");
            PowerOfAttorney anexo13 = new PowerOfAttorney();
            anexo13.Export(controller.getModel(), data, exportPath, "anexo13.pdf");
            IDAttachment anexo14 = new IDAttachment();
            anexo14.Export(controller.getModel(), data, exportPath,"anexo14.pdf");
            ArticlesSamplesDescription anexo15 = new ArticlesSamplesDescription();
            anexo15.Export(controller.getModel(), data, exportPath, "anexo15.pdf");
            ArticleSampleTitle anexo16 = new ArticleSampleTitle();
            anexo16.Export(controller.getModel(), data, exportPath, "anexo16.pdf");
            ClarificationMeeting anexo17 = new ClarificationMeeting();
            anexo17.Export(controller.getModel(), data, exportPath, "anexo17.pdf");
            

            ArrayList<String> toMergeDoc1 = new ArrayList<String>();
            ArrayList<String> toMergeDoc1P1 = new ArrayList<String>();
            ArrayList<String> toMergeDoc1P2 = new ArrayList<String>();
            ArrayList<String> toMergeDoc2 = new ArrayList<String>();
            ArrayList<String> toMerge = new ArrayList<String>();
            ArrayList<String> toDelete = new ArrayList<String>();
            toMerge.add( exportPath + File.separator + "front-page.pdf");

            toMergeDoc1P1.add( exportPath + File.separator + "anexo1.pdf");
            toMergeDoc1P1.add(validationFile);
            toMergeDoc1P1.add(exportPath + File.separator + "anexo2.pdf");
            PdfMerger.mergePdfs(toMergeDoc1P1, exportPath + File.separator + "doc1p1.pdf");

            toMergeDoc1P2.add(exportPath + File.separator + "anexo4.pdf");
            toMergeDoc1P2.add(exportPath + File.separator + "anexo5.pdf");
            toMergeDoc1P2.add(exportPath + File.separator + "anexo6.pdf");
            toMergeDoc1P2.add(rupcFile);
            toMergeDoc1P2.add(exportPath + File.separator + "anexo7.pdf");
            toMergeDoc1P2.add(exportPath + File.separator + "anexo8.pdf");
            toMergeDoc1P2.add(exportPath + File.separator + "anexo9.pdf");
            toMergeDoc1P2.add(exportPath + File.separator + "anexo10.pdf");
            toMergeDoc1P2.add(exportPath + File.separator + "anexo11.pdf");
            toMergeDoc1P2.add(exportPath + File.separator + "anexo12.pdf");
            toMergeDoc1P2.add(imssOcofFile);
            toMergeDoc1P2.add(imssAcuseFile);
            toMergeDoc1P2.add(satFile);
            toMergeDoc1P2.add(csfFile);
            toMergeDoc1P2.add(infonavitFile);
            PdfMerger.mergePdfs(toMergeDoc1P2,exportPath +  File.separator + "doc1p2.pdf");
            
            
            int totalPages = 1;
            for (String s : toMergeDoc1P1) {
                totalPages += countPages(s);
            }
             for (String s : toMergeDoc1P2) {
                totalPages += countPages(s);
            }
            PropositionIndex anexo3 = new PropositionIndex();
            anexo3.Export(controller.getModel(), data, exportPath, "anexo3.pdf",totalPages);
            toMergeDoc1.add(exportPath + File.separator + "doc1p1.pdf");
            toMergeDoc1.add(exportPath + File.separator + "anexo3.pdf");
            toMergeDoc1.add(exportPath + File.separator + "doc1p2.pdf");
            PdfMerger.mergePdfs(toMergeDoc1, exportPath + File.separator + "Doc1tmp.pdf");
            toDelete.add(exportPath + File.separator + "Doc1tmp.pdf");


            PdfNumbering.addPageNumber(exportPath + File.separator + "Doc1tmp.pdf", exportPath + File.separator + "Doc1.pdf");
            toMerge.add(exportPath + File.separator + "Doc1.pdf");
            toDelete.add(exportPath + File.separator + "Doc1.pdf");

            toMergeDoc2.add(exportPath + File.separator + "anexo2.pdf");
            if(!powerOfAttorneyFile.isBlank()){toMergeDoc2.add(powerOfAttorneyFile); }
            toMergeDoc2.add(exportPath + File.separator + "anexo14.pdf");
            toMergeDoc2.add(exportPath + File.separator + "anexo15.pdf");
            toMergeDoc2.add(exportPath + File.separator + "anexo16.pdf");
            toMergeDoc2.add(exportPath + File.separator + "anexo16.pdf");
            toMergeDoc2.add(exportPath + File.separator + "anexo16.pdf");
            toMergeDoc2.add(exportPath + File.separator + "anexo17.pdf");
            PdfMerger.mergePdfs(toMergeDoc2, exportPath + File.separator + "Doc2tmp.pdf");
            toDelete.add(exportPath + File.separator + "Doc2tmp.pdf");
            toDelete.add(exportPath + File.separator + "Doc2tmp.pdf");

            PdfNumbering.addPageNumber(exportPath + File.separator + "Doc2tmp.pdf", exportPath + File.separator + "Doc2.pdf");
            toMerge.add(exportPath + File.separator + "Doc2.pdf");
            toDelete.add(exportPath + File.separator + "Doc2.pdf");
            
            PdfMerger.mergePdfs(toMerge, exportPath + File.separator + "Anexos.pdf");
               
            toMergeDoc1P1.remove(validationFile);
            toMergeDoc1P2.remove(rupcFile);
            toMergeDoc1P2.remove(imssOcofFile);
            toMergeDoc1P2.remove(imssAcuseFile);
            toMergeDoc1P2.remove(satFile);
            toMergeDoc1P2.remove(csfFile);
            toMergeDoc1P2.remove(infonavitFile);
            if(!powerOfAttorneyFile.isBlank()){toMergeDoc2.remove(powerOfAttorneyFile);};

            cleanFolder(toMerge);
            cleanFolder(toMergeDoc1);
            cleanFolder(toMergeDoc1P1);
            cleanFolder(toMergeDoc1P2);
            cleanFolder(toMergeDoc2);
            cleanFolder(toDelete);
            
            isBusy = false;

            JOptionPane.showMessageDialog(this, "Anexos exportados");
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(this, "Error " + e1.getMessage() + " " + e1.getCause(),"Error al exportar",JOptionPane.ERROR_MESSAGE);
            isBusy = false;
        }
            
        });
        this.add(exportButton);

        this.pack();
        this.setVisible(true);
    }

    private int countPages(String filePath) throws Exception{
        PdfReader reader = new PdfReader(filePath);
        int numberOfPages = reader.getNumberOfPages();
        reader.close();
        return numberOfPages;
    }

    void cleanFolder(List<String> toDelete){
        System.out.println("Start cleaning of " + toDelete);
        for (String f : toDelete) {
            File file = new File(f);
            if(file.exists()){
                while(!file.delete()){
                    file = new File(f);
                    System.out.println("Waiting deletion for " + f);
                }   
            }
            else{
                System.out.println(f + " does not exists");
            }
        }
    }

    AttachmentData getData(){
        return new AttachmentData(tfNumber.getText(), tfType.getText(), tfName.getText(), tfCommittee.getText(),
            datePicker.getDate(), tfResponsible.getText(), tfObligations.getText(), tfDeliveryPlace.getText(),
            deliveryDatePicker.getDate(), tfWarranties.getText(), tfCertifications.getText(), tfPaymentConditions.getText(),
            tfSignPlace.getText(), tfPlace.getText(), tfPresent.getText(), tfReceiver.getText());
    }

    
    boolean allFieldsFilled(){
        return  exportPath != null && !exportPath.isBlank() && !validationFile.isBlank() && !csfFile.isBlank() && !imssOcofFile.isBlank() && !imssAcuseFile.isBlank() && !infonavitFile.isBlank() && !rupcFile.isBlank() && !satFile.isBlank() && !tfNumber.getText().isBlank() && !tfType.getText().isBlank() && !tfName.getText().isBlank() && !tfCommittee.getText().isBlank() && !tfResponsible.getText().isBlank() && !tfObligations.getText().isBlank() && !tfDeliveryPlace.getText().isBlank() && !tfWarranties.getText().isBlank() && !tfCertifications.getText().isBlank() && !tfPaymentConditions.getText().isBlank() && !tfSignPlace.getText().isBlank() && !tfSignPlace.getText().isBlank()&& !tfPlace.getText().isBlank() && !tfPlace.getText().isBlank() && !tfPresent.getText().isBlank() && !tfReceiver.getText().isBlank();
    }
}