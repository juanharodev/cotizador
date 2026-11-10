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
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class QuotationDDV extends PdfPageEventHelper implements QuotationExporter{

    String path;
    @Override
    public void ExporQuotation(QuotationTable tableReference, int PriceToExport, String filePath, String fileName) {
        
        
        try{
            BaseFont bfTableFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font tableFont = new Font(bfTableFont,9,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font boldFont =  new Font(bfBoldTableFont,9,Font.NORMAL);

            // step 1: create a document object with margin size based on the header and footer content
            Document document = new Document(PageSize.A4, 36, 36, 0, 36);
            float pageWidth = document.getPageSize().getWidth();

            if(!fileName.endsWith(".pdf")){
                fileName += ".pdf";
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
            writer.setPageEvent(new QuotationDDV());
            document.open();

            //Title table set up
            PdfPTable titleTable = new PdfPTable(2);

            titleTable.getDefaultCell().setBorder(0);
            titleTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            titleTable.setTotalWidth(pageWidth - 60);
            titleTable.setLockedWidth(true);
            
            PdfPCell titleCell = null;
            Paragraph titleParagraph = null;
            
            //Content
            titleParagraph = new Paragraph(new Phrase(tableReference.receiver + System.lineSeparator(), tableFont));
            titleParagraph.add(new Paragraph("PRESENTE.",boldFont));
            
            titleCell = new PdfPCell(titleParagraph);
            titleCell.setBorder(0);
            titleCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            titleTable.addCell(titleCell);
            
            titleParagraph =  new Paragraph(new Phrase("Cotización: ", tableFont));
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("'a' d 'de' MMMM 'del' yyyy");
            String date = tableReference.date.format(dateFormat);
            titleParagraph.add(new Paragraph(tableReference.quotationNumber, boldFont));
            titleParagraph.add(new Paragraph("Guadalajara Jalisco, " + date,tableFont));
            titleParagraph.add(new Phrase("RFC: ",tableFont));
            titleParagraph.add(new Paragraph("DSO181213C61", boldFont));
            titleParagraph.add(new Phrase("Número de proveedor:  ",tableFont));
            titleParagraph.add(new Paragraph("P30289", boldFont));
            titleParagraph.add(new Phrase("ISO:  ",tableFont));
            titleParagraph.add(new Paragraph("PENDIENTE", boldFont));
            
            titleCell = new PdfPCell(titleParagraph);
            titleCell.setBorder(0);
            titleCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            titleTable.addCell(titleCell);;

            document.add(titleTable);

            titleParagraph = new Paragraph("PROPUESTA TÉCNICA Y ECONÓMICA",boldFont);
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(titleParagraph);
            document.add(new Paragraph(" "));


            float[] colSize = {12,10,10,49,10,10};
            PdfPTable table = null;
            
            //table set up
            table = new PdfPTable(colSize);
            table.getDefaultCell().setBorder(3);
            table.getDefaultCell().setBorderColor(new Color(91,155,213));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(pageWidth - 60);
            table.setLockedWidth(true);
            
            table.addCell(new Paragraph("PARTIDA",tableFont));
            table.addCell(new Phrase("CANTIDAD",tableFont));
            table.addCell(new Phrase("U/M",tableFont));
            table.addCell(new Phrase("ARTICULOS/DESCRIPCIÓN",tableFont));
            table.addCell(new Phrase("P.U.",tableFont));
            table.addCell(new Phrase("MONTO",tableFont));

            table.getDefaultCell().setBorder(0);
            table.getDefaultCell().setBorderWidth(0);
            QuotationEntry entry;
            for(int i = 0; i<tableReference.getEntries().size(); i++){
                entry = tableReference.getEntries().get(i);
                if(i%2 == 0){
                    table.getDefaultCell().setBackgroundColor(new Color(221,235,247));
                }
                else{
                    table.getDefaultCell().setBackgroundColor(new Color(255,255,255));
                }
                table.addCell(new Phrase(Integer.toString(i+1),tableFont)); //Partida
                table.addCell(new Phrase(String.format("%.2f", entry.amount),tableFont)); //Cantidad
                table.addCell(new Phrase(tableReference.getEntries().get(i).measureUnit,tableFont)); //UM
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
                table.addCell(new Phrase(entry.name + " " + entry.description,tableFont));  //Descripcion
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
                table.addCell(new Phrase("$     " +   String.format(Locale.US,"%,.2f" ,entry.getPrice(PriceToExport)), tableFont));  //Precio
                table.addCell(new Phrase("$     " +   String.format(Locale.US,"%,.2f" ,entry.getTotal(PriceToExport)), tableFont)); //SUBTOTAL
            }
            document.add(table);

            //Table set up
            float[] colSizeTotal = {60,20,20};
            PdfPTable totalTable = null;
            Paragraph totalParagraph = null;
            
            //table set up
            totalTable = new PdfPTable(colSizeTotal);
            totalTable.getDefaultCell().setBorderWidth(0.1F);
            totalTable.getDefaultCell().setBackgroundColor(new Color(255,255,255));
            totalTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            totalTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            totalTable.setHorizontalAlignment(0);
            totalTable.setTotalWidth(pageWidth - 72);
            totalTable.setLockedWidth(true);

            //Content

            totalTable.getDefaultCell().setBorder(Rectangle.TOP);
            totalParagraph = new Paragraph("CONDICIONES DE PAGO",boldFont);
            totalParagraph.add( new Paragraph(": AL FINALIZAR EL SERVICIO.", tableFont));
            totalTable.addCell(totalParagraph);
            totalTable.addCell( new Paragraph("SUBTOTAL ",boldFont));
            totalTable.addCell(new Paragraph("$     " +   String.format(Locale.US,"%,.2f" ,tableReference.getSubtotal(PriceToExport)),boldFont));

            totalTable.getDefaultCell().setBorder(0);
            totalTable.addCell(new Paragraph("CANTIDAD CON LETRA:  ",boldFont));
            totalTable.addCell(new Paragraph("I.V.A. ",boldFont));
            totalTable.addCell( new Paragraph("$     " +   String.format(Locale.US,"%,.2f" ,tableReference.getIVA(PriceToExport)),boldFont));
            
            totalTable.addCell( new Paragraph(NumeroALetra.numeroALetras(tableReference.getGrandTotal(PriceToExport)),tableFont));
            totalTable.addCell(new Paragraph("TOTAL ",boldFont));
            totalTable.addCell(new Paragraph("$     " +   String.format(Locale.US,"%,.2f" ,tableReference.getGrandTotal(PriceToExport)),boldFont));
            
            //Add to doc
            document.add(totalTable);


            //Terms table
            PdfPTable termnsTable = new PdfPTable(2);
            Paragraph termsParagraph = null;
            termnsTable.getDefaultCell().setBorder(Rectangle.TOP);
            termnsTable.getDefaultCell().setBorderColor(new Color(91,155,213));
            termnsTable.getDefaultCell().setBorderWidth(0.1F);
            termnsTable.getDefaultCell().setBackgroundColor(new Color(226,239,217));
            termnsTable.getDefaultCell().setBorderColor(new Color(224,224,224));
            termnsTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            termnsTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            termnsTable.setHorizontalAlignment(0);
            termnsTable.setTotalWidth(pageWidth - 72);
            termnsTable.setLockedWidth(true);
            
            PdfPCell termsCellR = null;
            PdfPCell termsCellL = null;
            boldFont.setSize(8);
            tableFont.setSize(8);
            
            termsCellL = new PdfPCell();
            termsCellL.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            termsCellL.setBorder(0);
            termsParagraph =  new Paragraph("Fecha de entrega: ", boldFont);
            termsParagraph.add(new Phrase("30 días naturales después de la firma del contrato.", tableFont));
            termsCellL.addElement(termsParagraph);
            termnsTable.addCell(termsCellL);
            
            termsCellR = new PdfPCell();
            termsCellR.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            termsCellR.setBorder(0);
            termsParagraph = new Paragraph(new Phrase("Forma de Pago: ", boldFont));
            termsParagraph.add(new Phrase("03=Transferencia electrónica de fondos.", tableFont));
            termsCellR.addElement(termsParagraph);
            termnsTable.addCell(termsCellR);
            
            termsCellL = new PdfPCell();
            termsCellL.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            termsCellL.setBorder(0);
            termsParagraph = new Paragraph(new Phrase("Garantía: ", boldFont));
            termsParagraph.add(new Phrase("30 días por defectos de fabricación.", tableFont));
            termsCellL.addElement(termsParagraph);
            termnsTable.addCell(termsCellL);
            
            
            termsCellR = new PdfPCell();
            termsCellR.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            termsCellR.setBorder(0);
            termsParagraph = new Paragraph(new Phrase("Método de Pago: ", boldFont));
            termsParagraph.add(new Phrase(" PPD=Pago en parcialidades o diferido.", tableFont));
            termsCellR.addElement(termsParagraph);
            termnsTable.addCell(termsCellR);
            
            termsCellL = new PdfPCell();
            termsCellL.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            termsCellL.setBorder(0);
            termsParagraph = new Paragraph(new Phrase("Vigencia de la cotización: ", boldFont));
            termsParagraph.add(new Phrase("60 Días naturales.", tableFont));
            termsCellL.addElement(termsParagraph);
            termnsTable.addCell(termsCellL);
            
            termsCellR = new PdfPCell();
            termsCellR.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            termsCellR.setBorder(0);
            termsParagraph = new Paragraph(new Phrase("Uso de CFDI: ", boldFont));
            termsParagraph.add(new Phrase(" G03=Gastos en general DATOS PARA DEPOSITO.", tableFont));
            termsCellR.addElement(termsParagraph);
            termnsTable.addCell(termsCellR);
            
            
            document.add(new Paragraph(" "));
            document.add(termnsTable);

            Image sign = Image.getInstanceFromClasspath("img/DDV_FIRMA.jpg");
            sign.scaleToFit(120,120);
            document.add(sign);

            document.close();
            writer.close();
        }catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar DDV", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    @Override
    public void onStartPage(PdfWriter writer, Document document){
        try {
            float pageWidth = document.getPageSize().getWidth();
            float pageHeight = document.getPageSize().getHeight();
            Image logo = Image.getInstanceFromClasspath("img/DDV_LOGO.jpg");
            logo.scaleToFit(pageWidth,pageHeight);
            document.add(logo);
        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar DDV: Inicio de pagina incorrecto", JOptionPane.ERROR_MESSAGE);
        }
    }

        @Override
    public void onEndPage(PdfWriter writer, Document document) {

        // step 3: create a footer
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(510);
        table.setWidths(new int[]{30, 70});
        table.getDefaultCell().setPaddingBottom(5);
        table.getDefaultCell().setBorder(Rectangle.TOP);

        Paragraph title = new Paragraph("Página " + document.getPageNumber() , new Font(Font.HELVETICA, 7));
         
        PdfPCell titleCell = new PdfPCell(title);
        titleCell.setPaddingTop(4);
        titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        titleCell.setBorder(Rectangle.TOP);
        table.addCell(titleCell);

        BaseFont bfTableFont;
        try {
            bfTableFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font font = new Font(bfTableFont,9,Font.NORMAL);        
            Paragraph pageNumberText = new Paragraph("En mercancía sobre pedido no hay cambios ni devoluciones.\n", font);
            pageNumberText.add(new Phrase("Si es su primera compra con nosotros, favor de anexar su cédula de identificación fiscal.\n",font));
            pageNumberText.add(new Phrase("Una vez recibida la mercancía no se aceptan devoluciones\n",font));
            PdfPCell pageNumberCell = new PdfPCell(pageNumberText);
            pageNumberCell.setPaddingTop(4);
            pageNumberCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pageNumberCell.setBorder(Rectangle.TOP);
            table.addCell(pageNumberCell);
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        table.writeSelectedRows(0, -1, 34, 36, writer.getDirectContent());
        
    }
}
