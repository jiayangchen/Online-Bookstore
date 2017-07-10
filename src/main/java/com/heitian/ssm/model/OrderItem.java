package com.heitian.ssm.model;

import java.io.Serializable;

/**
 * Created by ChenJiayang on 2017/6/15.
 */
public class OrderItem implements Serializable {
    private int ot_id;
    private String o_code;
    private Long ot_bid;
    private int ot_quantity;

    public int getOt_id() {
        return ot_id;
    }

    public void setOt_id(int ot_id) {
        this.ot_id = ot_id;
    }

    public String getO_code() {
        return o_code;
    }

    public void setO_code(String o_code) {
        this.o_code = o_code;
    }

    public Long getOt_bid() {
        return ot_bid;
    }

    public void setOt_bid(Long ot_bid) {
        this.ot_bid = ot_bid;
    }

    public int getOt_quantity() {
        return ot_quantity;
    }

    public void setOt_quantity(int ot_quantity) {
        this.ot_quantity = ot_quantity;
    }
}
