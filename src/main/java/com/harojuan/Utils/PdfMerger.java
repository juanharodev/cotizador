package com.harojuan.Utils;
import java.io.FileOutputStream;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;


public class PdfMerger {
    public static void mergePdfs(List<String> inputFiles, String outputFile) throws Exception{
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(outputFile));
        document.open();

        for(String filePath : inputFiles){
            PdfReader reader = new PdfReader(filePath);
            int n = reader.getNumberOfPages();

            for(int i = 1; i <=n; i++){
                copy.addPage(copy.getImportedPage(reader, i));
            }
            reader.close();
        }
        document.close();
        copy.close();
    }
}
