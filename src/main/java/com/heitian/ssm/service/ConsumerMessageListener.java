package com.heitian.ssm.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.heitian.ssm.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import javax.jms.*;

public class ConsumerMessageListener implements MessageListener {

    @Autowired
    private OrderService orderService;

    public void onMessage(Message message) {

        TextMessage textMsg = (TextMessage) message;
        System.out.println("receive a order");
        try {
            String orderStr = textMsg.getText();
            Order order = JSON.parseObject(orderStr,Order.class);
            orderService.addOrder(order);
            System.out.println("order has been added");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
