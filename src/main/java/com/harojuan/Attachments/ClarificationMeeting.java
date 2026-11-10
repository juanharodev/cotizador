package com.harojuan.Attachments;

import java.awt.Color;
import java.io.FileOutputStream;

import com.harojuan.QuotationModel.QuotationTable;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class ClarificationMeeting extends PdfPageEventHelper{
        public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{  
        float fontSize = 9;
        BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
        BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        Font boldFont = new Font(bfBoldTableFont,fontSize, Font.NORMAL);
        Font italicBoldFont =  new Font(bfBoldTableFont,fontSize,Font.ITALIC);

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
        mainParagraph.add(new Phrase("ANEXO 13 - FORMATO DE PREGUNTA PARA JUNTA ACLARATORIA\n\n\n\n",regularFont));
        document.add(mainParagraph);

        float[] colSize = {40,60};
        PdfPTable table = null;

        //table set up
        table = new PdfPTable(colSize);
        table.getDefaultCell().setBorder(15);
        table.getDefaultCell().setBorderWidth(1);
        table.getDefaultCell().setBorderColor(Color.BLACK);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_TOP);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(pageWidth - 60);
        table.setLockedWidth(true);

        addCells(regularFont, table,"1. Nombre del participante: DDV Soluciones S.A. de C.V.","DDV Soluciones S.A. de C.V.");
        addCells(regularFont, table,"2. Nombre del representante legal (en caso): ","Carlos Vidarte Rodríguez");
        addCells(regularFont, table,"3. Número de Licitación Pública: ", data.number + " "  + data.name);
        document.add(table);

        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase("\nBAJO PROTESTA DE DECIR VERDAD DECLARO QUE ES MI INTERÉS PARTICIPAR DEL PROCESO DE LICITACIÓN PÚBLICA EXPRESADO EN EL PUNTO " + //
                        "3. DEL PRESENTE ANEXO.\n\n\n",italicBoldFont));
        document.add(mainParagraph);
        document.add(new Phrase("Pregunta\n\n",regularFont));
        
        mainParagraph = new Paragraph();
        Paragraph question = new Paragraph();
        question.add(new Phrase("En relación con lo establecido en las bases de la licitación, específicamente en la página ",regularFont));
        question.add(new Phrase("63, ",boldFont));
        question.add(new Phrase("punto ",regularFont));
        question.add(new Phrase("6. Especificaciones del Contrato, ",boldFont));
        question.add(new Phrase("apartado ",regularFont));
        question.add(new Phrase("Forma de entrega ",boldFont));
        question.add(new Phrase("se indica que los artículos deberán entregarse el día 15 de agosto del año en curso. Dado que dicha fecha ya ha transcurrido, " + //
            "solicitamos amablemente a la convocante nos aclare cuál será el tiempo real de entrega que deberá considerarse para el cumplimiento del " + //
            "contrato.  ",regularFont));
        List orderList = new List(List.ORDERED);
        ListItem item = new ListItem(question);
        item.setFont(regularFont);
        orderList.add(item);
        mainParagraph.add(orderList);
        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.setLeading(10);
        
        mainParagraph.add(new Phrase("\n\nATENTAMENTE\n\n\n\n",regularFont));
        mainParagraph.add(new Phrase("__________________________________\n",regularFont));
        mainParagraph.add(new Phrase(data.responsible + "\n",regularFont));
        mainParagraph.add(new Phrase("Representante Legal\n",regularFont));
        mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.\n",regularFont));

        document.add(mainParagraph);

        document.close();
        writer.close();
    }

        private void addCells(Font regularFont, PdfPTable table, String cell1Text, String cell2Text) {
            table.getDefaultCell().setBackgroundColor(Color.WHITE);
            table.addCell(new Phrase(cell1Text,regularFont));
            table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
            table.addCell(new Phrase(cell2Text,regularFont));
        }
}
