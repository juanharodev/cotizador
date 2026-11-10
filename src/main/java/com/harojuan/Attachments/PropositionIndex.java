package com.harojuan.Attachments;

import java.awt.Color;
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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class PropositionIndex extends PdfPageEventHelper {
    public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName, int totalPages){
        try{
            float fontSize = 9;
            float smallFontSize = 9;
            BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
            Font smallRegularFont = new Font(bRegularFont,smallFontSize,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            Font boldFont =  new Font(bfBoldTableFont,smallFontSize,Font.NORMAL);
            Font redFont = new Font(bfBoldTableFont, fontSize, Font.NORMAL, Color.RED);

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
            mainParagraph.add(new Phrase("ANEXO 3 -  ÍNDICE DE LA PROPOSICIÓN\n\n\n",regularFont));
            document.add(mainParagraph);

      
            float[] titleCol = {100};
            PdfPTable titleTable = new PdfPTable(titleCol);
            titleTable.getDefaultCell().setBackgroundColor(new Color(199,199,198));
            titleTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            titleTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            titleTable.setHorizontalAlignment(0);
            titleTable.setTotalWidth(pageWidth - 60);
            titleTable.setLockedWidth(true);
            titleTable.getDefaultCell().setBorder(15);
            titleTable.getDefaultCell().setBorderWidth(1);
            titleTable.addCell(new Phrase("PROPUESTA",boldFont));
            document.add(titleTable);

            //table set up
            float[] colSize = {10,60,15,15};
            PdfPTable table = null;

            table = new PdfPTable(colSize);
            table.getDefaultCell().setBorder(15);
            table.getDefaultCell().setBorderWidth(1);
            table.getDefaultCell().setBorderColor(Color.BLACK);
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(pageWidth - 60);
            table.setLockedWidth(true);
            table.getDefaultCell().setBackgroundColor(Color.WHITE);

            table.addCell(new Phrase("NÚMERO",smallRegularFont));
            table.addCell(new Phrase("NOMBRE DEL DOCUMENTO ",smallRegularFont));
            table.addCell(new Phrase("INTEGRAR A PROPOSICIÓN IMPRESA",smallRegularFont));
            table.addCell(new Phrase("FORMATO DIGITAL",smallRegularFont));

            table.addCell(new Phrase("1",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("Propuesta Técnica",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("2",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("ANEXO 3 - Índice de la Proposición",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("3",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("ANEXO 4 - Acreditación del Licitante",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("4",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("ANEXO 5 - Propuesta Económica",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("SI (EXCEL)",smallRegularFont));

            table.addCell(new Phrase("5",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("ANEXO 6 - Declaraciones del Licitante",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("6",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("ANEXO 7 - Aportación 5 al Millar",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("7",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("ANEXO 8 - Declaración de Estratificación",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("8",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("ANEXO 9 - Listado de miembros",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("9",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("ANEXO 10 - Manifestación de Cumplimiento de Obligaciones Fiscales.",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("10",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("ANEXO 11 - Manifiesto de cumplimiento obligaciones en materia de Seguridad Social",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("11",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("ANEXO 12 - Formato de Muestras Físicas (Cuando aplique)",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("2",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("Constancia de Visita de Campo (Cuando aplique).",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("NO",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));
            document.add(table);
            
            titleTable = new PdfPTable(titleCol);
            titleTable.getDefaultCell().setBackgroundColor(new Color(199,199,198));
            titleTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            titleTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            titleTable.setHorizontalAlignment(0);
            titleTable.setTotalWidth(pageWidth - 60);
            titleTable.setLockedWidth(true);
            titleTable.getDefaultCell().setBorder(15);
            titleTable.getDefaultCell().setBorderWidth(1);
            titleTable.addCell(new Phrase("DOCUMENTOS PÚBLICOS",boldFont));
            
            document.add(titleTable);

            table = new PdfPTable(colSize);
            table.getDefaultCell().setBorder(15);
            table.getDefaultCell().setBorderWidth(1);
            table.getDefaultCell().setBorderColor(Color.BLACK);
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(pageWidth - 60);
            table.setLockedWidth(true);
            table.getDefaultCell().setBackgroundColor(Color.WHITE);

            table.addCell(new Phrase("13",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("Opinión del cumplimiento de obligaciones fiscales en materia de seguridad social (Ante el IMSS)",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("14",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("Acuse de autorización al IMSS para hacer público el resultado de la consulta de su opinión del cumplimiento de obligaciones fiscales en materia de seguridad social.",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("15",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("Opinión del cumplimiento de obligaciones fiscales (Ante el SAT)",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("16",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("Constancia de Situación Fiscal (SAT)",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("17",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("Constancia de Situación Fiscal en Materia de Aportaciones Patronales y Entero de Descuentos ante INFONAVIT ",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));

            table.addCell(new Phrase("18",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            table.addCell(new Phrase("Copia de INE.",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(new Phrase("SI",smallRegularFont));
            table.addCell(new Phrase("NO",smallRegularFont));
            document.add(table);

            document.add(new Paragraph("\n"));

            titleTable = new PdfPTable(titleCol);
            titleTable.getDefaultCell().setBackgroundColor(Color.WHITE);
            titleTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            titleTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_TOP);
            titleTable.setHorizontalAlignment(0);
            titleTable.setTotalWidth(pageWidth - 60);
            titleTable.setLockedWidth(true);
            titleTable.getDefaultCell().setBorder(15);
            titleTable.getDefaultCell().setBorderWidth(1);
            titleTable.addCell(new Phrase("Declaro que acepto íntegramente las bases y me comprometo a cumplir con sus disposiciones. Reconozco que el incumplimiento de las mismas podrá acarrear las" + 
                                "sanciones correspondientes, incluyendo descalificación o exclusión del proceso. Asimismo, eximo a la Dirección de cualquier responsabilidad sobre la veracidad de" + 
                                "la información proporcionada por los proveedores", smallRegularFont));

            document.add(titleTable);

            mainParagraph = new Paragraph();
            mainParagraph.add(new Phrase("Total de páginas de la proposición: ",smallRegularFont));
            mainParagraph.add(new Phrase(totalPages + "\n",redFont));
            document.add(mainParagraph);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d 'de' yyyy", new Locale("es", "ES"));
            String formatedDate = data.date.format(formatter);
            document.add(new Phrase("Fecha: " + formatedDate,smallRegularFont));

            mainParagraph = new Paragraph();
            mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            mainParagraph.setLeading(smallFontSize + 1);

            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
            mainParagraph.add(new Phrase("ATENTAMENTE\n\n\n",smallRegularFont));
            table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            mainParagraph.add(new Phrase("__________________________________\n",smallRegularFont));
            mainParagraph.add(new Phrase(data.responsible + "\n",smallRegularFont));
            mainParagraph.add(new Phrase("Representante Legal\n",smallRegularFont));
            mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.",smallRegularFont));
            document.add(mainParagraph);

            document.close();
            writer.close();

        }
        catch(Exception e){

        }
    }
}
