package com.heitian.ssm.service;

import com.heitian.ssm.model.Order;

import java.util.List;

public interface OrderService {
    void addOrder(Order order);
    List<Order> getAllOrder();
    void updateOrderStatus(String orderCode, int status);
    List<Order> getOrderByName(String name);
    Order getOrderByCode(String ocode);
    List<Order> getOrderByStatus(String name, int status);
    List<Order> getOrderByPId(long opid);
}
