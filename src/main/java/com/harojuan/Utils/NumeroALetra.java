package com.harojuan.Utils;

public class NumeroALetra {

    private NumeroALetra(){

    }

    public static NumeroALetra instance;
    public static NumeroALetra getInstance(){
        if(instance == null){
            instance = new NumeroALetra();
        }
        return instance;
    }

    private static final String[] UNIDADES = {
        "", "UNO", "DOS", "TRES", "CUATRO", "CINCO",
        "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "ONCE",
        "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISÉIS",
        "DIECISIETE", "DIECIOCHO", "DIECINUEVE"
    };

    private static final String[] DECENAS = {
        "", "", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA",
        "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"
    };

    private static final String[] CENTENAS = {
        "", "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS",
        "QUINIENTOS", "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS",
        "NOVECIENTOS"
    };

    public static String numeroALetras(double numero) {
        int parteEntera = (int) numero;
        int parteDecimal = (int) Math.round((numero - parteEntera) * 100);
        return numeroALetras(parteEntera).toUpperCase() + " PESOS " + String.format("%02d", parteDecimal) + "/100 M.N.";
    }

    private static String numeroALetras(int numero) {
        if (numero == 0) {
            return "CERO";
        } else if (numero == 100) {
            return "CIEN";
        }

        StringBuilder resultado = new StringBuilder();

        if (numero >= 1_000_000) {
            int millones = numero / 1_000_000;
            resultado.append((millones == 1 ? "UN MILLÓN" : numeroALetras(millones) + " MILLONES"));
            numero %= 1_000_000;
            if (numero > 0) resultado.append(" ");
        }

        if (numero >= 1000) {
            int miles = numero / 1000;
            if (miles == 1) {
                resultado.append("MIL");
            } else {
                resultado.append(numeroALetras(miles)).append(" MIL");
            }
            numero %= 1000;
            if (numero > 0) resultado.append(" ");
        }

        if (numero >= 100) {
            int centenas = numero / 100;
            resultado.append(CENTENAS[centenas]);
            numero %= 100;
            if (numero > 0) resultado.append(" ");
        }

        if (numero >= 20) {
            int decenas = numero / 10;
            int unidades = numero % 10;
            resultado.append(DECENAS[decenas]);
            if (unidades > 0) {
                resultado.append(" Y ").append(UNIDADES[unidades]);
            }
        } else if (numero > 0) {
            resultado.append(UNIDADES[numero]);
        }

        return resultado.toString();
    }
}
