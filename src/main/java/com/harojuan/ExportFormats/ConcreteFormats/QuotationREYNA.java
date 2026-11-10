package com.harojuan.ExportFormats.ConcreteFormats;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JOptionPane;

import com.harojuan.ExportFormats.QuotationExporter;
import com.harojuan.QuotationModel.QuotationEntry;
import com.harojuan.QuotationModel.QuotationTable;
import com.harojuan.Utils.NumeroALetra;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class QuotationREYNA extends PdfPageEventHelper implements QuotationExporter {

    @Override
    public void ExporQuotation(QuotationTable tableReference, int PriceToExport, String filePath, String fileName) {
        

    try{
        BaseFont bfTableFont = BaseFont.createFont("fonts/centurygothic.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font tableFont = new Font(bfTableFont,8,Font.NORMAL);
        BaseFont bfBoldTableFont = BaseFont.createFont("fonts/centurygothic_bold.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font boldFont =  new Font(bfBoldTableFont,8,Font.NORMAL);
        Font whiteTableFont = new Font(bfTableFont,8,Font.NORMAL,Color.WHITE);
        // step 1: create a document object with margin size based on the header and footer content
        Document document = new Document(PageSize.A4, 36, 36, 0, 36);
        float pageWidth = document.getPageSize().getWidth();

        if(!fileName.endsWith(".pdf")){
            fileName += ".pdf";
        }
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
        writer.setPageEvent(new QuotationREYNA());
        document.open();

            
        PdfPTable titleTable = new PdfPTable(2);
        titleTable.getDefaultCell().setBorder(0);
        titleTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_TOP);
        titleTable.setHorizontalAlignment(0);
        titleTable.setTotalWidth(pageWidth - 60);
        titleTable.setLockedWidth(true);
        
        
        titleTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        Paragraph p1 = new Paragraph(tableReference.receiver + System.lineSeparator(), boldFont);
        titleTable.addCell(p1);
        
        
        PdfPCell c2 = new PdfPCell();
        c2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        c2.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        c2.setBorder(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "ES"));
        String formatedDate = tableReference.date.format(formatter);
        Paragraph p2 = new Paragraph("ISO PENDIENTE\n",boldFont);
        p2.add(new Phrase("Guadalajara, Jalisco. " + formatedDate, boldFont));
        p2.setAlignment(Paragraph.ALIGN_RIGHT);
        c2.addElement(p2);
        titleTable.addCell(c2);

        document.add(titleTable);

        document.add(new Paragraph("Inversión:\n\n\n"));
        
        //table set up
        float[] colSize = {4,9,9,60,7,11};
        PdfPTable table = null;
        
        //table set up
        table = new PdfPTable(colSize);
        table.getDefaultCell().setBorder(15);
        table.getDefaultCell().setBorderWidth(0);
        table.getDefaultCell().setBackgroundColor(new Color(87,87,87));
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(pageWidth - 60);
        table.setLockedWidth(true);
        
        PdfPCell partidaCell = null;
        partidaCell = new PdfPCell();
        partidaCell.setBorder(0);
        partidaCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        partidaCell.setVerticalAlignment(PdfCell.ALIGN_MIDDLE);
        partidaCell.addElement(new Paragraph(" ",tableFont));
        table.addCell(partidaCell);
        table.getDefaultCell().setBackgroundColor(new Color(89,89,89));
        table.addCell(new Phrase("Cantidad",whiteTableFont));
        table.addCell(new Phrase("Artículo",whiteTableFont));
        table.addCell(new Phrase("Descripción",whiteTableFont));
        table.addCell(new Phrase("P/U",whiteTableFont));
        table.addCell(new Phrase("Total",whiteTableFont));
        
        
        QuotationEntry entry;
        table.getDefaultCell().setBackgroundColor(new Color(255,255,255,0));
    
        for(int i = 0; i<tableReference.getEntries().size(); i++){
            entry = tableReference.getEntries().get(i);
            partidaCell = new PdfPCell();
            partidaCell.setBorder(0);
            partidaCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            partidaCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            partidaCell.addElement(new Phrase(Integer.toString(i+1),tableFont));
            table.addCell(partidaCell); //Partida
            table.addCell(new Phrase(String.format(Locale.US,"%,.2f", entry.amount),tableFont)); //Cantidad
            table.addCell(new Phrase(tableReference.getEntries().get(i).measureUnit,tableFont)); //UM
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase(entry.name + " " + entry.description,tableFont));  //Descripcion
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
            table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getPrice(PriceToExport)), tableFont));  //PU
            table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getTotal(PriceToExport)),tableFont)); //IMPORTE

        }
        document.add(table);
        document.add(new Paragraph("\n"));

        //Table set up
        float[] colSizeTotal = {4,70,11,15};
        PdfPTable totalTable = null;

        //table set up
        totalTable = new PdfPTable(colSizeTotal);
        totalTable.getDefaultCell().setBorder(0);
        totalTable.getDefaultCell().setBorderWidth(0.1F);
        totalTable.getDefaultCell().setBackgroundColor(new Color(89,89,89));
        totalTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        totalTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        totalTable.setHorizontalAlignment(0);
        totalTable.setTotalWidth(pageWidth - 60);
        totalTable.setLockedWidth(true);

        //Content
        partidaCell = new PdfPCell();
        partidaCell.setBorder(0);
        partidaCell.setBackgroundColor(Color.WHITE);
    

        totalTable.addCell(partidaCell);
        totalTable.addCell(new Paragraph(" ",whiteTableFont));
        totalTable.addCell( new Paragraph("Subtotal ",whiteTableFont));
        totalTable.addCell(new Paragraph("$ " +   String.format(Locale.US,"%,.2f" ,tableReference.getSubtotal(PriceToExport)),whiteTableFont));
        
        totalTable.addCell(partidaCell);
        totalTable.addCell(new Paragraph("CANTIDAD CON LETRA ",whiteTableFont));
        totalTable.addCell(new Paragraph("IVA 16%",whiteTableFont));
        totalTable.addCell( new Paragraph("$ " +   String.format(Locale.US,"%,.2f" ,tableReference.getIVA(PriceToExport)),whiteTableFont));
        
        totalTable.addCell(partidaCell);
        totalTable.addCell( new Paragraph(NumeroALetra.numeroALetras(tableReference.getGrandTotal(PriceToExport)),whiteTableFont));
        totalTable.addCell(new Paragraph("Total ",whiteTableFont));
        totalTable.addCell(new Paragraph("$ " +   String.format(Locale.US,"%,.2f" ,tableReference.getGrandTotal(PriceToExport)),whiteTableFont));
        //Add to doc
        document.add(totalTable);


        //Terms table
        Paragraph termsParagraph = new Paragraph();
        
        termsParagraph =  new Paragraph("Tiempo de entrega: 30 días naturales después de la firma del contrato.\n", tableFont);
        termsParagraph.add(new Phrase("Condiciones de pago: Finalizando la entrega.\n", tableFont));
        termsParagraph.add(new Phrase("Garantía: 30 a partir de la fecha de entrega.\n", tableFont));
        termsParagraph.add(new Phrase("Vigencia: 60 días naturales.\n", tableFont));
        document.add(termsParagraph);


        
        
        document.add(new Paragraph(" "));

        Image sign = Image.getInstanceFromClasspath("img/REYNA_FIRMA.jpg");
        sign.scaleToFit(100,100);
        sign.setAlignment(Image.ALIGN_RIGHT);
        document.add(sign);

        document.close();
        writer.close();
    }catch (DocumentException | IOException de) {
        JOptionPane.showMessageDialog(null, de, "Error al exportar REYNA", JOptionPane.ERROR_MESSAGE);
    }
    }
    

    @Override
    public void onStartPage(PdfWriter writer, Document document){
        try {
            float pageWidth = document.getPageSize().getWidth();
            Image logo = Image.getInstanceFromClasspath("img/REYNA_LOGO.jpg");
            logo.setAlignment(Image.ALIGN_CENTER);
            logo.scaleToFit(pageWidth,200);
            document.add(logo);
        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar REYNA: Inicio de pagina", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {       
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(510);
        table.getDefaultCell().setPaddingBottom(5);
        table.getDefaultCell().setBorder(0);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);;
        table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

        BaseFont bfTableFont;
        try {
            bfTableFont = BaseFont.createFont("fonts/centurygothic.ttf",BaseFont.CP1250,BaseFont.EMBEDDED); 
            Font footerFont = new Font(bfTableFont,8,Font.NORMAL);
            Paragraph footer = new Paragraph();
            footer.setAlignment(Paragraph.ALIGN_CENTER);
            footer.add(new Paragraph("Persona física con actividad empresarial Oscar Alejandro Reyna Huizar\n" +
            "RFC: REHO810409428 / No. Proveedor P35039\n" + 
            "Domicilio Fiscal. Primavera Amarilla 1026 Col. Álamo Industrial CP. 45590 Tlaquepaque Jalisco\n" +
            "comercializadorareyna80@gmail.com / 3316901418",footerFont));
            table.addCell(footer);
            table.writeSelectedRows(0, -1, 34, 36, writer.getDirectContent());
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       

    }
    
}
