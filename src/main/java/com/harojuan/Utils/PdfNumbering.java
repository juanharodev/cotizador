package com.harojuan.Utils;

import java.io.FileOutputStream;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PdfNumbering {
    public static void addPageNumber(String inputPdf, String outputPdf) throws Exception{
        PdfReader reader = new PdfReader(inputPdf);
        int total = reader.getNumberOfPages();

        PdfStamper stamper = new PdfStamper(reader,new FileOutputStream(outputPdf));
        BaseFont baseFont = BaseFont.createFont("fonts/calibri-regular.ttf",BaseFont.CP1250,BaseFont.EMBEDDED);
        
        float x = 297.5f; // centro aproximado para A4 (595/2)
        float y = 7f;    // margen inferior

        for(int i = 1; i<= total; i++){
            PdfContentByte canvas = stamper.getOverContent(i);
            String text = "Página " + i + " de " + total;
            
            canvas.beginText();
            canvas.setFontAndSize(baseFont, 10);
            canvas.showTextAligned(PdfContentByte.ALIGN_CENTER, text, x, y, 0);
            canvas.endText();
        } 

        stamper.close();
        reader.close();
    }
}
