package com.harojuan.Attachments;

import java.awt.Color;
import java.io.FileOutputStream;

import com.harojuan.QuotationModel.QuotationTable;
import com.lowagie.text.Anchor;
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

public class Members extends PdfPageEventHelper {
         public void Export(QuotationTable tableReference, AttachmentData data, String filePath, String fileName) throws Exception{  
        float fontSize = 9;
        BaseFont bRegularFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font regularFont = new Font(bRegularFont,fontSize,Font.NORMAL);
        BaseFont bfBoldTableFont = BaseFont.createFont("fonts/calibri-bold.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font boldFont =  new Font(bfBoldTableFont,fontSize,Font.NORMAL);
        Font italicFont =  new Font(bfBoldTableFont,fontSize,Font.ITALIC);

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
        mainParagraph.add(new Phrase("ANEXO 9 - LISTADO DE MIEMBROS Y REPRESENTANTES\n\n",regularFont));
        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase("Yo ",regularFont));
        mainParagraph.add(new Phrase("CARLOS VIDARTE RODRÍGUEZ",boldFont));
        mainParagraph.add(new Phrase(", manifiesto, ",regularFont));
        mainParagraph.add(new Phrase("BAJO PROTESTA DE DECIR VERDAD",boldFont));
        mainParagraph.add(new Phrase(", que los datos aquí asentados son ciertos y actuales a la fecha de la participación y han sido debidamente verificados, " + //
            "así como que ninguno de sus administradores, socios, accionistas, asociados, miembros o apoderados, se han desempeñado, colaborado o representado " + //
            "bajo cualquier modalidad, para alguna de las demás empresas, sociedades, asociaciones, agrupaciones, o proveedores en general, que participan" + //
            "dentro del procedimiento de adquisición de bienes y/o contratación de servicios que nos ocupa, en al menos los 02 dos últimos años a la fecha " + // 
            "de comenzar su participación en el mismo, por lo que de incurrir en falsedad., ",regularFont));
        mainParagraph.add(new Phrase("BAJO PROTESTA DE DECIR VERDAD\n\n",boldFont));

        document.add(mainParagraph);

        float[] colSize = {40,60};
        PdfPTable table = null;

        
        
        //table set up
        table = new PdfPTable(colSize);
        table.getDefaultCell().setBorder(15);
        table.getDefaultCell().setBorderWidth(.1F);
        table.getDefaultCell().setBorderColor(Color.BLACK);
        table.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_TOP);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(pageWidth - 60);
        table.setLockedWidth(true);

        //Title cell
        PdfPCell doubleCell = new PdfPCell();
        doubleCell.setColspan(2);
        doubleCell.setBackgroundColor(new Color(199,199,198));
        doubleCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        doubleCell.addElement(new Phrase("DATOS DE LA PERSONA FÍSICA", boldFont));
        table.addCell(doubleCell);

        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Nombre",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase(" ",regularFont));

        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Documento que acredita la actividad empresarial",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase(" ",regularFont));

        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Domicilio Fiscal:",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase("(Calle, Número exterior e interior, Colonia, Ciudad, Estado, Código Postal).",regularFont));

        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Ciudad y Entidad Federativa:",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase(" ",regularFont));

        //Title cell
        doubleCell = new PdfPCell();
        doubleCell.setColspan(2);
        doubleCell.setBackgroundColor(new Color(199,199,198));
        doubleCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        doubleCell.addElement(new Phrase("DATOS DE LA EMPRESA (PERSONA MORAL)", boldFont));
        table.addCell(doubleCell);

        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Nombre de la empresa:",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase("DDV SOLUCIONES S.A. DE C.V.",regularFont));

        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Registro Federal de Contribuyentes (RFC):",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase("DSO181213C61",regularFont));

        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Domicilio Fiscal:",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase("BELISARIO DOMÍNGUEZ, 1196 interior 10, LOMAS DE INDEPENDENCIA, GUADALAJARA, JALISCO, 44370.",regularFont));

        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Ciudad y Entidad Federativa:",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase("GUADALAJARA, JALISCO",regularFont));

        
        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Número de Escritura Pública y folio mercantil:",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase("ECRITURA: 75,348 SETENTA Y CINCO MIL TRECIENTOS CUARENTA Y OCHO\n" + //
                        "FOLIO: 968,274 NOVECIENTOS SESENTA Y OCHO MIL DOSCIENTOS SETENTA Y CUATRO",regularFont));

        
        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Fecha y lugar de expedición:",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase("GUADALAJARA, JALISCO, 27 DE NOVIEMBRE DEL 2024",regularFont));

        
        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Nombre del Fedatario Público:",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase("SALVADOR GUILLERMO PLAZA ARANA",regularFont));

        //Title cell
        doubleCell = new PdfPCell();
        doubleCell.setColspan(2);
        doubleCell.setBackgroundColor(new Color(199,199,198));
        doubleCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        doubleCell.addElement(new Phrase("LISTADO DE MIEMBROS Y REPRESENTANTES ACTUALES", boldFont));
        table.addCell(doubleCell);

        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Administrador(es) de la empresa (1)(2)(3):",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase("CARLOS VIDARTE RODRÍGUEZ",regularFont));

        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        table.addCell(new Phrase("Representante(s) legal(es) de la empresa (1)(2)(3):",regularFont));
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase("CARLOS VIDARTE RODRÍGUEZ",regularFont));

        //Row
        table.getDefaultCell().setBackgroundColor(new Color(199,199,198));
        mainParagraph = new Paragraph();
        mainParagraph.add(new Phrase("Apoderado(s) legal(es) de la empresa (1)(2)(3):\n\n" + //
                        "Socios y accionistas (1)(2)(3):\n",boldFont));
        mainParagraph.add(new Phrase("A efecto de cumplir con los principios constitucionales de" + //
                        "máxima publicidad y rendición de cuentas, para el caso que los " + //
                        "socios o accionistas de la empresa participante, sean a su vez, " + //
                        "personas morales, también se deberá señalar quiénes son los" + //
                        "socios y accionistas de esa sociedad, y así indefinidamente", regularFont));
        table.addCell(mainParagraph);
        table.getDefaultCell().setBackgroundColor(Color.WHITE);
        table.addCell(new Phrase("CARLOS VIDARTE RODRÍGUEZ\n\n\n" + //
                        "CARLOS VIDARTE RODRÍGUEZ (50%)\n" + //
                        "VIOLETA VIDARTE RODRIGUEZ (50%)",regularFont));
        document.add(table);

        document.add(new Phrase("\nEl Gobierno Del Estado De Jalisco, a través de la Dirección de Abastecimiento, con domicilio en la calle Av. Fray Antonio Alcalde 1221, Miraflores,\r\n" + //
                        "44266, de la ciudad de Guadalajara, se informa lo siguiente:\n\n" + 
                        "El tratamiento de sus datos personales se realiza con fundamento en lo establecido en:\n\n",regularFont));
        List bulletList = new List(List.UNORDERED);
        bulletList.setListSymbol("• ");
        bulletList.add(new ListItem("Constitución Política de los Estados Unidos Mexicanos: artículos 6 apartado A fracción I , 108 y 109 fracción III y 134;",regularFont));
        bulletList.add(new ListItem("Ley de Fiscalización y Rendición de Cuentas de la Federación: artículos 17 fracción XVI segundo párrafo, 24, 40 fracción V, 67 fracción II " + //
                        "primer párrafo y 73;",regularFont));
        bulletList.add(new ListItem("Constitución Política del Estado de Jalisco: artículos 35 Bis, fracción I, último párrafo, 91, fracción II, 106 fracción IV y 107;",regularFont));
        bulletList.add(new ListItem("Ley General de Responsabilidades Administrativas: artículos 58 y 59",regularFont));
        bulletList.add(new ListItem("Ley de Responsabilidades Políticas y Administrativas del Estado de Jalisco: artículos 1, 3 fracción IX, 46 fracción II, 51, 52, 53 fracción IV;",regularFont));
        bulletList.add(new ListItem("Ley de Compras Gubernamentales, Enajenaciones y Contratación de Servicios del Estado de Jalisco y sus Municipios: artículos 37 " + //
                        "párrafos segundo y tercero, 38, 90, 107 primer párrafo, 108, 110, 116, 119 primer párrafo, 120, 121 y 123;",regularFont));
        bulletList.add(new ListItem("Ley del Gobierno y la Administración Pública Municipal el Estado de Jalisco: artículos 67 quinquies, 67 sexies;",regularFont));
        bulletList.add(new ListItem("Código de Gobierno Del Estado De Jalisco: artículos 205, 208 Bis, 210;",regularFont));
        bulletList.add(new ListItem("Ley General de Transparencia: artículos 1 primer párrafo, 116, 120, fracción IV,",regularFont));
        bulletList.add(new ListItem("Adhesión del Estado Mexicano al Grupo de Acción Financiera1",regularFont));
        bulletList.add(new ListItem("Ley General de Sociedades Mercantiles: artículos 73 y 129.",regularFont));
        bulletList.add(new ListItem("4° Plan de Acción 2019 - 2021 del Comité Coordinador de la Alianza para el Gobierno Abierto en México: compromiso número 11.",regularFont));
        bulletList.add(new ListItem("Acuerdo ACT-PUB/31/08/2022.07 del Instituto Nacional de Transparencia, Acceso a la Información y Protección de Datos Personales",regularFont));
        document.add(bulletList);

        document.add(new Phrase("\nLos datos personales que serán recabados son los siguientes:\n\n",regularFont));

        bulletList = new List(List.UNORDERED);
        bulletList.setListSymbol("• ");
        bulletList.add(new ListItem("Nombre completo de las personas físicas que tengan algún nivel de propiedad o control sobre la entidad moral",regularFont));
        bulletList.add(new ListItem("Registro Federal de Contribuyentes.",regularFont));
        bulletList.add(new ListItem("Domicilio fiscal",regularFont));
        bulletList.add(new ListItem("Actividad empresarial.",regularFont));
        bulletList.add(new ListItem("Descripción del control y/o propiedad que tienen la parte interesada en la empresa participante",regularFont));
        bulletList.add(new ListItem("Nombre completo de las personas morales que tengan algún nivel de propiedad o control sobre la empresa participante",regularFont));
        bulletList.add(new ListItem("Nombre completo de las personas físicas que tengan algún nivel de propiedad o control sobre la persona moral que tenga algún nivel de\n" + //
                        "propiedad o control sobre la empresa participante.",regularFont));
        
        document.add(bulletList);

        document.add(new Paragraph("\nLos datos mencionados podrán ser recabados, de manera directa o indirectamente, tanto de forma física como electrónica y serán utilizados única y " + //
                        "exclusivamente para las siguientes finalidades:\n\n",regularFont));
        
        bulletList = new List(List.UNORDERED);
        bulletList.setListSymbol("• ");
        bulletList.add(new ListItem("Impulsar estrategias de rendición de cuentas y combate a la corrupción en los procesos de adjudicación y contratación de obra pública;" + //
                        "y contrataciones en procesos de adquisiciones y servicios;",regularFont));
        bulletList.add(new ListItem("Publicación de información relacionadas a Actas, aperturas o Fallos en las plataformas de transparencia.",regularFont));
        document.add(bulletList);

        document.add(new Phrase("\nSe le informa que con la aceptación del presente aviso de privacidad, se entiende que otorga su consentimiento tácito para que se transfieran " + //
                        "sus datos personales a terceros, los terceros receptores pueden ser: las autoridades jurisdiccionales; otras autoridades administrativas la Auditoría " + //
                        "Superior de la Federación y la Auditoría Superior del Estado de Jalisco, la Contraloría del Estado de Jalisco, Fiscalía del Estado de Jalisco, la Fiscalía " + //
                        "Especializada en Combate a la Corrupción el Ministerio Público Federal, las Secretarías, Órganos Internos de Control, o Tribunales al Tribunal de " + //
                        "Justicia Administrativa, al Comité Coordinador de los Sistemas Nacional y Estatal de Anticorrupción Servicio de Administración Tributaria y la Comisión " + //
                        "Nacional Bancaria y de Valores.", regularFont));
        document.add(new Phrase("\n\nEjercicio de los Derechos Arco",boldFont));
        document.add(new Phrase("\n\nUsted tiene derecho a solicitar en cualquier tiempo el Acceso, Rectificación, Cancelación, Oposición o Revocación del consentimiento del uso de sus " + //
                        "datos personales, mediante la presentación de una solicitud de ejercicio de derechos ARCO a esta dirección.",regularFont));
        document.add(new Phrase("\n\nEl presente aviso de privacidad puede sufrir modificaciones y/o actualizaciones incluyendo modificaciones a las políticas de privacidad que se susciten.\n" + //
                        "Cualquier cambio en el presente aviso de privacidad se hará del conocimiento de los titulares de la información confidencial.",regularFont));
        document.add(new Phrase("\n\nFecha de actualización: Diciembre 2024.\n",regularFont));

        mainParagraph = new Paragraph();
        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.setLeading(10);
        
        mainParagraph.add(new Phrase("ATENTAMENTE\n\n\n\n",regularFont));
        mainParagraph.add(new Phrase("__________________________________\n",regularFont));
        mainParagraph.add(new Phrase(data.responsible + "\n",regularFont));
        mainParagraph.add(new Phrase("Representante Legal\n",regularFont));
        mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.\n\n",regularFont));
        document.add(mainParagraph);

        document.add(new Phrase("\n\n\n\n\n\n\n\n\n\n\n\n_______________\n",boldFont));
        Anchor anchor = new Anchor("1 https://www.fatf-gafi.org/content/dam/fatf-gafi/annual-reports/1999%202000%20ENG.pdf",regularFont);
        anchor.setReference("https://www.fatf-gafi.org/content/dam/fatf-gafi/annual-reports/1999%202000%20ENG.pdf");
        document.add(anchor);

        document.close();
        writer.close();
        
    }
    

}
