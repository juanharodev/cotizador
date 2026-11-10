package com.harojuan.QuotationModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class QuotationTable implements Serializable{
    ArrayList<QuotationEntry> entries;
    public String quotationNumber;
    public LocalDate date;
    public  String receiver;


    public QuotationTable(){
        entries = new ArrayList<QuotationEntry>();
        quotationNumber = "";
        date = LocalDate.now();
        receiver = "Destinatario";
    }

    public float getSubtotal(int subTotalToGet){
        float subtotal = 0;

        for(QuotationEntry entry : entries){
            subtotal += entry.getTotal(subTotalToGet);
        }

        return subtotal;
    }

    public float getIVA(int ivaToGet){
        return getSubtotal(ivaToGet) * 0.16f;
    }

    public float getGrandTotal(int totalToGet){
        return getSubtotal(totalToGet) + getIVA(totalToGet);
    }
    
    public void addEntry(QuotationEntry newEntry){
        entries.add(newEntry);
    }

    public ArrayList<QuotationEntry> getEntries(){
        return entries;
    }
}
