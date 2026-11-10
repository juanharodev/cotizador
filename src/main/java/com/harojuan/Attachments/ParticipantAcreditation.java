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

public class ParticipantAcreditation extends PdfPageEventHelper {
        public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{  
            float fontSize = 9;
            BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
            BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            Font boldFont =  new Font(bfBoldTableFont,fontSize,Font.NORMAL);
            Font underlineBold =  new Font(bfBoldTableFont,fontSize,Font.UNDERLINE);

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
            mainParagraph.add(new Phrase("ANEXO 4 - ACREDITACIÓN DEL PARTICIPANTE\n\n",regularFont));
            document.add(mainParagraph);

            mainParagraph = new Paragraph();
            mainParagraph.add(new Phrase("Yo ",regularFont));
            mainParagraph.add(new Phrase(data.responsible,boldFont));
            mainParagraph.add(new Phrase(", manifiesto ",regularFont));
            mainParagraph.add(new Phrase("BAJO PROTESTA DE DECIR VERDAD",boldFont));
            mainParagraph.add(new Phrase(", que los datos aquí asentados son ciertos, así como que cuento con" + //
                                "facultades suficientes para suscribir la proposición de la presente Licitación, " + //
                                "a nombre y representación de quien firma el presente documento, por lo" + //
                                "que en caso de falsear información, datos o documentos a lo largo de la " + //
                                "proposición acepto que se apliquen las medidas disciplinarias tanto a " + //
                                "mí como a mí representada, en los términos de la ley de la materia, incluyendo" + //
                                " el desechamiento de la proposición en la presente licitación y que la sancione" + //
                                "a mi representada de acuerdo a los artículos 107 del reglamento y 110, 116, 117 y 118" + //
                                " de la Ley de Compras Gubernamentales, Enajenaciones y" + //
                                "Contratación de Servicios del Estado de Jalisco y sus Municipios.\n\n",regularFont));
            document.add(mainParagraph);
            
            float[] colSize = {40,60};
            PdfPTable table = new PdfPTable(colSize);
            table.setTotalWidth(pageWidth - 60);
            table.setLockedWidth(true);
            
            PdfPCell titleCell = new PdfPCell();
            titleCell.setColspan(colSize.length);
            titleCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            titleCell.setBackgroundColor(new Color(199,199,198));
            titleCell.setFixedHeight(25);
            Paragraph titleParagraph = new Paragraph("DATOS DEL PARTICIPANTE",boldFont);
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            titleCell.addElement(titleParagraph);
            table.addCell(titleCell);

            table.addCell(new Phrase("Nombre del Participante:",regularFont));
            table.addCell(new Phrase(" DDV Soluciones S.A. de C.V.",regularFont));

            
            table.addCell(new Phrase("Domicilio: (Calle, Número exterior - interior, Colonia, Municipio, Entidad Federativa, Código Postal)",regularFont));
            table.addCell(new Phrase("Belisario Domínguez 1196 Interior 10, Lomas de Independencia, Guadalajara, Jalisco, 44370",regularFont));

            
            table.addCell(new Phrase("RFC:",regularFont));
            table.addCell(new Phrase(" DSO181213C61",regularFont));

            
            table.addCell(new Phrase("Teléfono (s):",regularFont));
            table.addCell(new Phrase(" 3319166203",regularFont));

            
            table.addCell(new Phrase("Correo Electrónico:",regularFont));
            table.addCell(new Phrase(" ddvsoluciones2019@gmail.com",regularFont));

            titleCell = new PdfPCell();
            titleCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            titleCell.setBackgroundColor(new Color(199,199,198));
            titleCell.setFixedHeight(25);
            titleCell.setColspan(colSize.length);
            titleParagraph = new Paragraph("Requisitarse este recuadro únicamente por Personas Jurídicas:",underlineBold);
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            titleCell.addElement(titleParagraph);
            table.addCell(titleCell);

            table.addCell(new Phrase("Número de Escritura Pública: 75,348\nReformas y modificaciones: 52,458",regularFont));
            table.addCell(new Phrase("Notario No. 7 de la Subregión Centro Conurbada, con Adscripción en el Municipio de Tlaquepaque.",regularFont));
            
            table.addCell(new Phrase("Folio Mercantil: 968274",regularFont));
            
            PdfPCell multiRowCell = new PdfPCell();
            multiRowCell.setRowspan(2);
            multiRowCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            multiRowCell.addElement(new Phrase("En caso de contar con registro de proveedores Vigente, indicar número de proveedor y con" + //
            "esto se verifica Fecha y lugar de expedición: la creditación de la empresa: P30289",regularFont));
            table.addCell(multiRowCell);
            
            table.addCell(new Phrase("Fecha y lugar de expedición: 27/11/2018 Guadalajara\n" + //
                                "Reformas y modificaciones: 07/06/2019 Guadalajara",regularFont));

            titleCell = new PdfPCell();
            titleCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            titleCell.setBackgroundColor(new Color(199,199,198));
            titleCell.setFixedHeight(25);
            titleCell.setColspan(colSize.length);
            titleParagraph = new Paragraph("Requisitarse este recuadro únicamente por Personas Físicas:",underlineBold);
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            titleCell.addElement(titleParagraph);
            table.addCell(titleCell);

            table.addCell(new Phrase("Tipo de Identificación Oficial Vigente:",regularFont));
            table.addCell(new Phrase(" ",regularFont));

            table.addCell(new Phrase("Número de registro de Identificación Oficial Vigente:",regularFont));
            table.addCell(new Phrase(" ",regularFont));
            
            titleCell = new PdfPCell();
            titleCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            titleCell.setBackgroundColor(new Color(199,199,198));
            titleCell.setFixedHeight(25);
            titleCell.setColspan(colSize.length);
            titleParagraph = new Paragraph("Para Personas Físicas o Jurídicas que comparezcan a través del Representante",underlineBold);
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            titleCell.addElement(titleParagraph);
            table.addCell(titleCell);

            document.add(table);

            float[] colSize2 = {20,30,50};
            table = new PdfPTable(colSize2);
            table.setTotalWidth(pageWidth - 60);
            table.setLockedWidth(true);

            multiRowCell = new PdfPCell();
            multiRowCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            multiRowCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            multiRowCell.setRowspan(4);
            titleParagraph = new Paragraph("REPRESENTACIÓN",boldFont);
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            multiRowCell.addElement(titleParagraph);
            table.addCell(multiRowCell);

            table.addCell(new Phrase("Número de Escritura Pública:",regularFont));
            table.addCell(new Phrase("75,348",regularFont));

            table.addCell(new Phrase("Tipo de representación:",regularFont));
            table.addCell(new Phrase("Administrador General Único",regularFont));

            table.addCell(new Phrase("Nombre del Fedatario Público:",regularFont));
            table.addCell(new Phrase("Salvador Guillermo Plaza Arana",regularFont));
            
            table.addCell(new Phrase("Lugar y fecha de expedición:",regularFont));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("' a los' d 'días del mes de ' MMMM", new Locale("es", "ES"));
            String formatedDate = data.date.format(formatter);
            table.addCell(new Phrase("Guadalajara, Jalisco" + formatedDate,regularFont));
            document.add(table);

            document.add(new Phrase("\n\nNombre del participante: DDV SOLUCIONES S.A DE C.V.",regularFont));
            formatter = DateTimeFormatter.ofPattern("MMMM d 'de' yyyy", new Locale("es", "ES"));
            formatedDate = data.date.format(formatter);
            document.add(new Phrase("\n\nFecha: " + formatedDate,regularFont));
            document.add(new Phrase("\n\n\nFirma:",regularFont));
            document.add(new Phrase("\nNombre del representante: " + data.responsible,regularFont));

            document.close();
            writer.close();
        }

}
