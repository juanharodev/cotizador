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
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class QuotationENVI extends PdfPageEventHelper implements QuotationExporter{

    String path;
    @Override
    public void ExporQuotation(QuotationTable tableReference, int PriceToExport, String filePath, String fileName) {
        try{
        BaseFont bfTableFont = BaseFont.createFont("fonts/IBMPlexSans_Regular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font tableFont = new Font(bfTableFont,8,Font.NORMAL);
        BaseFont bfBoldTableFont = BaseFont.createFont("fonts/IBMPlexSans_Bold.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font boldFont =  new Font(bfBoldTableFont,8,Font.NORMAL);

        
            // step 1: create a document object with margin size based on the header and footer content
            Document document = new Document(PageSize.A4, 36, 36, 0, 36);
            float pageWidth = document.getPageSize().getWidth();

            if(!fileName.endsWith(".pdf")){
                fileName += ".pdf";
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
            writer.setPageEvent(new QuotationENVI());
            document.open();

            Paragraph p1 = new Paragraph(tableReference.receiver + System.lineSeparator(), tableFont);
            document.add(p1);
            Paragraph p2 = new Paragraph("PRESENTE.\n", boldFont);
            document.add(p2);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "ES"));
            String formatedDate = tableReference.date.format(formatter);
            Paragraph p3 = new Paragraph("Guadalajara, Jalisco a " + formatedDate +"\n\nISO: PENDIENTE", tableFont);
            p3.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(p3);
            Paragraph p4 = new Paragraph("COTIZACIÓN Y CARACTERÍSTICAS TÉCNICAS\n\n", boldFont);
            p4.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p4);

            float[] colSize = {12,10,10,48,10,10};
            PdfPTable table = null;
            
            //table set up
            table = new PdfPTable(colSize);
            table.getDefaultCell().setBorder(0);
            table.getDefaultCell().setBorderWidth(0);
            table.getDefaultCell().setBackgroundColor(new Color(217,225,242));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(pageWidth - 60);
            table.setLockedWidth(true);
            
            table.addCell(new Paragraph("PROGRESIVO",tableFont));
            table.addCell(new Phrase("CANTIDAD",tableFont));
            table.addCell(new Phrase("U/M",tableFont));
            table.addCell(new Phrase("CONCEPTO",tableFont));
            table.addCell(new Phrase("P/U",tableFont));
            table.addCell(new Phrase("SUBTOTAL",tableFont));

  
            QuotationEntry entry;
            table.getDefaultCell().setBackgroundColor(Color.WHITE);
            for(int i = 0; i<tableReference.getEntries().size(); i++){
                entry = tableReference.getEntries().get(i);
                table.addCell(new Phrase(Integer.toString(i+1),tableFont)); //Progresivo
                table.addCell(new Phrase(String.format("%.2f", entry.amount),tableFont)); //Cantidad
                table.addCell(new Phrase(tableReference.getEntries().get(i).measureUnit,tableFont)); //UM
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
                table.addCell(new Phrase(entry.name + " " + entry.description,tableFont));  //Descripcion
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
                table.addCell(new Phrase("$     " +   String.format(Locale.US,"%,.2f" ,entry.getPrice(PriceToExport)), tableFont));  //Precio
                table.addCell(new Phrase("$     " +   String.format(Locale.US,"%,.2f" ,entry.getTotal(PriceToExport)),tableFont)); //SUBTOTAL
            }
            document.add(table);
            document.add(new Paragraph("\n"));

            //Table set up
            float[] colSizeTotal = {60,20,20};
            PdfPTable totalTable = null;
            
            //table set up
            totalTable = new PdfPTable(colSizeTotal);
            totalTable.getDefaultCell().setBorder(0);
            totalTable.getDefaultCell().setBorderWidth(0F);
            totalTable.getDefaultCell().setBackgroundColor(new Color(217,225,242));
            totalTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            totalTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            totalTable.setHorizontalAlignment(0);
            totalTable.setTotalWidth(pageWidth - 60);
            totalTable.setLockedWidth(true);

            //Content
            Paragraph totalParagraph;
           
            totalParagraph = new Paragraph();
            totalParagraph.add(new Phrase("CANTIDAD CON LETRA: ",boldFont));
            totalParagraph.add(new Phrase("30 Días naturales.",tableFont));
            totalTable.addCell(totalParagraph);
            totalTable.addCell( new Paragraph("SUBTOTAL ",boldFont));
            totalTable.addCell(new Paragraph("$     " + String.format("%.2f" ,tableReference.getSubtotal(PriceToExport)),boldFont));


            totalParagraph = new Paragraph();
            totalParagraph.add(new Phrase("CONDICIONES DE PAGO: ",boldFont));
            totalParagraph.add(new Phrase("Al finalizar las entregas.",tableFont));
            totalTable.addCell(totalParagraph);
            totalTable.addCell(new Paragraph("IVA + 16% ",boldFont));
            totalTable.addCell( new Paragraph("$    " + String.format("%.2f" ,tableReference.getIVA(PriceToExport)),boldFont));
            
            totalParagraph = new Paragraph("CANTIDAD CON LETRA ",boldFont);
            totalParagraph.add(new Phrase(NumeroALetra.numeroALetras(tableReference.getGrandTotal(PriceToExport)),tableFont));
            totalTable.addCell(totalParagraph);
            totalTable.addCell(new Paragraph("GRAN TOTAL ",boldFont));
            totalTable.addCell(new Paragraph("$     " +   String.format(Locale.US,"%,.2f" ,tableReference.getGrandTotal(PriceToExport)),boldFont));
            //Add to doc
            document.add(totalTable);

            Paragraph termsParagraph = null;
            termsParagraph = new Paragraph();
            termsParagraph.add(new Phrase("VIGENCIA: ", boldFont));
            termsParagraph.add(new Phrase("30 Días naturales a partir de la fecha de entrega.\n", tableFont));
            document.add(termsParagraph);

            termsParagraph = new Paragraph();
            termsParagraph.add((new Phrase("GARANTIA DE LOS BIENES: ", boldFont)));
            termsParagraph.add(new Phrase("60 Días NATURALES", tableFont));
            document.add(termsParagraph);
            
            termsParagraph = new Paragraph(new Phrase("Están incluidos los costos de maniobra y entrega.\n", tableFont));
            document.add(termsParagraph);

            Image sign = Image.getInstanceFromClasspath("img/ENVI_FIRMA.jpg");
            sign.scaleToFit(150,150);
            sign.setAlignment(Image.ALIGN_CENTER);
            document.add(sign);

            document.close();
            writer.close();
        }catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar ENVI", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    @Override
    public void onStartPage(PdfWriter writer, Document document){
        try {
            float pageWidth = document.getPageSize().getWidth();
            float pageHeight = document.getPageSize().getHeight();
            Image logo = Image.getInstanceFromClasspath("img/ENVI_LOGO.jpg");
            logo.scaleToFit(pageWidth,110);
            document.add(logo);
            PdfContentByte canvas = writer.getDirectContent();
            ColumnText ct = new ColumnText(canvas);
            float fontSize = 7f;

            BaseFont bfTableFont = BaseFont.createFont("fonts/IBMPlexSans_Regular.ttf",BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
            Font font = new Font(bfTableFont,fontSize,Font.NORMAL);
            Paragraph p = new Paragraph("ENVI Importaciones S. de R.L. de C.V.",font);
            p.setAlignment(Paragraph.ALIGN_RIGHT);
            ct.addElement(p);

            p = new Paragraph("RFC: EIM130702RX7",font);
            p.setAlignment(Paragraph.ALIGN_RIGHT);
            ct.addElement(p);

            p = new Paragraph("Av. Chapultepec Sur 130, Col Americana, Lafayette,",font);
            p.setAlignment(Paragraph.ALIGN_RIGHT);
            ct.addElement(p);

            p = new Paragraph("CP 44150, Guadalajara, Jalisco.",font);
            p.setAlignment(Paragraph.ALIGN_RIGHT);
            ct.addElement(p);

            p = new Paragraph("enviprint2020@gmail.com",font);
            p.setAlignment(Paragraph.ALIGN_RIGHT);
            ct.addElement(p);

            p = new Paragraph("Tel: 33 1553 6612",font);
            p.setAlignment(Paragraph.ALIGN_RIGHT);
            ct.addElement(p);

            p = new Paragraph("RUPC: En trámite",font);
            p.setAlignment(Paragraph.ALIGN_RIGHT);
            ct.addElement(p);
        
            // fill first box
            ct.setSimpleColumn(pageWidth - 200, pageHeight - 150, pageWidth-20, pageHeight -20f);
            ct.go();
            

        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar ENVI: Inicio de pagina", JOptionPane.ERROR_MESSAGE);
        }
    }
}
