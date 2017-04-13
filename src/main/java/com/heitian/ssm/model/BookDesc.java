package com.heitian.ssm.model;

import java.io.Serializable;

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
