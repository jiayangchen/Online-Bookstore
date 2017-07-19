package com.heitian.ssm.model;

import java.io.Serializable;

/**
 * Created by ChenJiayang on 2017/7/19.
 */
public class Amount implements Serializable {
    long mid;
    long uid;
    double amount;

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
