package com.heitian.ssm.model;

import com.heitian.ssm.utils.WsConstants;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlRootElement
@Singleton
@Stateless
public class User implements Serializable{

    private Long uid;
    private String uName;
    private String uPassword;
    private Long rid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
}
