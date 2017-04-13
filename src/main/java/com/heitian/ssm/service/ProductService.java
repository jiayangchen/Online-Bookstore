package com.heitian.ssm.service;

import javax.jms.Destination;

public interface ProductService {
    void sendMessage(Destination destination, final String message);
}
