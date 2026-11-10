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

public class QuotationPATSSIE extends PdfPageEventHelper implements QuotationExporter {
    
    @Override
    public void ExporQuotation(QuotationTable tableReference, int PriceToExport, String filePath, String fileName) {
 

        
        try{
            BaseFont bfTableFont = BaseFont.createFont("fonts/AdobeGaramondProRegular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font tableFont = new Font(bfTableFont,7,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/AdobeGaramondProRegular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font whiteTableFont = new Font(bfTableFont,7,Font.NORMAL, Color.WHITE);
            Font boldFont =  new Font(bfBoldTableFont,7,Font.NORMAL);
            // step 1: create a document object with margin size based on the header and footer content
            Document document = new Document(PageSize.A4, 50, 20, 80, 90);
            float pageWidth = document.getPageSize().getWidth();

            if(!fileName.endsWith(".pdf")){
                fileName += ".pdf";
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
            writer.setPageEvent(new QuotationPATSSIE());
            document.open();

            
            
            Paragraph p1 = new Paragraph();
            p1.setAlignment(Paragraph.ALIGN_RIGHT);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'a' d 'de' MMMM 'del' yyyy", new Locale("es", "ES"));
            String formatedDate = tableReference.date.format(formatter);

            p1.add(new Phrase("ISO ", tableFont));
            p1.add(new Phrase("PENDIENTE" + "\n", boldFont));
            p1.add(new Phrase("Guadalajara jalisco "+ formatedDate + "\n", tableFont));
            p1.add(new Phrase("Mediante la presente cotización, deseamos informarle sobre el precio\n",tableFont));
            p1.add(new Phrase("del servicio que detallamos a continuación.\n\n",tableFont));
            document.add(p1);
            

            float[] colSize = {8,12,10,40,15,15};
            PdfPTable table = null;
            
            //table set up
            table = new PdfPTable(colSize);
            table.getDefaultCell().setBorder(15);
            table.getDefaultCell().setBorderWidth(1);
            table.getDefaultCell().setBackgroundColor(new Color(255,192,0));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            table.setTotalWidth(pageWidth - 100);
            table.setLockedWidth(true);
            
            table.addCell(new Phrase("PARTIDA",whiteTableFont));
            table.addCell(new Phrase("CANTIDAD",whiteTableFont));
            table.addCell(new Phrase("U.M.",whiteTableFont));
            table.addCell(new Phrase("DESCRIPCIÓN",whiteTableFont));
            table.addCell(new Phrase("P.U.",whiteTableFont));
            table.addCell(new Phrase("TOTAL",whiteTableFont));

  
            QuotationEntry entry;
            table.getDefaultCell().setBackgroundColor(new Color(255,255,255,255));
     
            for(int i = 0; i<tableReference.getEntries().size(); i++){
                entry = tableReference.getEntries().get(i);
                table.addCell(new Phrase(Integer.toString(i + 1),tableFont)); //PARTIDA
                table.addCell(new Phrase(String.format(Locale.US,"%,.2f", entry.amount),tableFont)); //Cantidad
                table.addCell(new Phrase(tableReference.getEntries().get(i).measureUnit,tableFont)); //UM
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
                table.addCell(new Phrase(entry.name + " " + entry.description,tableFont));  //Descripcion
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getPrice(PriceToExport)), tableFont));  //PU
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getTotal(PriceToExport)), tableFont)); //IMPORTE
            }
            document.add(table);
            
            //Table set up
            float[] colSizeTotal = {70,15,15};
            PdfPTable totalTable = null;

            totalTable = new PdfPTable(colSizeTotal);
            totalTable.getDefaultCell().setBorder(0);
            totalTable.getDefaultCell().setBorderWidth(0.1F);
            totalTable.getDefaultCell().setBackgroundColor(new Color(255,255,255,0));
            totalTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            totalTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            totalTable.setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
            totalTable.setTotalWidth(pageWidth - 250);
            totalTable.setLockedWidth(true);



            totalTable.addCell(new Paragraph("Cantidad con letra:",boldFont));
            totalTable.addCell( new Paragraph("SUBTOTAL ",boldFont));
            totalTable.addCell(new Paragraph("$ " +   String.format(Locale.US,"%,.2f" ,tableReference.getSubtotal(PriceToExport)),tableFont));
            
            totalTable.addCell( new Paragraph(NumeroALetra.numeroALetras(tableReference.getGrandTotal(PriceToExport)),boldFont));
            totalTable.addCell(new Paragraph("IVA 16%",boldFont));
            totalTable.addCell( new Paragraph("$ " +   String.format(Locale.US,"%,.2f" ,tableReference.getIVA(PriceToExport)),tableFont));
            
            totalTable.addCell(new Paragraph(" ",boldFont));
            totalTable.addCell(new Paragraph("TOTAL ",boldFont));
            totalTable.addCell(new Paragraph("$ " +   String.format(Locale.US,"%,.2f" ,tableReference.getGrandTotal(PriceToExport)),tableFont));

            //Add to doc
            document.add(totalTable);

            tableFont.setSize(12);
            Paragraph termsParagraph = null;
            termsParagraph = new Paragraph();
            termsParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
            termsParagraph.add(new Phrase("\nQuedo a sus órdenes para cualquier duda de la presente. ", tableFont));
            document.add(termsParagraph);

            Image sign = Image.getInstanceFromClasspath("img/PATSSIE_FIRMA.jpg");
            sign.scaleToFit(150,150);
            sign.setAlignment(Image.RIGHT);
            document.add(sign);


            document.close();
            writer.close();

        }catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar PATSSIE", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document){
        try {
            float pageWidth = document.getPageSize().getWidth();
            float pageHeight = document.getPageSize().getHeight();
            float lm = 0;
            float rm = 0;
            Image logo = Image.getInstanceFromClasspath("img/PATSSIE_LOGO.jpg");
            logo.scaleAbsolute(pageWidth,pageHeight);
            logo.setAbsolutePosition(lm, rm);
            logo.setBorder(15);
            logo.setBorderWidth(5);
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(logo);
        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar PATSSIE: Inicio de pagina", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override 
    public void onEndPage(PdfWriter writer, Document document){
        try {
            float fontSize = 8f;
            BaseFont bfFont = BaseFont.createFont("fonts/AdobeGaramondProRegular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font font = new Font(bfFont,fontSize,Font.NORMAL, Color.WHITE);
            Font yellowFont = new Font(bfFont,fontSize + 2,Font.BOLD,new Color(255,192,0));
            Font boldFont = new Font(bfFont,fontSize,Font.BOLD,Color.WHITE);
            PdfContentByte canvas = writer.getDirectContent();
            canvas.setCharacterSpacing(.3F);
            ColumnText ct = new ColumnText(canvas);
            
            Paragraph paragraph = new Paragraph();
            paragraph.setLeading(11);
            paragraph.add(new Phrase("TERMINOS Y CONDICIONES\n",yellowFont));
            
            paragraph.add(new Phrase(("Tiempo de entrega"),boldFont));
            paragraph.add(new Phrase((": 30 días hábiles.\n"),font));

            paragraph.add(new Phrase(("Condiciones de pago"),boldFont));
            paragraph.add(new Phrase((": Crédito 45 días.\n"),font));

            paragraph.add(new Phrase(("Garantía"),boldFont));
            paragraph.add(new Phrase((": Dos meses a partir de su recepción.\n"),font));

            paragraph.add(new Phrase(("Vigencia"),boldFont));
            paragraph.add(new Phrase((": 60 Días naturales.\n"),font));
            ct.addElement(paragraph);
            // fill first box
            ct.setSimpleColumn(30, 0, 250, 85);
            ct.go();
            

        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar ENVI: Inicio de pagina", JOptionPane.ERROR_MESSAGE);
        }
    }
}
