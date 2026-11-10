package com.harojuan.Attachments;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JOptionPane;

import com.harojuan.QuotationModel.QuotationEntry;
import com.harojuan.QuotationModel.QuotationTable;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class TechnicalAttachement extends PdfPageEventHelper{
    public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{
            float fontSize = 14;
            float smallFontSize = 8;
            BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            Font smallRegularFont = new Font(bRegularFont,smallFontSize,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            Font smallBoldFont =  new Font(bfBoldTableFont,smallFontSize,Font.NORMAL);

            Document document = new Document(PageSize.A4, 36, 36, 0, 36);
            float pageWidth = document.getPageSize().getWidth();

            if(!fileName.endsWith(".pdf")){
                fileName += ".pdf";
            }

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
            writer.setPageEvent(new TechnicalAttachement());
            document.open();

            Paragraph mainParagraph = new Paragraph();
            
            mainParagraph = new Paragraph();
            mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            mainParagraph.setLeading(smallFontSize + 1);
            mainParagraph.add(new Phrase("PODER EJECUTIVO DEL ESTADO DE JALISCO\n",smallRegularFont));
            mainParagraph.add(new Phrase( data.number + "\n",smallRegularFont));
            mainParagraph.add(new Phrase( data.type + "\n",smallRegularFont));
            mainParagraph.add(new Phrase( data.name + "\n",smallRegularFont));
            mainParagraph.add(new Phrase( data.committee + "\n\n\n",smallRegularFont));
            mainParagraph.add(new Phrase( "ANEXO 1 - TÉCNICO\n\n",smallRegularFont));

            document.add(mainParagraph);
            
            float[] colSize = {10,10,10,70};
            PdfPTable table = null;
            
            //table set up
            table = new PdfPTable(colSize);
            table.getDefaultCell().setBorder(15);
            table.getDefaultCell().setBorderWidth(1);
            table.getDefaultCell().setBorderColor(Color.BLACK);
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(pageWidth - 60);
            table.setLockedWidth(true);
            
            table.getDefaultCell().setBackgroundColor(new Color(199, 199, 198));
            table.addCell(new Paragraph("PARTIDA",smallRegularFont));
            table.addCell(new Phrase("CANTIDAD",smallRegularFont));
            table.addCell(new Phrase("U/M",smallRegularFont));
            table.addCell(new Phrase("ARTICULOS/DESCRIPCIÓN",smallRegularFont));

            QuotationEntry entry;
            table.getDefaultCell().setBackgroundColor(new Color(255,255,255));
            for(int i = 0; i<tableReference.getEntries().size(); i++){
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                entry = tableReference.getEntries().get(i);
                table.addCell(new Phrase(Integer.toString(i+1),smallRegularFont)); //Partida
                table.addCell(new Phrase(String.format("%.2f", entry.amount),smallRegularFont)); //Cantidad
                table.addCell(new Phrase(tableReference.getEntries().get(i).measureUnit,smallRegularFont)); //UM
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
                table.addCell(new Phrase(entry.name + "\n" + entry.description,smallRegularFont));  //Descripcion
            }
            document.add(table);

            mainParagraph = new Paragraph();
            mainParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            mainParagraph.setLeading(10);

            mainParagraph.add(new Phrase("\nOBLIGACIONES\n",smallBoldFont));
            mainParagraph.add(new Phrase(data.obligations + "\n\n",smallRegularFont));
            
            mainParagraph.add(new Phrase("LUGAR DE ENTREGA\n",smallBoldFont));
            mainParagraph.add(new Phrase(data.deliveryPlace + "\n\n",smallRegularFont));

            mainParagraph.add(new Phrase("TIEMPO DE ENTREGA\n",smallBoldFont));
            mainParagraph.add(new Phrase(data.deliveryTime + "\n\n",smallRegularFont));

            mainParagraph.add(new Phrase("GARANTÍA\n",smallBoldFont));
            mainParagraph.add(new Phrase(data.warranty + "\n\n",smallRegularFont));

            mainParagraph.add(new Phrase("CERTIFICACIONES\n ",smallBoldFont));
            mainParagraph.add(new Phrase(data.obligations +  " " + data.certifications + "\n\n\n\n",smallRegularFont));

            document.add(mainParagraph);

            mainParagraph = new Paragraph();
            mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            mainParagraph.setLeading(10);
            
            mainParagraph.add(new Phrase("ATENTAMENTE\n\n\n\n",smallRegularFont));
            mainParagraph.add(new Phrase("__________________________________\n",smallRegularFont));
            mainParagraph.add(new Phrase(data.responsible + "\n",smallRegularFont));
            mainParagraph.add(new Phrase("Representante Legal\n",smallRegularFont));
            mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.\n",smallRegularFont));

            document.add(mainParagraph);

            document.close();
            writer.close();
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
    }
}
