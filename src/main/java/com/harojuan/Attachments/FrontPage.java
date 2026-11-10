package com.harojuan.Attachments;


import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


import com.harojuan.QuotationModel.QuotationTable;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class FrontPage extends PdfPageEventHelper{
    public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{
        float fontSize = 14;
        float smallFontSize = 8;
        BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
        Font smallRegularFont = new Font(bRegularFont,smallFontSize,Font.NORMAL);
        BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font boldFont =  new Font(bfBoldTableFont,fontSize,Font.NORMAL);

        Document document = new Document(PageSize.A4, 36, 36, 0, 36);

        if(!fileName.endsWith(".pdf")){
            fileName += ".pdf";
        }

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
        writer.setPageEvent(new TechnicalAttachement());
        document.open();

        Paragraph mainParagraph = new Paragraph();
        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.add(new Phrase("\n\nGOBIERNO DEL ESTADO DE JALISCO\n",boldFont));
        mainParagraph.add(new Phrase("\n\nPODER EJECUTIVO DEL ESTADO DE JALISCO\n",boldFont));
        mainParagraph.add(new Phrase("\n\nSECRETARÍA DE ADMINISTRACIÓN\n",boldFont));
        mainParagraph.add(new Phrase("\n\nDIRECCIÓN DEL COMITÉ DE ADQUISICIONES\n",boldFont));
        mainParagraph.add(new Phrase("\n\nPROPUESTA TÉCNICA Y ECONÓMICA\n",boldFont));
        
        mainParagraph.add(new Phrase("\n\n"+data.number + "\n",regularFont));
        mainParagraph.add(new Phrase(data.type + "\n",regularFont));
        mainParagraph.add(new Phrase(data.name + "\n",regularFont));
        mainParagraph.add(new Phrase(data.committee + "\n\n",regularFont));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d 'de' yyyy", new Locale("es", "ES"));
        String formatedDate = data.date.format(formatter);
        mainParagraph.add(new Phrase(formatedDate + "\n\n\n",regularFont));
        
        mainParagraph.add(new Phrase("NÚMERO DE PROVEEDOR: P30289\n",regularFont));
        mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.\r",regularFont));

        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.setLeading(10);
        mainParagraph.add(new Phrase("\n\n\nATENTAMENTE", smallRegularFont));
        mainParagraph.add(new Phrase("\n\n\n\n__________________________________", smallRegularFont));
        mainParagraph.add(new Phrase("\nCarlos Vidarte Rodríguez\n", smallRegularFont));
        mainParagraph.add(new Phrase("Representante Legal\r\n", smallRegularFont));
        mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.\n", smallRegularFont));
        
        document.add(mainParagraph);

        document.close();
        writer.close();
        System.gc();
        Thread.sleep(50);
    }
}
