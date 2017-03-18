package com.heitian.ssm.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import javax.ejb.Stateful;
import java.util.List;

@Stateful
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class Cart {

    private Integer customerId;
    private List<Book> contens;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<Book> getContens() {
        return contens;
    }

    public void setContens(List<Book> contens) {
        this.contens = contens;
    }
}
