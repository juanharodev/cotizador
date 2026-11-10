package com.harojuan.Attachments;

import java.time.LocalDate;

public class AttachmentData {
    public String number;
    public String type;
    public String name;
    public String committee;
    public LocalDate date;
    public String responsible;
    public String obligations;
    public String deliveryPlace;
    public LocalDate deliveryTime;
    public String warranty;
    public String certifications;
    public String paymentConditions;
    public String signPlace;
    public String place;
    public String present;
    public String receiver;
    



    public AttachmentData(String number, String type, String name, String committtee,
                            LocalDate date, String responsible, String obligations,
                            String deliveryPlace, LocalDate deliveryTime, String warranty,
                            String certifications, String paymentConditions, String signPlace,
                            String place, String present, String receiver){
        this.number = number;
        this.type = type;
        this.name = name;
        this.committee = committtee;
        this.date = date;
        this.responsible = responsible;
        this.obligations = obligations;
        this.deliveryPlace = deliveryPlace;
        this.deliveryTime = deliveryTime;
        this.warranty = warranty;
        this.certifications = certifications;
        this.paymentConditions = paymentConditions;
        this.signPlace = signPlace;
        this.place = place;
        this.present = present;
        this.receiver = receiver;
    }
}
