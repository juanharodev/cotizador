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

public class QuotationOFFSET extends PdfPageEventHelper implements QuotationExporter{

    @Override
    public void ExporQuotation(QuotationTable tableReference, int PriceToExport, String filePath, String fileName) {        
        try{   
            BaseFont bfTableFont = BaseFont.createFont("fonts/ARIAL.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font tableFont = new Font(bfTableFont,8,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/ARIALBD.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
            Font boldFont =  new Font(bfBoldTableFont,8,Font.NORMAL);    
            Font whiteTableFont = new Font(bfTableFont,8,Font.NORMAL,Color.WHITE);
            // step 1: create a document object with margin size based on the header and footer content
            Document document = new Document(PageSize.A4, 36, 36, 0, 36);
            float pageWidth = document.getPageSize().getWidth();

            if(!fileName.endsWith(".pdf")){
                fileName += ".pdf";
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
            writer.setPageEvent(new QuotationOFFSET());
            document.open();

            PdfPTable titleTable = new PdfPTable(2);
            titleTable.getDefaultCell().setBorder(0);
            titleTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_TOP);
            titleTable.setHorizontalAlignment(0);
            titleTable.setTotalWidth(pageWidth - 60);
            titleTable.setLockedWidth(true);
            
            
            titleTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            Paragraph p1 = new Paragraph(tableReference.receiver + System.lineSeparator(), tableFont);
            titleTable.addCell(p1);
            
            PdfPCell c2 = new PdfPCell();
            c2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            c2.setVerticalAlignment(PdfPCell.ALIGN_TOP);
            c2.setBorder(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d 'de' yyyy", new Locale("es", "ES"));
            String formatedDate = tableReference.date.format(formatter);
            c2.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
            Paragraph p2 = new Paragraph("Guadalajara, Jalisco.\r\n" + 
                                formatedDate +"\r\n" + 
                                "No. Proveedor P09365\r\n" + 
                                "ISO: PENDIENTE\r\n", boldFont);
            c2.addElement(p2);
            titleTable.addCell(c2);

            document.add(titleTable);

            Paragraph p3 = new Paragraph();
            p3.add(new Phrase("ATENCIÓN: ", boldFont));
            p3.add(new Phrase("INEEJAD" + System.lineSeparator() + System.lineSeparator(),tableFont));
            p3.add(new Phrase("POR MEDIO DE LA PRESENTE HACEMOS DE SU CONOCIMIENTO EL COSTO DEL MATERIAL QUE A CONTINUACIÓN SE DESCRIBE:\n\n", tableFont));
            p3.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(p3);

            float[] colSize = {10,60,10,10,10};
            PdfPTable table = null;
            
            //table set up
            table = new PdfPTable(colSize);
            table.getDefaultCell().setBorder(15);
            table.getDefaultCell().setBorderWidth(1);
            table.getDefaultCell().setBackgroundColor(new Color(128,128,128));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(pageWidth - 60);
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
                table.addCell(new Phrase(String.format(Locale.US,"%,.2f", entry.amount),tableFont)); //Cantidad
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getPrice(PriceToExport)), tableFont));  //PU
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f", entry.getTotal(PriceToExport)),tableFont)); //IMPORTE
            }
            document.add(table);
            //Table set up
            float[] colSizeTotal = {70,10,10,10};
            PdfPTable totalTable = null;
            
            //table set up
            totalTable = new PdfPTable(colSizeTotal);
            totalTable.getDefaultCell().setBorder(15);
            totalTable.getDefaultCell().setBorderWidth(1F);
            totalTable.getDefaultCell().setBackgroundColor(new Color(128,128,128));
            totalTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            totalTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            totalTable.setHorizontalAlignment(0);
            totalTable.setTotalWidth(pageWidth - 60);
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
            termsParagraph = new Paragraph();
            termsParagraph.add(new Phrase("*Tiempo estimado de entrega: ", boldFont));
            termsParagraph.add(new Phrase("30 días naturales a partir de un día después de la firma del contrato.\n", tableFont));
            termsParagraph.add(new Phrase("*Condiciones de venta: ", boldFont));
            termsParagraph.add(new Phrase("30 días naturales de crédito después de la recepción de la mercancía.\n", tableFont));
            termsParagraph.add(new Phrase("*Garantía: ", boldFont));
            termsParagraph.add(new Phrase("Se repondrá aquel producto que por tener defectos de fabricación o impresión no reúna la calidad ofrecida.\n", tableFont));
            termsParagraph.add(new Phrase("*Vigencia: ", boldFont));
            termsParagraph.add(new Phrase("Esta cotización estará vigente 60 días después de la fecha de expedición de la misma.\n", tableFont));
            termsParagraph.add(new Phrase("*Los precios ya incluyen la impresión de su logotipo a una tinta en una posición.\n", tableFont));
            termsParagraph.add(new Phrase("* Mercancía L.A.B. Guadalajara. Todos los pedidos foráneos viajan por cuenta y riesgo del cliente.\n", tableFont));
            document.add(termsParagraph);
            
            document.add(new Paragraph("\n\n"));

            Image sign = Image.getInstanceFromClasspath("img/OFFSET_FIRMA.jpg");
            sign.scaleToFit(150,150);
            sign.setAlignment(Image.RIGHT);
            document.add(sign);


            document.close();
            writer.close();

        }catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar OFFSET", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    @Override
    public void onStartPage(PdfWriter writer, Document document){
        try {
            float pageWidth = document.getPageSize().getWidth();
            Image logo = Image.getInstanceFromClasspath("img/OFFSET_LOGO.jpg");
            logo.setAlignment(Image.ALIGN_CENTER);
            logo.scaleToFit(pageWidth,200);
            document.add(logo);
        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(null, de, "Error al exportar OFFSET: Inicio de pagina", JOptionPane.ERROR_MESSAGE);
        }
    }
}