package com.harojuan;


import com.harojuan.QuotationModel.QuotationController;
import com.harojuan.QuotationModel.QuotationTable;
import com.harojuan.QuotationModel.QuotationView;



public class Main {
    public static void main(String[] args) {
        QuotationView view = new QuotationView();
        QuotationTable model = new QuotationTable();
        QuotationController controller = new QuotationController(model, view); 
    }
}																												