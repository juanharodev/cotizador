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

public class PersonalityManifesto extends PdfPageEventHelper{
    public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName){
        try {
            float fontSize = 11;
            float smallFontSize = 11;
            BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
            Font smallRegularFont = new Font(bRegularFont,smallFontSize,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            Font italic =  new Font(bfBoldTableFont,smallFontSize,Font.ITALIC);

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
            mainParagraph.add(new Phrase("ANEXO 2 - MANIFIESTO DE PERSONALIDAD\n\n\n",regularFont));
            document.add(mainParagraph);
            
            
            mainParagraph = new Paragraph();
            mainParagraph.setLeading(fontSize + 1);
            mainParagraph.add(new Phrase("Número de Licitación: " + data.number + "\n\n",regularFont));
            mainParagraph.add(new Phrase("Título de la Licitación: " + data.name + "\n\n",regularFont));
            mainParagraph.add(new Phrase("Nombre o denominación del participante: DDV SOLUCIONES S.A. DE C.V.\n\n ",regularFont));
            mainParagraph.add(new Phrase("Nombre del representante o apoderado (si aplica): " + data.responsible + "\n\n\n\n ",regularFont));
            mainParagraph.add(new Phrase("Declaro bajo protesta de decir verdad que cuento con las facultades suficientes para comprometerme en el acto de presentación"+
                                        "y apertura de proposiciones de la presente licitación Pública.\n\n\n",regularFont));
            document.add(mainParagraph);

            mainParagraph = new Paragraph();
            mainParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d 'de' yyyy", new Locale("es", "ES"));
            String formatedDate = data.date.format(formatter);
            mainParagraph.add(new Phrase(formatedDate + "\n\n\n",regularFont));
            document.add(mainParagraph);

            mainParagraph = new Paragraph();
            mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            mainParagraph.setLeading(smallFontSize + 1);


            mainParagraph.add(new Phrase("ATENTAMENTE\n\n\n",smallRegularFont));
            mainParagraph.add(new Phrase("__________________________________\n",smallRegularFont));
            mainParagraph.add(new Phrase(data.responsible + "\n",smallRegularFont));
            mainParagraph.add(new Phrase("Representante Legal\n",smallRegularFont));
            mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.\n",smallRegularFont));
            document.add(mainParagraph);

            mainParagraph = new Paragraph();

            mainParagraph.setLeading(smallFontSize+1);
            mainParagraph.setIndentationLeft(20);
            mainParagraph.add(new Phrase("\n\n\n\n\n1 Este documento no hace las veces de poder.\n",italic));
            mainParagraph.add(new Phrase("2 La presentación de este documento es de carácter obligatorio, sin él no se podrá participar ni entregar propuesta alguna ante la Unidad\n" + //
                                "Centralizada de Compras, de conformidad con lo dispuesto en el artículo 59 numeral 1 fracciones VI y VIII de la Ley de Compras Gubernamentales,\n" + //
                                "Enajenaciones y Contrataciones de Servicios del Estado de Jalisco y sus Municipios.\n",italic));
            document.add(mainParagraph);

            document.close();
            writer.close();

        } catch (Exception e) {
            
        }
    }
}