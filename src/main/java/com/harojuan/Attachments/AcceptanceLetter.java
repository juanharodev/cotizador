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

public class AcceptanceLetter extends PdfPageEventHelper{
    public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{  
        float fontSize = 11;
        BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
        BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font boldFont =  new Font(bfBoldTableFont,fontSize - 1,Font.NORMAL);

        Document document = new Document(PageSize.A4, 36, 36, 0, 36);

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
        mainParagraph.add(new Phrase("ANEXO 7 - " + data.number + " " + data.name + "\n\n",regularFont));
        mainParagraph.add(new Phrase("CARTA DE ACEPTACIÓN A LA APORTACIÓN CINCO AL MILLAR PARA FONDO IMPULSO JALISCO.\n\n",regularFont));
        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.add(new Phrase("La suscrito ",regularFont));
        mainParagraph.add(new Phrase("DDV SOLUCIONES S.A. DE C.V.",boldFont));
        mainParagraph.add(new Phrase(", por mi propio derecho, en mi carácter de REPRESENTANTE LEGAL, en cumplimiento al artículo 59, numeral" +
                            "1, fracción XVI, y 145 numeral 1 Fracción I de la Ley de Compras Gubernamentales, Enajenaciones y Contratación de Servicios del Estado de Jalisco y" +
                            "sus Municipios, manifiesto mediante la declaración escrita de mi representada, en la cual, hago constar que NO ACEPTO la aportación cinco al millar" + 
                            "del contrato que sea adjudicado (0.5%) antes de IVA para ser aportado al Fondo Impulso Jalisco.\n\n",regularFont));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d 'de' yyyy", new Locale("es", "ES"));
        String formatedDate = data.date.format(formatter);
        mainParagraph.add(new Phrase(formatedDate + "\n\n",regularFont));   
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

        document.close();
        writer.close();
    }
}
