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
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class QuotationMETRIK extends PdfPageEventHelper implements QuotationExporter{

    String path;
    @Override
    public void ExporQuotation(QuotationTable tableReference, int PriceToExport, String filePath, String fileName) {
        
        
        try{
            BaseFont bfTableFont = BaseFont.createFont("fonts/arialnarrow.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font tableFont = new Font(bfTableFont,8,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/arialnarrow_bold.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font boldFont =  new Font(bfBoldTableFont,8,Font.NORMAL);

            // step 1: create a document object with margin size based on the header and footer content
            Document document = new Document(PageSize.A4, 36, 36, 70, 36);
            float pageWidth = document.getPageSize().getWidth();

            if(!fileName.endsWith(".pdf")){
                fileName += ".pdf";
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
            writer.setPageEvent(new QuotationMETRIK());
            document.open();

            PdfPTable titleTable = new PdfPTable(3);
            titleTable.getDefaultCell().setBorder(0);
            titleTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_TOP);
            titleTable.setHorizontalAlignment(0);
            titleTable.setTotalWidth(pageWidth - 60);
            titleTable.setLockedWidth(true);
            
            
            titleTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            Paragraph p1 = new Paragraph(tableReference.receiver + System.lineSeparator(), tableFont);
            titleTable.addCell(p1);
            
            PdfPCell c2 = new PdfPCell();
            c2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            c2.setVerticalAlignment(PdfPCell.ALIGN_TOP);
            c2.setBorder(0);
            Paragraph p2 = new Paragraph("Presente.-\n", boldFont);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            c2.addElement(p2);
            titleTable.addCell(c2);
            
            PdfPCell c3 = new PdfPCell();
            c3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            c3.setVerticalAlignment(PdfPCell.ALIGN_BASELINE);
            c3.setBorder(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
            String formatedDate = tableReference.date.format(formatter);
            Paragraph p3 = new Paragraph("\nTonalá, Jalisco.\n" + formatedDate + "\n ISO - PENDIENTE", tableFont);
            p3.setAlignment(Paragraph.ALIGN_RIGHT);
            c3.addElement(p3);
            titleTable.addCell(c3);

            document.add(titleTable);

            Paragraph p4 = new Paragraph("PROPUESTA TÉCNICA Y ECONÓMICA\n\n", boldFont);
            p4.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p4);

            float[] colSize = {3,7,55,7,7,7,7,7};
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
            partidaCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

            partidaCell.addElement(new Paragraph(" ",tableFont));
            table.addCell(partidaCell);
            table.addCell(new Phrase("U/M",tableFont));
            table.addCell(new Phrase("CÓDIGO / NOMBRE / DESCRIPCIÓN",tableFont));
            table.addCell(new Phrase("CANTIDAD",tableFont));
            table.addCell(new Phrase("P.U.",tableFont));
            table.addCell(new Phrase("IMPORTE",tableFont));
            table.addCell(new Phrase("IVA",tableFont));
            table.addCell(new Phrase("TOTAL",tableFont));

  
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
                table.addCell(new Phrase(tableReference.getEntries().get(i).measureUnit,tableFont)); //UM
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
                table.addCell(new Phrase(entry.name + " " + entry.description,tableFont));  //Descripcion
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
                table.addCell(new Phrase(String.format(Locale.US,"%,.2f", entry.amount),tableFont)); //Cantidad
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getPrice(PriceToExport)), tableFont));  //PU
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getTotal(PriceToExport)),tableFont)); //IMPORTE
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getTotal(PriceToExport) * 0.6F),tableFont)); //IVA
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getTotal(PriceToExport)*1.6F),tableFont)); //TOTAL

            }
            document.add(table);
            document.add(new Paragraph("\n"));

            //Table set up
            float[] colSizeTotal = {5,65,10,10,10};
            PdfPTable totalTable = null;
            
            //table set up
            totalTable = new PdfPTable(colSizeTotal);
            totalTable.getDefaultCell().setBorder(0);
            totalTable.getDefaultCell().setBorderWidth(0F);
            totalTable.getDefaultCell().setBackgroundColor(new Color(217,225,242));
            totalTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            totalTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            totalTable.setHorizontalAlignment(0);
            totalTable.setTotalWidth(pageWidth - 60);
            totalTable.setLockedWidth(true);
            
            //Content
            partidaCell = new PdfPCell(new Phrase(" "));
            partidaCell.setBorder(0);
            partidaCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            totalTable.addCell(partidaCell);
            
            Paragraph totalParagraph;
           
            totalParagraph = new Paragraph();
            totalParagraph.add(new Phrase("CANTIDAD CON LETRA:\n",boldFont));
            totalParagraph.add(new Phrase(NumeroALetra.numeroALetras(tableReference.getGrandTotal(PriceToExport)),tableFont));
            totalTable.addCell(totalParagraph);
            totalTable.addCell(new Paragraph("$ " + String.format(Locale.US,"%,.2f" ,tableReference.getSubtotal(PriceToExport)),boldFont));
            totalTable.addCell( new Paragraph("$ " + String.format(Locale.US,"%,.2f" ,tableReference.getIVA(PriceToExport)),boldFont));
            totalTable.addCell(new Paragraph("$ " +   String.format(Locale.US,"%,.2f" ,tableReference.getGrandTotal(PriceToExport)),boldFont));
            //Add to doc
            document.add(totalTable);

            PdfPTable termsTable = new PdfPTable(new float[] {70,30});
            termsTable.getDefaultCell().setBorder(0);
            termsTable.getDefaultCell().setBorderWidth(0);
            termsTable.getDefaultCell().setBackgroundColor(new Color(255,255,255,0));
            termsTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            termsTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            termsTable.setHorizontalAlignment(0);
            termsTable.setTotalWidth(pageWidth - 60);
            termsTable.setLockedWidth(true);

            Paragraph termsParagraph = null;
            termsParagraph = new Paragraph();
            termsParagraph.add(new Phrase("*Tiempo estimado de entrega: ", boldFont));
            termsParagraph.add(new Phrase("30 días naturales a partir de un día después de la firma del contrato.\n", tableFont));
            termsParagraph.add(new Phrase("*Condiciones de pago: ", boldFont));
            termsParagraph.add(new Phrase("30 días naturales de crédito después de la recepción de la mercancía.\n", tableFont));
            termsParagraph.add(new Phrase("*Garantía: ", boldFont));
            termsParagraph.add(new Phrase("Se repondrá aquel producto que por tener defectos de fabricación o impresión no reúna la calidad ofrecida.\n", tableFont));
            termsParagraph.add(new Phrase("*Vigencia: ", boldFont));
            termsParagraph.add(new Phrase("Esta cotización estará vigente 60 días después de la fecha de expedición de la misma.\n", tableFont));
            termsParagraph.add(new Phrase("*Los precios ya incluyen la impresión de su logotipo a una tinta en una posición.\n", tableFont));
            termsParagraph.add(new Phrase("* Mercancía L.A.B. Guadalajara. Todos los pedidos foráneos viajan por cuenta y riesgo del cliente. \n", tableFont));
            termsTable.addCell(termsParagraph);
            
            termsTable.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
            Image sign = Image.getInstanceFromClasspath("img/METRIK_FIRMA.jpg");
            sign.scaleToFit(150,150);
            sign.setAlignment(Image.RIGHT);
            termsTable.addCell(sign);

            document.add(termsTable);

            document.close();
            writer.close();
        }catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar METRIK", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    @Override
    public void onStartPage(PdfWriter writer, Document document){
        try {
            float pageWidth = document.getPageSize().getWidth();
            float pageHeight = document.getPageSize().getHeight();
            float lm = 0;
            float rm = 0;
            Image logo = Image.getInstanceFromClasspath("img/METRIK_LOGO.jpg");
            logo.scaleAbsolute(pageWidth,pageHeight);
            logo.setAbsolutePosition(lm, rm);
            logo.setBorder(15);
            logo.setBorderWidth(5);
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addImage(logo);
        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar METRIK: Inicio de pagina", JOptionPane.ERROR_MESSAGE);
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
            bfTableFont = BaseFont.createFont("fonts/arialnarrow.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);   
            Font footerFont = new Font(bfTableFont,8,Font.NORMAL);
            Paragraph footer = new Paragraph();
            footer.setAlignment(Paragraph.ALIGN_CENTER);
            footer.add(new Phrase("AV. Periférico No. 70L | Jalisco Etapa 3. | C.P. 45412 | Tonalá, Jalisco, México. | Tel. 33 2173 9660 | metriklira@gmail.com\r\n" +
                        "Persona física con actividad empresarial Salvador Ramírez Lira | RFC: RALS880101RJ3 | RUPC: P37024", footerFont));
        table.addCell(footer);
        table.writeSelectedRows(0, -1, 34, 36, writer.getDirectContent());
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
