/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package com.heitian.ssm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;

@Named
public class BotStockBean {
    private static final Logger logger = Logger.getLogger("BotStockBean");
    
    /* Respond to a message from the chat */
    public String respond(String msg) {
        String response;           
        
        /* Remove question marks */
        msg = msg.toLowerCase().replaceAll("\\?", "");
        if (msg.contains("how are you")) {
            /* We are always doing great */
            response = "I'm doing great, thank you!";
        } else if (msg.contains("do you recommend ")) {
            /* Get the stock ticker from the message */
            String[] msgarr = msg.split(" ");
            String ticker = "";
            for (int i=0; i<msgarr.length-1; i++) {
                if (msgarr[i].compareTo("recommend") == 0)
                    ticker = msgarr[i+1];
            }
            ticker = ticker.toUpperCase();
            /* Recommend if the price increased in the last 6 months */
            if (ticker.length() > 0) {
                try {
                    if (get6mChange(ticker) > 1.0)
                        response = ticker + " is doing well. I recommend it.";
                    else
                        response = ticker + " is not doing well. I'm bearish on this one.";
                } catch (FileNotFoundException e) {
                    response = "Sorry, I don't know this stock (" + ticker +")";
                } catch (Exception e) {
                    response = "Sorry, I'm having trouble with my sources today :-( ";
                    response += "Ensure that your proxy configuration for Glassfish is correct.";
                }
            } else
                response = "Tell me what stock you want a recommendation on.";
        } else {
            response = "Sorry, I did not understand what you said. ";
            response += "Enter a stock symbol and ask me if I recommend buying the stock. ";
            response += "I'm good at that! For example: ";
            response += "@Duke Do you recommend ORCL?";
        }
        return response;
    }
    
    /* Find the 6 month change on Yahoo finance */
    private double get6mChange(String ticker) throws IOException, 
                                                    MalformedURLException, 
                                                    FileNotFoundException {
        float price6m;
        float priceNow;
        /* Get the date for 6 months ago */
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);
        int y = cal.get(Calendar.YEAR);
        /* Request price info from Yahoo Finance */
        URL url = new URL("http://ichart.yahoo.com/table.csv?s="+ticker+"&a="+
                           m+"&b="+d+"&c="+y+"&g=w&ignore=.csv");
        logger.log(Level.INFO, "[BotStockBean] Opening {0}", url.toString());
        URLConnection con = url.openConnection();
        /* Get today's price and the price 6 months ago from the response */
        InputStreamReader ir = new InputStreamReader(con.getInputStream());
        BufferedReader reader = new BufferedReader(ir);
        /* The response has the form:
         Date,Open,High,Low,Close,Volume,Adj Close
         2013-03-11,831.69,839.70,831.50,834.82,1594700,834.82
         2013-03-04,805.30,844.00,805.00,831.52,2931400,831.52
         ...
         (We need the last field from the second and last lines) */
        /* First line */
        String line = reader.readLine();
        logger.log(Level.INFO, "[BotStockBean] {0}", line);
        /* Second line */
        line = reader.readLine();
        logger.log(Level.INFO, "[BotStockBean] {0} ...", line);
        String[] fields = line.split(",");
        priceNow = Float.parseFloat(fields[fields.length-1]);
        /* Last line */
        line = reader.readLine();
        while (reader.ready())
            line = reader.readLine();
        fields = line.split(",");
        price6m = Float.parseFloat(fields[fields.length-1]);
        /* Relative change */
        return priceNow / price6m;
    }
}
