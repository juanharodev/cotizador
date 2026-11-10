package com.harojuan.ExportFormats.ConcreteFormats;

import java.awt.Color;
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
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
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

public class QuotationPINESGDL extends PdfPageEventHelper implements QuotationExporter{
    static LocalDate date;
    @Override
    public void ExporQuotation(QuotationTable tableReference, int PriceToExport, String filePath, String fileName) {
        
        
        try{
            BaseFont bfTableFont = BaseFont.createFont("fonts/Roboto-Regular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font tableFont = new Font(bfTableFont,8,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/Roboto-Bold.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font boldFont =  new Font(bfBoldTableFont,8,Font.NORMAL);
            Font whiteTableFont = new Font(bfTableFont,8,Font.NORMAL,Color.WHITE);
            date = tableReference.date; 

            // step 1: create a document object with margin size based on the header and footer content
            Document document = new Document(PageSize.A4, 80, 80, 160, 100);
            float pageWidth = document.getPageSize().getWidth();

            if(!fileName.endsWith(".pdf")){
                fileName += ".pdf";
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
            writer.setPageEvent(new QuotationPINESGDL());
            document.open();

            
            
            Paragraph p1 = new Paragraph("ISO: ", tableFont);
            p1.add(new Paragraph("PENDIENTE\n", boldFont));
            p1.add(new Paragraph("Secretaría de Educación del Estado de Jalisco\n"));
            p1.add(new Paragraph("PRESENTE.\n"));
            document.add(p1);
            

            float[] colSize = {10,54,12,12,12};
            PdfPTable table = null;
            
            //table set up
            table = new PdfPTable(colSize);
            table.getDefaultCell().setBorder(15);
            table.getDefaultCell().setBorderWidth(1);
            table.getDefaultCell().setBackgroundColor(new Color(169,209,142));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(pageWidth - 165);
            table.setLockedWidth(true);
            
            table.addCell(new Phrase("Unidad de Medida",whiteTableFont));
            table.addCell(new Phrase("Código / Nombre / Descripción",whiteTableFont));
            table.addCell(new Phrase("Cantidad",whiteTableFont));
            table.addCell(new Phrase("Precio Unitario",whiteTableFont));
            table.addCell(new Phrase("Importe",whiteTableFont));

  
            QuotationEntry entry;
            table.getDefaultCell().setBackgroundColor(new Color(255,255,255,0));
     
            for(int i = 0; i<tableReference.getEntries().size(); i++){
                entry = tableReference.getEntries().get(i);
                table.addCell(new Phrase(tableReference.getEntries().get(i).measureUnit,tableFont)); //UM
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
                table.addCell(new Phrase(entry.name + " " + entry.description,tableFont));  //Descripcion
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
                table.addCell(new Phrase(String.format(Locale.US,"%,.2f", entry.amount),boldFont)); //Cantidad
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getPrice(PriceToExport)), boldFont));  //PU
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getTotal(PriceToExport)), boldFont)); //IMPORTE
            }
            document.add(table);
            //Table set up
            float[] colSizeTotal = {64,12,12,12};
            PdfPTable totalTable = null;
            
            //table set up
            totalTable = new PdfPTable(colSizeTotal);
            totalTable.getDefaultCell().setBorder(15);
            totalTable.getDefaultCell().setBorderWidth(1F);
            totalTable.getDefaultCell().setBackgroundColor(new Color(169,209,142));
            totalTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            totalTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            totalTable.setHorizontalAlignment(0);
            totalTable.setTotalWidth(pageWidth - 165);
            totalTable.setLockedWidth(true);
            
            whiteTableFont.setStyle(Font.BOLD);
            totalTable.addCell(new Paragraph("CANTIDAD CON LETRA:",whiteTableFont));
            totalTable.addCell(new Paragraph("SUBTOTAL",whiteTableFont));
            totalTable.addCell(new Paragraph("IVA 16%\n",whiteTableFont));
            totalTable.addCell(new Paragraph("TOTAL",whiteTableFont));
            
            totalTable.getDefaultCell().setBackgroundColor(new Color(255,255,255,0));
            totalTable.addCell(new Paragraph(new Phrase(NumeroALetra.numeroALetras(tableReference.getGrandTotal(PriceToExport)),tableFont)));
            totalTable.addCell(new Paragraph("$ " + String.format(Locale.US,"%,.2f" ,tableReference.getSubtotal(PriceToExport)),boldFont));
            totalTable.addCell( new Paragraph("$ " + String.format(Locale.US,"%,.2f" ,tableReference.getIVA(PriceToExport)),boldFont));
            totalTable.addCell(new Paragraph("$ " +   String.format(Locale.US,"%,.2f" ,tableReference.getGrandTotal(PriceToExport)),boldFont));
            //Add to doc
            document.add(totalTable);

            Paragraph termsParagraph = null;
            termsParagraph = new Paragraph(new Phrase("Fecha de entrega:  ", boldFont));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'a' d 'de' MMMM yyyy", new Locale("es", "ES"));
            String formatedDate = date.plusDays(30).format(formatter);
            termsParagraph.add(new Phrase( formatedDate +" (fecha inamovible).\n", tableFont));
            termsParagraph.add(new Phrase("Garantía: ", boldFont));
            termsParagraph.add(new Phrase("Certificado de autenticidad por medalla. Cumplimiento en la entrega de los bienes adquiridos.\n",tableFont ));
            termsParagraph.add(new Phrase("Vigencia de la cotización: ", boldFont));
            termsParagraph.add(new Phrase("60 Días naturales\n", tableFont));

            document.add(termsParagraph);

            Image sign = Image.getInstanceFromClasspath("img/PINES_FIRMA.jpg");
            sign.scaleToFit(150,150);
            sign.setAlignment(Image.RIGHT);
            document.add(sign);


            document.close();
            writer.close();

        }catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar PINES", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document){
        try {
            BaseFont bfTableFont = BaseFont.createFont("fonts/Roboto-Regular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font tableFont = new Font(bfTableFont,8,Font.NORMAL);            
            float pageWidth = document.getPageSize().getWidth();
            float pageHeight = document.getPageSize().getHeight();
            float lm = 0;
            float rm = 0;
            Image logo = Image.getInstanceFromClasspath("img/PINES_LOGO.jpg");
            logo.scaleAbsolute(pageWidth,pageHeight);
            logo.setAbsolutePosition(lm, rm);
            logo.setBorder(15);
            logo.setBorderWidth(5);
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(logo);

            ColumnText ct = new ColumnText(canvas);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'a' d 'de' MMMM 'del' yyyy", new Locale("es", "ES"));
            String formatedDate = date.format(formatter);
            ct.addElement(new Paragraph("Guadalajara jalisco, " + formatedDate + "\n\n", tableFont));
            ct.setSimpleColumn(pageWidth-250, pageHeight-175, pageWidth-50, pageHeight-125);
            ct.go();

        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar PINSES: Inicio de pagina", JOptionPane.ERROR_MESSAGE);
        }
    }
}
