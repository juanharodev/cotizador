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

public class PowerOfAttorney extends PdfPageEventHelper{
    public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{  
        float fontSize = 9;
        BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
        Font italicFont = new Font(bRegularFont,fontSize,Font.ITALIC);
        BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);

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
        mainParagraph.add(new Phrase("ANEXO 12 - PODER\n\n",regularFont));
        document.add(mainParagraph);

        document.add(new Phrase(data.receiver+"\n\n",regularFont));

        mainParagraph = new Paragraph();
        mainParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d 'de' yyyy", new Locale("es", "ES"));
        String formatedDate = data.date.format(formatter);
        mainParagraph.add(new Phrase("Fecha: "  + formatedDate + "\n",regularFont));
        mainParagraph.add(new Phrase("Lugar: "  + data.place,regularFont));
        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.add(new Phrase("Poderdante (Quien concede el poder): Carlos Vidarte Rodríguez\n",regularFont));
        mainParagraph.add(new Phrase("Apoderado (quien recibe el poder): Violeta Vidarte Rodríguez\n\n",regularFont));
        
        mainParagraph.add(new Phrase("El suscrito poderdante, bajo protesta de decir verdad, que cuenta con facultades suficientes para " + //
            "comprometerse por sí o por su representada, sin que resulte necesario acreditar su personalidad jurídica; manifiesta mexicano " +//
            "por nacimiento, persona física, con plena capacidad para contratar, suscribo esta carta poder que se otorga en los términos de " + //
            "lo dispuesto por los artículos 2551 y 2554 del Código Civil Federal, así mismo, en favor de la siguiente persona, denominada " + //
            " como apoderado.\n\n",regularFont));
        
        mainParagraph.add(new Phrase("El presente poder se otorga de manera amplia y bastante, a fin de que la persona referida previamente, " + //
            "comparezcan de manera individual o conjunta, ante esta honorable autoridad, para que cumplan con el siguiente objeto:\n\n",regularFont));
        
        mainParagraph.add(new Phrase("Acudir ante la CONVOCANTE para presentar la propuesta de Licitación Pública en nombre y " + //
            "representación del poderdante.\n\n",italicFont));
        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.setLeading(10);
        
        mainParagraph.add(new Phrase("ATENTAMENTE\n\n\n\n",regularFont));
        mainParagraph.add(new Phrase("__________________________________\n",regularFont));
        mainParagraph.add(new Phrase("Carlos Vidarte Rodríguez\r\n",regularFont));
        mainParagraph.add(new Phrase("Representante Legal\n",regularFont));
        mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.\n\n\n\n",regularFont));
                
        mainParagraph.add(new Phrase("APODERADO\n\n\n\n",regularFont));
        mainParagraph.add(new Phrase("__________________________________\n",regularFont));
        mainParagraph.add(new Phrase("Violeta Vidarte Rodríguez\n",regularFont));
        mainParagraph.add(new Phrase("Ventas Gobierno",regularFont));
        document.add(mainParagraph);


        document.close();
        writer.close();
    }
}
