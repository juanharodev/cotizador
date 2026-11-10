package com.harojuan.ExportFormats;

import com.harojuan.QuotationModel.QuotationTable;

public interface QuotationExporter {
    public  void ExporQuotation(QuotationTable tableReference,int PriceToExport, String filePath, String fileName);
}
