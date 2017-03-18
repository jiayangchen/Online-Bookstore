package com.heitian.ssm.service;

import com.heitian.ssm.model.Order;

import java.util.List;

public interface OrderService {
    void addOrder(Order order);
    List<Order> getAllOrder();
}
