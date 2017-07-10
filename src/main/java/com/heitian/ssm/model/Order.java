package com.heitian.ssm.model;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Order implements Serializable{

    private Long oid;
    private String ocode;
    private Long ouid;
    private Long opid;
    private Timestamp o_create_time;
    private int o_status;
    private Double o_amount;

    public String getOcode() {
        return ocode;
    }

    public void setOcode(String ocode) {
        this.ocode = ocode;
    }

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

    public Long getOpid() {
        return opid;
    }

    public void setOpid(Long opid) {
        this.opid = opid;
    }

    public Timestamp getO_create_time() {
        return o_create_time;
    }

    public void setO_create_time(Timestamp o_create_time) {
        this.o_create_time = o_create_time;
    }

    public int getO_status() {
        return o_status;
    }

    public void setO_status(int o_status) {
        this.o_status = o_status;
    }

    public Double getO_amount() {
        return o_amount;
    }

    public void setO_amount(Double o_amount) {
        this.o_amount = o_amount;
    }
}
