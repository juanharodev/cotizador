package com.harojuan.Attachments;

import java.io.FileOutputStream;

import com.harojuan.QuotationModel.QuotationTable;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class Stratification extends PdfPageEventHelper{
        public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{  
        float fontSize = 11;
        BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
        BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font boldFont =  new Font(bfBoldTableFont,fontSize,Font.NORMAL);
        Font italicFont =  new Font(bRegularFont,fontSize,Font.ITALIC);

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
        mainParagraph.add(new Phrase("ANEXO 8 - ESTRATIFICACIÓN\n\n",regularFont));
        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);

        mainParagraph.add(new Phrase("Me refiero al procedimiento de ",regularFont));
        mainParagraph.add(new Phrase(data.type + ", " + data.number + ", " + data.committee + //
                        ", " + data.name + " ",boldFont));
        mainParagraph.add(new Phrase("en el que mí representada, la empresa ",regularFont));
        mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V",boldFont));
        mainParagraph.add(new Phrase("., participa a través de la presente proposición.\n\n",regularFont));
        mainParagraph.add(new Phrase("Al respecto y de conformidad con lo dispuesto por el numeral 1 del artículo 68 de la Ley, ",regularFont));
        mainParagraph.add(new Phrase("MANIFIESTO BAJO PROTESTA DE DECIR VERDAD ",boldFont));
        mainParagraph.add(new Phrase("que mi representada está constituida conforme a las leyes mexicanas, con Registro Federal de Contribuyentes ",regularFont));
        mainParagraph.add(new Phrase("DSO18121C61",boldFont));
        mainParagraph.add(new Phrase(", y asimismo que considerando los criterios (sector, número total de trabajadores y ventas anuales) " + //
                        "establecidos en el Acuerdo por el que se establece la estratificación de las micro, pequeñas y medianas empresas, " + //
                        "publicado en el Diario Oficial de la Federación el 30 de junio de 2009, mi representada tiene un Tope Máximo Combinado de ",regularFont));
        mainParagraph.add(new Phrase("7.58",boldFont));
        mainParagraph.add(new Phrase(", con base en lo cual se estratifica como una empresa ",regularFont));
        mainParagraph.add(new Phrase("PEQUEÑA\n\n",boldFont));
        mainParagraph.add(new Phrase("De igual forma, declaro que la presente manifestación la hago teniendo pleno conocimiento de que la omisión, simulación o presentación de" + //
                        "información falsa, son infracciones previstas por los artículos 116, 117 y 118 de la Ley y los diversos numerales 155 al 161 de su Reglamento, así como" + //
                        "los artículos 69 y 81 de la Ley General de Responsabilidades Administrativas de aplicación supletoria.\n\n",regularFont));
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
        mainParagraph.setIndentationLeft(20);
        mainParagraph.add(new Phrase("Quien manifiesta bajo protesta de decir verdad, que a esta fecha el mandato con el que se ostenta no le ha sido modificado o revocado, y que\r\n" + //
                        "por tanto la cotización es cierta y actual.",italicFont));
        document.add(mainParagraph);

        document.close();
        writer.close();
    }
}
