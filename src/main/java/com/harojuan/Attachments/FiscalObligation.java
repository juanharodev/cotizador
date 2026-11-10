package com.harojuan.Attachments;

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
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class FiscalObligation extends PdfPageEventHelper {
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
        mainParagraph.add(new Phrase("ANEXO 10 - MANIFESTACIÓN DE CUMPLIMIENTO DE OBLIGACIONES FISCALES\n\n",regularFont));
        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase("DDV SOLUCIONES S.A. DE C.V. ", boldFont));
        mainParagraph.add(new Phrase("con RFC ", regularFont));
        mainParagraph.add(new Phrase("DSO181213C61", boldFont));
        mainParagraph.add(new Phrase(", manifiesto bajo protesta de decir verdad que tal como lo señala la opinión de cumplimiento" + //
            "en materia fiscal, adjunto a la presente propuesta, no me encuentro en ninguno de los supuestos de " + //
            "incumplimiento de mis obligaciones fiscales, establecidos por el artículo 32-D del Código Fiscal de la" + //
            "Federación, el cual a la letra señala:\n\n", regularFont));
        document.add(mainParagraph);
            
        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.setIndentationLeft(20);
        mainParagraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        mainParagraph.add(new Phrase("Artículo 32-D. Cualquier autoridad, ente público, entidad, órgano u organismo de los poderes Legislativo," + //
            "Ejecutivo y Judicial, de la Federación, de las entidades federativas y de los municipios, órganos autónomos, partidos " + //
            "políticos, fideicomisos y fondos, así como cualquier persona física, moral o sindicato, que reciban y ejerzan recursos " + // 
            "públicos federales, en ningún caso contratarán adquisiciones, arrendamientos, servicios u obra pública con las personas" + //
            "físicas, morales o entes jurídicos que:\n\n",regularFont));
        mainParagraph.add(new Phrase("I. Tengan a su cargo créditos fiscales firmes.\n", regularFont));
        mainParagraph.add(new Phrase("II. Tengan a su cargo créditos fiscales determinados, firmes o no, que no se encuentren pagados o " + 
            "garantizados en alguna de las formas permitidas por este Código.\n", regularFont));
        mainParagraph.add(new Phrase("III. No se encuentren inscritos en el Registro Federal de Contribuyentes.\n", regularFont));
        mainParagraph.add(new Phrase("IV. Habiendo vencido el plazo para presentar alguna declaración, provisional o no, así como aquellas " + // 
            "declaraciones correspondientes a retenciones y con independencia de que en la misma resulte o no cantidad a pagar, ésta no haya " + //
            "sido presentada. Lo dispuesto en esta también aplicará a la falta de cumplimiento de cualquier otra declaración informativa, que " + //
            "establezca el Servicio de Administración Tributaria mediante reglas de carácter general\n", regularFont));
        mainParagraph.add(new Phrase("V. Estando inscritos en el registro federal de contribuyentes, se encuentren como no localizados.\n", regularFont));
        mainParagraph.add(new Phrase("VI. Tengan sentencia condenatoria firme por algún delito fiscal. El impedimento para contratar será por un " + //
            "periodo igual al de la pena impuesta, a partir de que cause firmeza la sentencia.\n", regularFont));
        mainParagraph.add(new Phrase("VII. No hayan desvirtuado la presunción de emitir comprobantes fiscales que amparan operaciones inexistentes o " + //
            "transmitir indebidamente pérdidas fiscales y, por tanto, se encuentren en los listados a que se refieren los artículos 69-B, cuarto párrafo " + // 
            "o 69-B Bis, noveno párrafo de este Código\n", regularFont));
        mainParagraph.add(new Phrase("VIII. Hayan manifestado en las declaraciones de pagos provisionales, retenciones, definitivos o anuales, ingresos y " +  //
            "retenciones que no concuerden con los comprobantes fiscales digitales por Internet, expedientes, documentos o bases de datos que lleven las " + //
            "autoridades fiscales, tengan en su poder o a las que tengan acceso.\n", regularFont));
        mainParagraph.add(new Phrase("IX. Incumplan con las obligaciones establecidas en los artículos 32-B Ter y 32-B Quinquies de este Código.\n\n", regularFont));

        mainParagraph.add(new Phrase("La prohibición establecida en este artículo no será aplicable a los particulares que se encuentren en los supuestos de " + //
            "las fracciones I y II de este artículo, siempre que celebren convenio con las autoridades fiscales en los términos que este Código establece para " +//
            "cubrir a plazos, ya sea como pago diferido o en parcialidades, los adeudos fiscales que tengan a su cargo con los recursos que obtengan por enajenación, " +  //
            "arrendamiento, servicios u obra pública que se pretendan contratar y que no se ubiquen en algún otro de los supuestos contenidos en este artículo.\n\n", regularFont));
        
        mainParagraph.add(new Phrase("Para estos efectos, en el convenio se establecerá que los sujetos a que se refiere el primer párrafo de este artículo retengan " + //
            "una parte de la contraprestación para ser enterada al fisco federal para el pago de los adeudos correspondientes.\n\n", regularFont));

        mainParagraph.add(new Phrase("Cuando se ubiquen en los supuestos de las fracciones III, IV y VIII, los particulares contarán con un plazo de quince días para " + //
            "corregir su situación fiscal, a partir del día siguiente a aquél en que la autoridad les notifique la irregularidad detectada.\n\n", regularFont));
        
        mainParagraph.add(new Phrase("Los proveedores a quienes se adjudique el contrato, para poder subcontratar, deberán solicitar y entregar a la contratante la " + //
            "constancia de cumplimiento de las obligaciones fiscales del subcontratante, que se obtiene a través de la página de Internet del Servicio de " + //
            "Administración Tributaria\n\n", regularFont));
        mainParagraph.add(new Phrase("Los contribuyentes que requieran obtener la opinión del cumplimiento de obligaciones fiscales para realizar alguna operación " + //
            "comercial o de servicios, para obtener subsidios y estímulos, para realizar algún trámite fiscal u obtener alguna autorización en materia de impuestos " + //
            "internos, incluyendo los de comercio exterior, así como para las contrataciones por adquisición de bienes, arrendamiento, prestación de servicio y obra " + //
            "pública que vayan a realizar con los sujetos señalados en el primer párrafo de este artículo, deberán hacerlo mediante el procedimiento que " + //
            "establezca el Servicio de Administración Tributaria y las autoridades fiscales federales en materia de seguridad social, a través de las reglas" + //
            "de carácter general.\n\n", regularFont));

        mainParagraph.add(new Phrase("Para participar como proveedores de los sujetos señalados en el primer párrafo de este artículo, los contribuyentes estarán obligados a" + //
            "autorizar al Servicio de Administración Tributaria y a las autoridades fiscales federales en materia de seguridad social, para que hagan público " + //
            "el resultado de la opinión del cumplimiento, a través del procedimiento que establezcan dicho órgano desconcentrado y autoridades fiscales" + //
            "federales, mediante reglas de carácter general, además de cumplir con lo establecido en las fracciones anteriores\n\n", regularFont));
        
        document.add(mainParagraph);

        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.add(new Phrase("Robusteciendo lo anterior, manifiesto bajo protesta de decir verdad que no me encuentro en ninguno de los supuestos de " + //
            "incumplimiento establecidos en los numerales 5, 6 y 7 de la Regla 2.1.36 de la Resolución Miscelánea para el ejercicio fiscal 2025, o bien, " + //
            "encontrándome dentro de cualquiera de éstos, el contribuyente, con RFC ",regularFont));
        mainParagraph.add(new Phrase("DSO181213C61",boldFont));
        mainParagraph.add(new Phrase(", cuento con:\n\n",regularFont));

        document.add(mainParagraph);

        List orderList = new List(List.ORDERED);
        orderList.add(new ListItem("Cuento con autorización emitida por la autoridad fiscal para pagar a plazos, sin que esta me haya sido revocada.",regularFont));
        orderList.add(new ListItem("Me encuentro dentro del plazo para pagar a que se refiere el artículo 65 del Código Fiscal de la Federación.",regularFont));
        orderList.add(new ListItem("Teniendo un medio de defensa en contra de un crédito fiscal determinado, éste se encuentra debidamente garantizado hasta por el " + //
            "interés fiscal.",regularFont));
        orderList.add(new ListItem("Me encuentro pagando mis adeudos por periodo o ejercicio, en términos del segundo párrafo de la regla 2.1.49. y además que, entre la " + //
            "fecha de solicitud y la del primer pago, o entre cada pago realizado, no han transcurrido más de sesenta días naturales.",regularFont));

        document.add(orderList);

        document.add(new Phrase("\n",regularFont));

        mainParagraph = new Paragraph();
        mainParagraph.setLeading(fontSize + 1);
        mainParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        mainParagraph.setLeading(10);
        
        mainParagraph.add(new Phrase("ATENTAMENTE\n\n\n\n",regularFont));
        mainParagraph.add(new Phrase("__________________________________\n",regularFont));
        mainParagraph.add(new Phrase(data.responsible + "\n",regularFont));
        mainParagraph.add(new Phrase("Representante Legal\n",regularFont));
        mainParagraph.add(new Phrase("DDV Soluciones S.A. de C.V.",regularFont));
        document.add(mainParagraph);
        
        document.close();
        writer.close();

    }
}
