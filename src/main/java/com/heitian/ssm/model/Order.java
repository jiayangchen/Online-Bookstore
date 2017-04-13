package com.heitian.ssm.model;


import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable{
    private Long oid;
    private Long ouid;
    private Double amount;
    private String date;

    public Long getOid() {
        return oid;
    }
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public Long getOuid() {
        return ouid;
    }
    public void setOuid(Long ouid) {
        this.ouid = ouid;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
