package com.harojuan.Attachments;

import java.awt.Color;
import java.io.FileOutputStream;

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

public class ParticipantDeclaration extends PdfPageEventHelper {
    public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{  
        float fontSize = 9;
        BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
        BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font boldFont =  new Font(bfBoldTableFont,fontSize,Font.NORMAL);

        Document document = new Document(PageSize.A4, 36, 36, 0, 36);

        if(!fileName.endsWith(".pdf")){
            fileName += ".pdf";
        }

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
        writer.setPageEvent(new TechnicalAttachement());
        document.open();
        float pageWidth = document.getPageSize().getWidth();

        Paragraph mainParagraph = new Paragraph();

        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase("PODER EJECUTIVO DEL ESTADO DE JALISCO\n",regularFont));
        mainParagraph.add(new Phrase(data.number + "\n",regularFont));
        mainParagraph.add(new Phrase(data.type + "\n",regularFont));
        mainParagraph.add(new Phrase(data.name + "\n",regularFont));
        mainParagraph.add(new Phrase(data.committee + "\n\n",regularFont));
        mainParagraph.add(new Phrase("ANEXO 6 - DECLARACIONES DEL PARTICIPANTE\n\n",regularFont));
        document.add(mainParagraph);

        float[] colSize = {100};
        PdfPTable table = null;
        Paragraph tableJump = new Paragraph("\n",regularFont);
        tableJump.setLeading(5);
        
        
        //table set up
        table = new PdfPTable(colSize);
        table.getDefaultCell().setBorder(15);
        table.getDefaultCell().setBorderWidth(.1F);
        table.getDefaultCell().setBorderColor(Color.BLACK);
        table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(pageWidth - 60);
        table.setLockedWidth(true);
        
        table.getDefaultCell().setBackgroundColor(new Color(199, 199, 198));
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(new Phrase("Declaración de aceptación de bases.",boldFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(new Phrase("Por medio de la presente, acepto que he leído todo el contenido de las presentes bases de licitación. " + //
                                "Entiendo y acepto cada uno de los puntos establecidos en el" + //
                                "documento. Declaro que toda la información que proporcionó es verídica"+ //
                                " y acepto que de mentir o falsear información asumiré lo que la ley determine.",regularFont));
        document.add(table);
        document.add(tableJump);

        
        table = new PdfPTable(colSize);
        table.getDefaultCell().setBorder(15);
        table.getDefaultCell().setBorderWidth(.1F);
        table.getDefaultCell().setBorderColor(Color.BLACK);
        table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(pageWidth - 60);
        table.setLockedWidth(true);
        
        table.getDefaultCell().setBackgroundColor(new Color(199, 199, 198));
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(new Phrase("Declaración de tiempos, calidad y precio fijo.",boldFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(new Phrase("Manifiesto que en caso de ser adjudicado de entregar los servicios solicitados:"+//
                                " me comprometo a cumplir de acuerdo con las necesidades y tiempos de la " + //
                        "Dependencia solicitante de los mismos. Así como de mantener el precio de los bienes"+ //
                        " ofertados y en caso de resultar adjudicado cubrir cualquier eventualidad. Y " + //
                        "de la calidad de los servicios ofertados contra vicios ocultos del bien o servicio"+//
                        " que ofrecen, y me comprometo a responder por ellos.",regularFont));
        document.add(table);
        document.add(tableJump);

        table = new PdfPTable(colSize);
        table.getDefaultCell().setBorder(15);
        table.getDefaultCell().setBorderWidth(.1F);
        table.getDefaultCell().setBorderColor(Color.BLACK);
        table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(pageWidth - 60);
        table.setLockedWidth(true);
        
        table.getDefaultCell().setBackgroundColor(new Color(199, 199, 198));
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(new Phrase("Declaración de no colusión.",boldFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(new Phrase("De conformidad con los artículos 59 punto 1 fracción IX de la Ley de Compras Gubernamentales,"+//
                                " Enajenaciones y Contratación de Servicios del Estado de Jalisco " + //
                                "y sus Municipios, 183 y 187 de su Reglamento; me presentó a realizar"+//
                                " la Declaración de Integridad y No Colusión, por tal motivo, manifiesto BAJO PROTESTA DE " + //
                                "DECIR VERDAD, que me comprometo de abstenerse de adoptar conductas que induzcan a los servidores" + //
                                "públicos a alterar las evaluaciones de las proposiciones " + //
                                "técnicas y económicas en el presente proceso de licitación, a alterar el resultado del procedimiento" + //
                                " de contratación pública de que se trate, u otros aspectos que " + //
                                "otorguen condiciones más ventajosas, en igualdad de circunstancias, a determinada persona en relación con"+//
                                " los demás participantes en el procedimiento de mérito o a incitar la celebración de acuerdos colusorios\n" + 

                                "Asimismo, me comprometo a no incitar a los servidores públicos a que incumplan con los principios rectores y " + //
                                "valores que rigen su actuación en el ejercicio de su empleo, cargo o comisión, tales como disciplina, legalidad,"+ //
                                " objetividad, profesionalismo, honradez, lealtad, imparcialidad, integridad, rendición de cuentas, " + //
                                "eficacia, eficiencia, equidad, transparencia, economía, competencia por mérito, confidencialidad, respeto a la" +//
                                " dignidad humana y a la equidad de género, compromiso, cooperación, honestidad, liderazgo, rendición de cuentas," + //
                                " responsabilidad, solidaridad, tolerancia, vocación de servicio e igualdad de trato y oportunidades, inclusión y no"+ //
                                " discriminación, previstos en el artículo 5 de la Ley General del Sistema Nacional Anticorrupción.",regularFont));
        document.add(table);
        document.add(tableJump);

        table = new PdfPTable(colSize);
        table.getDefaultCell().setBorder(15);
        table.getDefaultCell().setBorderWidth(.1F);
        table.getDefaultCell().setBorderColor(Color.BLACK);
        table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(pageWidth - 60);
        table.setLockedWidth(true);
        
        table.getDefaultCell().setBackgroundColor(new Color(199, 199, 198));
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(new Phrase("Declaración de no conflicto de interés particular de acuerdo al art. 52. de la Ley",boldFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table.addCell(new Phrase("En cumplimiento al artículo 49, fracción IX, de la Ley General de Responsabilidades Administrativas, manifiesto"+//
                                " bajo protesta de decir verdad que (el o la) que suscribe, así como los socios o accionistas que ejercen control" + //
                                " sobre la sociedad (en caso de que sea persona jurídica), no desempeñar empleo, cargo o comisión " + //
                                " en el servicio público y, en caso de desempeñarlo, no se actualiza ningún conflicto de interés con la"+//
                                " formalización del contrato en caso de resultar adjudicado. Además, manifiesto bajo protesta de decir"+ //
                                " verdad que no me encuentro en alguno de los supuestos establecidos en el artículo 52 de la Ley de Compras " + //
                                "Gubernamentales, Enajenaciones y Contratación de Servicios del Estado de Jalisco y sus Municipios.",regularFont));
        document.add(table);
        document.add(tableJump);

        table = new PdfPTable(colSize);
        table.getDefaultCell().setBorder(15);
        table.getDefaultCell().setBorderWidth(.1F);
        table.getDefaultCell().setBorderColor(Color.BLACK);
        table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(pageWidth - 60);
        table.setLockedWidth(true);
        
        table.getDefaultCell().setBackgroundColor(new Color(199, 199, 198));
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(new Phrase("Declaración Constancia de proveedores.",boldFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
        Paragraph multiFontParagraph = new Paragraph();
        multiFontParagraph.add(new Phrase("Por medio de la presente declaración, expresó que actualmente",regularFont));
        multiFontParagraph.add(new Phrase(" Si ",boldFont));
        multiFontParagraph.add(new Phrase("Me encuentro registrado ante el padrón de proveedores. En caso de no contar con el registro" + //
        "activo, acepto que tengo 5 días a partir del fallo para registrarme\n",regularFont));
        multiFontParagraph.add(new Phrase("En caso de contar con registro, favor de proporcionar el número: ",regularFont));
        multiFontParagraph.add(new Phrase("P30289",boldFont));

        table.addCell(multiFontParagraph);
        document.add(table);
        document.add(tableJump);

        table = new PdfPTable(colSize);
        table.getDefaultCell().setBorder(15);
        table.getDefaultCell().setBorderWidth(.1F);
        table.getDefaultCell().setBorderColor(Color.BLACK);
        table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(pageWidth - 60);
        table.setLockedWidth(true);
        
        table.getDefaultCell().setBackgroundColor(new Color(199, 199, 198));
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(new Phrase("Declaración de Veracidad de Documental y No Edición de Anexos.",boldFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
        table.addCell(new Phrase("Declaro bajo protesta de decir verdad que la totalidad de los documentos presentados en el marco de la Ley,"+//
                                " incluyendo propuestas técnicas, documentos públicos y legales, certificaciones, contratos y cualquier otra "+//
                                "documentación requerida, son auténticos, veraces, y reflejan fielmente la información original " + //
                                "emitida por las autoridades o entidades correspondientes. Asimismo, manifiesto que ninguno de los documentos "+
                                "ni de los anexos presentados ha sido alterado, editado o modificado en su contenido\n"+//
                                "Me comprometo a asumir la responsabilidad legal que pudiera derivarse de cualquier falsedad o inexactitud en "+//
                                "los documentos proporcionados, en términos de la legislación aplicable",regularFont));
        document.add(table);
        document.add(tableJump);

        document.add(new Phrase("\nNombre o Denominación del Participante: DDV SOLUCIONES S.A DE C.V.\n\n",regularFont));
        
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
