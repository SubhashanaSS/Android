package com.citibank.transactions;

public class Transactions {
    String id;
    String trAccNo;
    String trAmount;
    String beAccNo;


    public Transactions(String id, String trAccNo, String trAmount, String beAccNo) {
        this.trAccNo = trAccNo;
        this.trAmount = trAmount;
        this.beAccNo = beAccNo;
        this.id = id;
    }

    public String getTrAccNo() {
        return trAccNo;
    }

    public String getTrAmount() {
        return trAmount;
    }

    public String getBeAccNo() {
        return beAccNo;
    }

    public String getId() {
        return id;
    }
}
