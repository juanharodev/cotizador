package com.harojuan.ExportFormats.ConcreteFormats;


import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Phaser;

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

public class QuotationWIX extends PdfPageEventHelper implements QuotationExporter{
    @Override
    public void ExporQuotation(QuotationTable tableReference, int PriceToExport, String filePath, String fileName) {        
        try{
            BaseFont bfTableFont = BaseFont.createFont("fonts/ARIAL.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font tableFont = new Font(bfTableFont,9,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/ARIALBD.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font boldFont =  new Font(bfBoldTableFont,9,Font.NORMAL);
            Font pinkFont =  new Font(bfTableFont,9,Font.NORMAL, new Color(250,0,185)); //FontFactory.getFont(FontFactory.TIMES,10, new Color(250,0,185));
            // step 1: create a document object with margin size based on the header and footer content
            Document document = new Document(PageSize.A4, 36, 36, 0, 36);
            float pageWidth = document.getPageSize().getWidth();

            if(!fileName.endsWith(".pdf")){
                fileName += ".pdf";
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
            writer.setPageEvent(new QuotationWIX());
            document.open();

            
            Paragraph titleParagraph = null;
            
            //Content
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'del' yyyy", new Locale("es", "ES"));
            String formatedDate = tableReference.date.format(formatter);
            titleParagraph = new Paragraph(formatedDate,pinkFont);
            titleParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(titleParagraph);
            titleParagraph = new Paragraph(new Phrase(tableReference.receiver + System.lineSeparator(), tableFont));
            titleParagraph.add(new Paragraph("PRESENTE.",boldFont));
            document.add(titleParagraph);

            pinkFont.setStyle(Font.BOLD);
            titleParagraph = new Paragraph("Numero de proveedor: P32154",pinkFont);
            titleParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
            
            
            pinkFont.setStyle(Font.BOLD);
            titleParagraph = new Paragraph(System.lineSeparator() + "ISO: PENDIENTE" + System.lineSeparator(),pinkFont);
            titleParagraph.add(new Phrase("Numero de proveedor: P32154\n\n",pinkFont));
            titleParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(titleParagraph);

            titleParagraph = new Paragraph("ESPECIFICACIONES\n\n",pinkFont);
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(titleParagraph);

            
            //table set up
            float[] colSize = {4,55,9,10,11,11};
            PdfPTable table = null;
            
            //table set up
            table = new PdfPTable(colSize);
            table.getDefaultCell().setBorder(15);
            table.getDefaultCell().setBorderWidth(0);
            table.getDefaultCell().setBackgroundColor(new Color(217,225,242));
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
            table.getDefaultCell().setBackgroundColor(new Color(255,121,220));
            table.addCell(new Phrase("DESCRIPCIÓN",tableFont));
            table.addCell(new Phrase("U/M",tableFont));
            table.addCell(new Phrase("CANTIDAD",tableFont));
            table.addCell(new Phrase("PRECIO",tableFont));
            table.addCell(new Phrase("SUBTOTAL",tableFont));

  
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
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
                table.addCell(new Phrase(entry.name + " " + entry.description,tableFont));  //Descripcion
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
                table.addCell(new Phrase(tableReference.getEntries().get(i).measureUnit,tableFont)); //UM
                table.addCell(new Phrase(String.format(Locale.US,"%,.2f", entry.amount),tableFont)); //Cantidad
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getPrice(PriceToExport)), tableFont));  //PU
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getTotal(PriceToExport)),tableFont)); //IMPORTE

            }
            document.add(table);
            document.add(new Paragraph("\n"));

            //Table set up
            float[] colSizeTotal = {4,74,11,11};
            PdfPTable totalTable = null;

            //table set up
            totalTable = new PdfPTable(colSizeTotal);
            totalTable.getDefaultCell().setBorder(0);
            totalTable.getDefaultCell().setBorderWidth(0.1F);
            totalTable.getDefaultCell().setBackgroundColor(new Color(255,121,220));
            totalTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            totalTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            totalTable.setHorizontalAlignment(0);
            totalTable.setTotalWidth(pageWidth - 72);
            totalTable.setLockedWidth(true);

            //Content
            partidaCell = new PdfPCell();
            partidaCell.setBorder(0);
            partidaCell.setBackgroundColor(new Color(255,255,255,0));

            totalTable.addCell(partidaCell);
            totalTable.addCell(new Paragraph("CANTIDAD CON LETRA ",tableFont));
            totalTable.addCell( new Paragraph("SUBTOTAL ",tableFont));
            totalTable.addCell(new Paragraph("$ " +   String.format(Locale.US,"%,.2f" ,tableReference.getSubtotal(PriceToExport)),tableFont));
            
            totalTable.addCell(partidaCell);
            totalTable.addCell( new Paragraph(NumeroALetra.numeroALetras(tableReference.getGrandTotal(PriceToExport)),tableFont));
            totalTable.addCell(new Paragraph("IVA ",tableFont));
            totalTable.addCell( new Paragraph("$ " +   String.format(Locale.US,"%,.2f" ,tableReference.getIVA(PriceToExport)),tableFont));
            
            totalTable.addCell(partidaCell);
            totalTable.addCell(new Paragraph(" ",tableFont));
            totalTable.addCell(new Paragraph("TOTAL ",tableFont));
            totalTable.addCell(new Paragraph("$ " +   String.format(Locale.US,"%,.2f" ,tableReference.getGrandTotal(PriceToExport)),tableFont));

            //Add to doc
            document.add(totalTable);


            //Terms table
            Paragraph termsParagraph = new Paragraph();
            
            termsParagraph =  new Paragraph("Tiempo de entrega: ", boldFont);
            termsParagraph.add(new Phrase("30 días naturales después de la firma del contrato.\n", tableFont));
            termsParagraph.add(new Phrase("Condiciones de pago: ", boldFont));
            termsParagraph.add(new Phrase("Crédito 45 días.\n", tableFont));
            termsParagraph.add(new Phrase("Garantía de los bienes: ", boldFont));
            termsParagraph.add(new Phrase("Un mes a partir de su recepción.\n", tableFont));
            termsParagraph.add(new Phrase("Vigencia: ", boldFont));
            termsParagraph.add(new Phrase("60 Días naturales.", tableFont));
            termsParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(termsParagraph);

            termsParagraph =  new Paragraph("wixarikagraficahn@gmail.com\n", tableFont);
            termsParagraph.add(new Phrase("Tel: 3111812091\n", tableFont));
            document.add(termsParagraph);


            
            
            document.add(new Paragraph(" "));

            Image sign = Image.getInstanceFromClasspath("img/WIX_FIRMA.jpg");
            sign.scaleToFit(100,100);
            sign.setAlignment(Image.ALIGN_CENTER);
            document.add(sign);

            document.close();
            writer.close();
        }catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar WIX", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    @Override
    public void onStartPage(PdfWriter writer, Document document){
        try {
            float pageWidth = document.getPageSize().getWidth();
            Image logo = Image.getInstanceFromClasspath("img/WIX_LOGO.jpg");
            logo.setAlignment(Image.ALIGN_CENTER);
            logo.scaleToFit(pageWidth- 100,200);
            document.add(logo);
        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar WIX: Inicio de pagina", JOptionPane.ERROR_MESSAGE);
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
            bfTableFont = BaseFont.createFont("fonts/ARIAL.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);     
            Font footerFont = new Font(bfTableFont,9,Font.NORMAL);
            Paragraph footer = new Paragraph();
            footer.setAlignment(Paragraph.ALIGN_CENTER);
            footer.add(new Paragraph("Manifiesto bajo protesta de decir la verdad," +  
        "no encontrarme en ningún supuesto contenido en el artículo 69 y 69-B del código"+  
        "fiscal de la federación y no estar identificado como EFOS y/o EDOS en el portal del SAT"+
        " o en el diario oficial de la federación.",footerFont));
            table.addCell(footer);
            table.writeSelectedRows(0, -1, 34, 36, writer.getDirectContent());
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
   

    }
}
