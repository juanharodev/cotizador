package com.harojuan.Attachments;

import java.io.FileOutputStream;
import java.io.IOException;

import com.harojuan.QuotationModel.QuotationTable;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.events.PdfPageEventForwarder;

public class IDAttachment extends PdfPageEventForwarder{
       public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{  
        float fontSize = 9;
        BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);


        Document document = new Document(PageSize.A4, 36, 36, 0, 36);

        if(!fileName.endsWith(".pdf")){
            fileName += ".pdf";
        }

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
        writer.setPageEvent(new TechnicalAttachement());
        document.open();

        AddIDPage(data, fontSize, regularFont, document,"img/IDCarlos.jpg");
        document.newPage();
        AddIDPage(data, fontSize, regularFont, document,"img/IDVioleta.jpg");

        document.close();
        writer.close();
    }

       private void AddIDPage(AttachmentData data, float fontSize, Font regularFont, Document document, String idPath)
            throws IOException {
        Paragraph mainParagraph = new Paragraph();

        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase("PODER EJECUTIVO DEL ESTADO DE JALISCO\n",regularFont));
        mainParagraph.add(new Phrase(data.number + "\n",regularFont));
        mainParagraph.add(new Phrase(data.type + "\n",regularFont));
        mainParagraph.add(new Phrase(data.name + "\n",regularFont));
        mainParagraph.add(new Phrase(data.committee + "\n\n",regularFont));
        mainParagraph.add(new Phrase("IDENTIFICACIÓN\n\n",regularFont));
        document.add(mainParagraph);

        Image idImage = Image.getInstanceFromClasspath(idPath);
        idImage.setAlignment(Image.MIDDLE);
        idImage.scaleAbsolute(idImage.getWidth()/5, idImage.getHeight()/5);
        document.add(idImage);

        mainParagraph = new Paragraph();
        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.setLeading(10);
        
        mainParagraph.add(new Phrase("ATENTAMENTE\n\n\n\n",regularFont));
        mainParagraph.add(new Phrase("__________________________________\n",regularFont));
        mainParagraph.add(new Phrase("Carlos Vidarte Rodríguez\r\n",regularFont));
        mainParagraph.add(new Phrase("Representante Legal\n",regularFont));
        mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.",regularFont));
        document.add(mainParagraph);
       }
}
