package com.heitian.ssm.utils;

import org.springframework.http.HttpHeaders;

/**
 * Created by ChenJiayang on 2017/5/29.
 */
public class MyHttpHeader {
    public static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json;charset=UTF-8");
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, TU-Hash, TU-Name");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        //headers.add("Access-Control-Allow-Origin", "*");
        return headers;
    }
}
