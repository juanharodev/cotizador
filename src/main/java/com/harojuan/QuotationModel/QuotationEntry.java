package com.harojuan.QuotationModel;

import java.io.Serializable;

public class QuotationEntry implements Serializable{
    public int partida;
    public float amount;
    public String measureUnit;
    public String name;
    public String description;
    public float baseUnitPrice;
    public float unitPrice1;
    public float unitPrice2;
    public float unitPrice3;
   

    public static final int BASE = 0;
    public static final int PRICE_1 = 1;
    public static final int PRICE_2 = 2;
    public static final int PRICE_3 = 3;
    public static final int AVERAGE = 4;


    public QuotationEntry(int partida, float amount, String measureUnit,String name, String description, 
        float baseUnitPrice, float unitPrice1, float unitPrice2, float unitPrice3){
        this.partida = partida;
        this.amount = amount;
        this.measureUnit = measureUnit;
        this.name = name;
        this.description = description;
        this.baseUnitPrice = baseUnitPrice;
        this.unitPrice1 = unitPrice1;
        this.unitPrice2 = unitPrice2;
        this.unitPrice3 = unitPrice3;
    }

    public float getPrice(int priceToGet){
        switch (priceToGet) {
            case BASE:
                return baseUnitPrice;        
            case PRICE_1:
                return unitPrice1;
            case PRICE_2:
                return unitPrice2;
            case PRICE_3:
                return unitPrice3;
            case AVERAGE: 
                return getAveragePrice();
            default:
            return 0;
        }
    }

    public float getAveragePrice(){
        return (unitPrice1 + unitPrice2 + unitPrice3)/3.0F;
    }
    public float getTotal(int totalToGet){
        float multiplier = 0;
        switch (totalToGet) {
            case BASE:
                multiplier = baseUnitPrice;        
                break;
            case PRICE_1:
                multiplier = unitPrice1;
                break;
            case PRICE_2:
                multiplier = unitPrice2;
                break;
            case PRICE_3:
                multiplier = unitPrice3;
                break;
            case AVERAGE: 
                multiplier = getAveragePrice();
                break;
            default:
                break;
        }

        return amount * multiplier;
    }
}
