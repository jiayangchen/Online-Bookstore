package com.heitian.ssm.model;

import java.io.Serializable;

/**
 * Created by ChenJiayang on 2017/7/13.
 */
public class Provided implements Serializable{
    int gp_id;
    int b_id;
    int p_id;

    public int getGp_id() {
        return gp_id;
    }

    public void setGp_id(int gp_id) {
        this.gp_id = gp_id;
    }

    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }
}
