package com.heitian.ssm.dao;

import com.heitian.ssm.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {
    void addOrder(Order order);
    List<Order> selectAllOrder();
    void updateOrderStatus(String orderCode, int status);
    List<Order> getOrderByUId(Long uid);
}
