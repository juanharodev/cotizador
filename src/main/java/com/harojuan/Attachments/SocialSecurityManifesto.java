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

public class SocialSecurityManifesto extends PdfPageEventHelper {
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
        mainParagraph.add(new Phrase("ANEXO 11 - MANIFIESTO DE CUMPLIMIENTO OBLIGACIONES EN MATERIA DE SEGURIDAD SOCIAL\n\n",regularFont));
        document.add(mainParagraph);
        
        mainParagraph = new Paragraph();
        mainParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d 'de' yyyy", new Locale("es", "ES"));
        String formatedDate = data.date.format(formatter);
        mainParagraph.add(new Phrase(data.place + " " + formatedDate,regularFont));
        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase(data.present + "\nPRESENTE.\n",boldFont));
        document.add(mainParagraph);
        
        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
        mainParagraph.add(new Phrase("AT'N: " + data.receiver + "\n\n",boldFont));
        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase("CARLOS VIDARTE RODRÍGUEZ", boldFont));
        mainParagraph.add(new Paragraph(", en mi carácter de Representante Legal de la empresa DDV SOLUCIONES S.A. DE C.V., manifiesto estar al corriente " + //
            "de mis obligaciones fiscales en materia de Seguridad Social, por lo que anexo la Constancia de opinión positiva de cumplimiento de obligaciones en " + //
            "materia de Seguridad Social, documento vigente expedido el IMSS, de conformidad al ACUERDO número ACDO.AS2.HCT.270422/107.P.DIR dictado " + //
            "por el H. Consejo Técnico, en sesión ordinaria de 27 de abril del 2022\n\n", regularFont));
        mainParagraph.add(new Phrase("Asimismo, se informa que, se autorizó al IMSS a hacer público el resultado de la consulta de opinión del cumplimiento " + //
            "de obligaciones fiscales en materia de seguridad social de DDV SOLUCIONES S.A. DE C.V., de conformidad con la regla séptima del ACUERDO número " + //
            "ACDO.AS2.HCT.270422/107.P.DIR dictado por el H. Consejo Técnico, en sesión ordinaria de 27 de abril del 2022. Se adjunta al presente el acuse " + //
            "correspondiente. Dicha autorización se mantendrá activa hasta el momento que se emita resolución en el procedimiento de licitación citado a rubro, " + //
            "y en caso de resultar adjudicado hasta el término del contrato.\n\n", regularFont));
        mainParagraph.add(new Phrase("De igual manera, por virtud del presente se autoriza a la Secretaría de Administración del Gobierno del Estado de Jalisco, " + //
            "para que, a través de las áreas organizativas que resulten competentes, lleve a cabo la consulta en línea de la opinión del cumplimiento de las " + //
            "obligaciones fiscales en materia de seguridad social de mi representada.\n\n\n", regularFont));
        document.add(mainParagraph);


        mainParagraph = new Paragraph();
        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase("ATENTAMENTE\n\n\n",regularFont));
        mainParagraph.add(new Phrase("__________________________________\n",regularFont));
        mainParagraph.add(new Phrase(data.responsible + "\n",regularFont));
        mainParagraph.add(new Phrase("Representante Legal\n",regularFont));
        mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.\n",regularFont));
        document.add(mainParagraph);

        document.close();
        writer.close();
    }
}
