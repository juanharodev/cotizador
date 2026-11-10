package com.harojuan.Attachments;

import java.awt.Color;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.harojuan.QuotationModel.QuotationEntry;
import com.harojuan.QuotationModel.QuotationTable;
import com.harojuan.Utils.NumeroALetra;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class EconomicProposal extends PdfPageEventHelper {
    public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{  
        float fontSize = 9;
        BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
        BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font boldFont =  new Font(bfBoldTableFont,fontSize,Font.NORMAL);

        Document document = new Document(PageSize.A4, 36, 36, 0, 36);
        float pageWidth = document.getPageSize().getWidth();

        if(!fileName.endsWith(".pdf")){
            fileName += ".pdf";
        }

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
        writer.setPageEvent(new TechnicalAttachement());
        document.open();

        Paragraph mainParagraph = new Paragraph();

        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase("PODER EJECUTIVO DEL ESTADO DE JALISCO\n",regularFont));
        mainParagraph.add(new Phrase(data.number + "\n",regularFont));
        mainParagraph.add(new Phrase(data.type + "\n",regularFont));
        mainParagraph.add(new Phrase(data.name + "\n",regularFont));
        mainParagraph.add(new Phrase(data.committee + "\n\n",regularFont));
        mainParagraph.add(new Phrase("ANEXO 5 - PROPUESTA ECONÓMICA\n\n",regularFont));
        document.add(mainParagraph);
        
        mainParagraph = new Paragraph();
        mainParagraph.add(new Phrase("Nombre o Razón Social: DDV SOLUCIONES S.A DE C.V.\n\n",regularFont));
        document.add(mainParagraph);

        float[] colSize = {10,10,10,50,10,10};
            PdfPTable table = null;
            
            //table set up
            table = new PdfPTable(colSize);
            table.getDefaultCell().setBorder(15);
            table.getDefaultCell().setBorderWidth(.1F);
            table.getDefaultCell().setBorderColor(Color.BLACK);
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(pageWidth - 60);
            table.setLockedWidth(true);
            
            table.getDefaultCell().setBackgroundColor(new Color(199, 199, 198));
            table.addCell(new Paragraph("PARTIDA",boldFont));
            table.addCell(new Phrase("CANTIDAD",boldFont));
            table.addCell(new Phrase("U/M",boldFont));
            table.addCell(new Phrase("ARTICULOS/DESCRIPCIÓN",boldFont));
            table.addCell(new Phrase("P.U.",boldFont));
            table.addCell(new Phrase("MONTO",boldFont));

            QuotationEntry entry;
            table.getDefaultCell().setBackgroundColor(new Color(255,255,255));
            for(int i = 0; i<tableReference.getEntries().size(); i++){
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                entry = tableReference.getEntries().get(i);
                table.addCell(new Phrase(Integer.toString(i+1),regularFont)); //Partida
                table.addCell(new Phrase(String.format("%.2f", entry.amount),regularFont)); //Cantidad
                table.addCell(new Phrase(tableReference.getEntries().get(i).measureUnit,regularFont)); //UM
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
                table.addCell(new Phrase(entry.name,regularFont));  //Descripcion
                table.getDefaultCell().setHorizontalAlignment(Phrase.ALIGN_CENTER);
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f" ,entry.getPrice(QuotationEntry.BASE)),regularFont));  //PU
                table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f" ,entry.getTotal(QuotationEntry.BASE)),regularFont));  //Monto
            }

        
        PdfPCell specialCell = new PdfPCell();
        specialCell.setBorderWidth(.1F);
        specialCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        specialCell.setColspan(4);
        Paragraph specialParagraph = new Paragraph("CONDICIONES DE PAGO: AL FINALIZAR EL SERVICIO.",regularFont);
        specialParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        specialCell.addElement(specialParagraph);
        table.addCell(specialCell);
        table.addCell(new Phrase("SUBTOTAL",regularFont));
        table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f" ,tableReference.getSubtotal(QuotationEntry.BASE)),regularFont));

        specialCell = new PdfPCell();
        specialCell.setBorderWidth(.1F);
        specialCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        specialCell.setColspan(4);
        specialParagraph = new Paragraph("CANTIDAD CON LETRA:",regularFont);
        specialParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        specialCell.addElement(specialParagraph);
        table.addCell(specialCell);
        table.addCell(new Phrase("I.V.A.",regularFont));
        table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f" ,tableReference.getIVA(QuotationEntry.BASE)),regularFont));

        specialCell = new PdfPCell();
        specialCell.setBorderWidth(.1F);
        specialCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        specialCell.setColspan(4);
        specialParagraph = new Paragraph(NumeroALetra.numeroALetras(tableReference.getGrandTotal(QuotationEntry.BASE)),regularFont);
        specialParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        specialCell.addElement(specialParagraph);
        table.addCell(specialCell);
        table.addCell(new Phrase("TOTAL",regularFont));
        table.addCell(new Phrase("$" + String.format(Locale.US,"%,.2f" ,tableReference.getGrandTotal(QuotationEntry.BASE)),regularFont));
        document.add(table);

        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase("\nCONDICIONES DE PAGO\n",boldFont));
        mainParagraph.add(new Phrase(data.paymentConditions + "\n\n",regularFont));
        
        mainParagraph.add(new Phrase("TIEMPO DE ENTREGA\n ",boldFont));
        mainParagraph.add(new Phrase(data.deliveryTime + "\n\n",regularFont));
        
        mainParagraph.add(new Phrase("GARANTÍA\n",boldFont));
        mainParagraph.add(new Phrase(data.warranty + "\n\n",regularFont));

        

        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.setLeading(10);
        
        mainParagraph.add(new Phrase("ATENTAMENTE\n\n\n\n",regularFont));
        mainParagraph.add(new Phrase("__________________________________\n",regularFont));
        mainParagraph.add(new Phrase(data.responsible + "\n",regularFont));
        mainParagraph.add(new Phrase("Representante Legal\n",regularFont));
        mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.\n\n",regularFont));
        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase("Lugar de firma: "+ data.signPlace + "\n",regularFont));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d 'de' yyyy", new Locale("es", "ES"));
        String formatedDate = data.date.format(formatter);
        mainParagraph.add(new Phrase("Fecha: "+ formatedDate,regularFont));
        document.add(mainParagraph);

        document.close();
        writer.close(); 
    }
}
