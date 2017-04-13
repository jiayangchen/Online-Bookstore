package com.heitian.ssm.model;

import java.io.Serializable;

/**
 * Created by ChenJiayang on 2017/4/12.
 */
public class BookDesc implements Serializable {

    public BookDesc(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
}
