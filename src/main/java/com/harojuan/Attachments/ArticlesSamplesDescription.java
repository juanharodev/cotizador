package com.harojuan.Attachments;

import java.awt.Color;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.harojuan.QuotationModel.QuotationEntry;
import com.harojuan.QuotationModel.QuotationTable;
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

public class ArticlesSamplesDescription extends PdfPageEventHelper{
        public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{  
        float fontSize = 11;
        float smalFontSize = 8;
        BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
        BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font smallFont = new Font(bfBoldTableFont,smalFontSize,Font.NORMAL);
        Font boldFont =  new Font(bfBoldTableFont,fontSize,Font.NORMAL);

        Document document = new Document(PageSize.A4, 36, 36, 0, 36);
        float pageWidth = document.getPageSize().getWidth();

        if(!fileName.endsWith(".pdf")){
            fileName += ".pdf";
        }

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
        writer.setPageEvent(new TechnicalAttachement());
        document.open();

        addFormat(tableReference, data, fontSize, regularFont, boldFont, smallFont, document, pageWidth,"12 - A - ORIGINAL");
        document.newPage();
        addFormat(tableReference, data, fontSize, regularFont, boldFont, smallFont,document, pageWidth,"12 - B - ACUSE");

        document.close();
        writer.close();
    }

        @SuppressWarnings("deprecation")
        private void addFormat(QuotationTable tableReference, AttachmentData data, float fontSize, Font regularFont,
                Font boldFont, Font smallFont, Document document, float pageWidth,String type) {
            
            Paragraph mainParagraph = new Paragraph();

            mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            mainParagraph.setLeading(fontSize + 1);
            mainParagraph.add(new Phrase("PODER EJECUTIVO DEL ESTADO DE JALISCO\n",regularFont));
            mainParagraph.add(new Phrase(data.number + "\n",regularFont));
            mainParagraph.add(new Phrase(data.type + "\n",regularFont));
            mainParagraph.add(new Phrase(data.name + "\n",regularFont));
            mainParagraph.add(new Phrase(data.committee + "\n\n",regularFont));
            mainParagraph.add(new Phrase("ANEXO 12 - FORMATO DE MUESTRAS\n\n",regularFont));
            document.add(mainParagraph);

            mainParagraph = new Paragraph();
            mainParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d 'de' yyyy", new Locale("es", "ES"));
            String formatedDate = data.date.format(formatter);
            mainParagraph.add(new Phrase(formatedDate, regularFont));
            document.add(mainParagraph);
            
            mainParagraph = new Paragraph();
            mainParagraph.add(new Phrase(type + "\n\n",regularFont));
            document.add(mainParagraph);

            float[] colSize = {8,8,8,76};
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
            table.addCell(new Paragraph("PARTIDA",smallFont));
            table.addCell(new Phrase("CANTIDAD",smallFont));
            table.addCell(new Phrase("U/M",smallFont));
            table.addCell(new Phrase("ARTICULOS/DESCRIPCIÓN",smallFont));

            QuotationEntry entry;
            table.getDefaultCell().setBackgroundColor(new Color(255,255,255));
            for(int i = 0; i<tableReference.getEntries().size(); i++){
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                entry = tableReference.getEntries().get(i);
                table.addCell(new Phrase(Integer.toString(i+1),regularFont)); //Partida
                table.addCell(new Phrase("1",regularFont)); //Cantidad
                table.addCell(new Phrase(tableReference.getEntries().get(i).measureUnit,regularFont)); //UM
                table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
                table.addCell(new Phrase(entry.description,regularFont));  //Descripcion
            }
            document.add(table);
            
            mainParagraph = new Paragraph();
            mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            mainParagraph.setLeading(10);
            
            mainParagraph.add(new Phrase("\nATENTAMENTE\n\n\n\n",regularFont));
            mainParagraph.add(new Phrase("__________________________________\n",regularFont));
            mainParagraph.add(new Phrase(data.responsible + "\n",regularFont));
            mainParagraph.add(new Phrase("Representante Legal\n",regularFont));
            mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.\n",regularFont));

            document.add(mainParagraph);
        }
}