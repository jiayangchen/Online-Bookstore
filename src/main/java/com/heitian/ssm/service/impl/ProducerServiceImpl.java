package com.heitian.ssm.service.impl;

import com.heitian.ssm.model.Order;
import com.heitian.ssm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import javax.annotation.Resource;
import javax.jms.*;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class ProducerServiceImpl implements ProductService{

    @Resource
    private JmsTemplate jmsTemplate;

    public void sendMessage(Destination destination, final String message) {
        System.out.println("---------------producer send message-----------------");
        System.out.println("---------------producer has sent a msg:" + message);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
