package com.heitian.ssm.service;

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
            JSONObject jsonObject = JSONObject.parseObject(orderStr);
            Order order = new Order();
            order.setOuid(jsonObject.getLong("ouid"));
            order.setAmount(jsonObject.getDouble("totalAmount"));
            order.setDate(jsonObject.getString("date"));
            orderService.addOrder(order);
            System.out.println("order has been added");

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
