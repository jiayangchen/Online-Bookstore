package com.heitian.ssm.model;

import java.io.Serializable;

public class UserRole implements Serializable {

    private Long RId;
    private Long UId;

    public Long getRId() {
        return RId;
    }

    public void setRId(Long RId) {
        this.RId = RId;
    }

    public Long getUId() {
        return UId;
    }

    public void setUId(Long UId) {
        this.UId = UId;
    }
}
