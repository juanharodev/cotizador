package com.harojuan.ExportFormats.ConcreteFormats;


import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JOptionPane;

import com.harojuan.ExportFormats.QuotationExporter;
import com.harojuan.QuotationModel.QuotationEntry;
import com.harojuan.QuotationModel.QuotationTable;
import com.harojuan.Utils.NumeroALetra;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class QuotationCALIMEO extends PdfPageEventHelper implements QuotationExporter{

    String path;
    static LocalDate date;
    @Override
    public void ExporQuotation(QuotationTable tableReference, int PriceToExport, String filePath, String fileName) {
        

        try{
            date = tableReference.date;
            BaseFont bfTableFont = BaseFont.createFont("fonts/arialnarrow.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font tableFont = new Font(bfTableFont,9,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/arialnarrow_bold.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font boldFont =  new Font(bfBoldTableFont,9,Font.NORMAL);

            // step 1: create a document object with margin size based on the header and footer content
            Document document = new Document(PageSize.A4, 36, 36, 0, 36);
            float pageWidth = document.getPageSize().getWidth();

            if(!fileName.endsWith(".pdf")){
                fileName += ".pdf";
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
            writer.setPageEvent(new QuotationCALIMEO());
            document.open();

            Paragraph p1 = new Paragraph(tableReference.receiver + System.lineSeparator());
            document.add(p1);
            document.add(new Paragraph("ISO: PENDIENTE "));
            document.add(new Paragraph(" "));

            float[] colSize = {14,8,10,48,10,10};
            PdfPTable table = null;
            
            //table set up
            table = new PdfPTable(colSize);
            table.getDefaultCell().setBorder(3);
            table.getDefaultCell().setBorderColor(new Color(112,173,71));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(pageWidth - 60);
            table.setLockedWidth(true);
            
            table.addCell(new Paragraph("PROGRESIVO",tableFont));
            table.addCell(new Phrase("U/M",tableFont));
            table.addCell(new Phrase("CANTIDAD",tableFont));
            table.addCell(new Phrase("DESCRIPCIÓN",tableFont));
            table.addCell(new Phrase("PRECIO",tableFont));
            table.addCell(new Phrase("SUB-TOTAL",tableFont));

            table.getDefaultCell().setBorder(0);
            table.getDefaultCell().setBorderWidth(0);
            QuotationEntry entry;
            for(int i = 0; i<tableReference.getEntries().size(); i++){
                entry = tableReference.getEntries().get(i);
                if(i%2 == 0){
                    table.getDefaultCell().setBackgroundColor(new Color(226,239,218));
                }
                else{
                    table.getDefaultCell().setBackgroundColor(new Color(255,255,255));
                }
                table.addCell(new Phrase(Integer.toString(i+1),tableFont)); //Progresivo
                table.addCell(new Phrase(tableReference.getEntries().get(i).measureUnit,tableFont)); //UM
                table.addCell(new Phrase(String.format("%.2f", entry.amount),tableFont)); //Cantidad
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
                table.addCell(new Phrase(entry.name + " " + entry.description,tableFont));  //Descripcion
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
                table.addCell(new Phrase("$ " +   String.format(Locale.US,"%,.2f" ,entry.getPrice(PriceToExport)), tableFont));  //Precio
                table.addCell(new Phrase("$ " +   String.format(Locale.US,"%,.2f" ,entry.getTotal(PriceToExport)),tableFont)); //SUBTOTAL
            }
            document.add(table);

            //Table set up
            float[] colSizeTotal = {60,20,20};
            PdfPTable totalTable = null;
            
            //table set up
            totalTable = new PdfPTable(colSizeTotal);
            totalTable.getDefaultCell().setBorder(0);
            totalTable.getDefaultCell().setBorderWidth(0.1F);
            totalTable.getDefaultCell().setBackgroundColor(new Color(255,255,255));
            totalTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            totalTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            totalTable.setHorizontalAlignment(0);
            totalTable.setTotalWidth(pageWidth - 72);
            totalTable.setLockedWidth(true);

            //Content
            totalTable.addCell(new Paragraph("CANTIDAD CON LETRA: ",boldFont));
            totalTable.addCell( new Paragraph("SUBTOTAL ",boldFont));
            totalTable.addCell(new Paragraph("$     " +   String.format(Locale.US,"%,.2f" ,tableReference.getSubtotal(PriceToExport)),boldFont));

            totalTable.addCell(new Paragraph(" ",boldFont));
            totalTable.addCell(new Paragraph("IVA ",boldFont));
            totalTable.addCell( new Paragraph("$     " +   String.format(Locale.US,"%,.2f" ,tableReference.getIVA(PriceToExport)),boldFont));
            
            totalTable.addCell( new Paragraph(NumeroALetra.numeroALetras(tableReference.getGrandTotal(PriceToExport)),tableFont));
            totalTable.addCell(new Paragraph("TOTAL ",boldFont));
            totalTable.addCell(new Paragraph("$     " +   String.format(Locale.US,"%,.2f" ,tableReference.getGrandTotal(PriceToExport)),boldFont));
            //Add to doc
            document.add(totalTable);


            PdfPTable termnsTable = new PdfPTable(1);
            termnsTable.getDefaultCell().setBorder(15);
            termnsTable.getDefaultCell().setBorderWidth(0.1F);
            termnsTable.getDefaultCell().setBackgroundColor(new Color(226,239,217));
            termnsTable.getDefaultCell().setBorderColor(new Color(224,224,224));
            termnsTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            termnsTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            termnsTable.setHorizontalAlignment(0);
            termnsTable.setTotalWidth(pageWidth - 72);
            termnsTable.setLockedWidth(true);


            termnsTable.addCell(new Phrase("TÉRMINOS Y CONDICIONES", boldFont));
            
            boldFont.setSize(9);
            tableFont.setSize(8);
            PdfPCell termsCell = null;
            termsCell = new PdfPCell();
            termsCell.setBackgroundColor(Color.WHITE);
            termsCell.setBorder(15);
            termsCell.setBorderWidth(0.1F);

            Paragraph termsParagraph = null;
            termsParagraph = new Paragraph(new Phrase("     • Tiempo de entrega: ", boldFont));
            termsParagraph.add(new Phrase("30 días naturales a partir de un día después de la firma del contrato.", tableFont));
            termsCell.addElement(termsParagraph);

            termsParagraph = new Paragraph(new Phrase("     • Condiciones de pago: ", boldFont));
            termsParagraph.add(new Phrase("30 días naturales de crédito después de la recepción de la mercancía.", tableFont));
            termsCell.addElement(termsParagraph);

            termsParagraph = new Paragraph(new Phrase("     • Garantía: ", boldFont));
            termsParagraph.add(new Phrase("Se procederá a reemplazar cualquier artículo que no cumpla con los estándares de calidad prometidos debido a defectos de fabricación o impresión.", tableFont));
            termsCell.addElement(termsParagraph);

            termsParagraph = new Paragraph(new Phrase("     • Vigencia de cotización: ", boldFont));
            termsParagraph.add(new Phrase("60 días después de la fecha de emisión.", tableFont));
            termsCell.addElement(termsParagraph);

            termsParagraph = new Paragraph(new Phrase("     • En pedidos confirmados no se aceptarán modificaciones.", tableFont));
            termsCell.addElement(termsParagraph);

            termsParagraph = new Paragraph(new Phrase("     • Horarios de atención de lunes a viernes de 9:00 a 18:00 hr.", tableFont));
            termsCell.addElement(termsParagraph);

            termsParagraph = new Paragraph(new Phrase("     • Si usted tiene alguna pregunta sobre esta cotización, por favor, póngase en contacto con nosotros.", tableFont));
            termsCell.addElement(termsParagraph);
            termsParagraph = new Paragraph(new Phrase(" ", tableFont));
            termsCell.addElement(termsParagraph);
            
            termnsTable.addCell(termsCell);
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(termnsTable);

            Image sign = Image.getInstanceFromClasspath("img/CALIMEO_FIRMA.jpg");
            sign.scaleToFit(100,100);
            document.add(sign);

            document.close();
            writer.close();
        }catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar CALIMEO", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    @Override
    public void onStartPage(PdfWriter writer, Document document){

        try {
            BaseFont bfTableFont = BaseFont.createFont("fonts/arialnarrow.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            float fontSize = 8.0f;
            Font headerFont = new Font(bfTableFont,fontSize,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/arialnarrow_bold.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font headerboldFont =  new Font(bfBoldTableFont,fontSize,Font.BOLD);
            float pageWidth = document.getPageSize().getWidth();
            float pageHeight = document.getPageSize().getHeight();
            Image logo = Image.getInstanceFromClasspath("img/CALIMEO_LOGO.jpg");
            logo.scaleToFit(pageWidth,100);
            document.add(logo);
            PdfContentByte canvas = writer.getDirectContent();
            ColumnText ct = new ColumnText(canvas);
            ct.addElement(new Paragraph("Razon social",headerboldFont));
            ct.addElement(new Paragraph("Ana Paulina",headerFont));
            ct.addElement(new Paragraph("Rodríguez Ríos",headerFont));
            ct.addElement(new Paragraph("RORA840703266",headerFont));
            ct.addElement(new Paragraph("Zapopan, Jalisco.",headerFont));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d 'de' yyyy", new Locale("es", "ES"));
            String formatedDate = date.format(formatter);
            ct.addElement(new Paragraph(formatedDate,headerFont));
            // fill first box
            ct.setSimpleColumn(pageWidth - 110, pageHeight - 105, pageWidth, pageHeight -16.5f);
            ct.go();
            

        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar CALIMEO: Inicio de pagina incorrecto", JOptionPane.ERROR_MESSAGE);
        }
    }

        @Override
    public void onEndPage(PdfWriter writer, Document document) {

        BaseFont bfTableFont;
        try {
            bfTableFont = BaseFont.createFont("fonts/arialnarrow.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);     
            float fontSize = 10.0f;
            Font footerFont = new Font(bfTableFont,fontSize,Font.NORMAL);
            // step 3: create a footer
            PdfPTable table = new PdfPTable(2);
            table.setTotalWidth(510);
            table.setWidths(new int[]{50, 50});
            table.getDefaultCell().setPaddingBottom(5);
            table.getDefaultCell().setBorder(Rectangle.TOP);

            Paragraph title =  new Paragraph("", footerFont);
            PdfPCell titleCell = new PdfPCell(title);
            titleCell.setPaddingTop(4);
            titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleCell.setBorder(Rectangle.TOP);
            table.addCell(titleCell);

            Paragraph pageNumberText =  new Paragraph("Página " + document.getPageNumber(),footerFont);
            PdfPCell pageNumberCell = new PdfPCell(pageNumberText);
            pageNumberCell.setPaddingTop(4);
            pageNumberCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pageNumberCell.setBorder(Rectangle.TOP);
            table.addCell(pageNumberCell);

            table.writeSelectedRows(0, -1, 34, 36, writer.getDirectContent());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
   
    }
}
