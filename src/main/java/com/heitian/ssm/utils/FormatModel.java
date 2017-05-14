package com.heitian.ssm.utils;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class FormatModel{

    @NumberFormat(style=Style.CURRENCY)
    private double money;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;

    private String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public double getMoney(){
        return money;
    }
    public Date getDate(){
        return date;
    }

    public void setMoney(double money){
        this.money=money;
    }
    public void setDate(Date date){
        this.date=date;
    }

}
