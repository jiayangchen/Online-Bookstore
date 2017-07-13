package com.heitian.ssm.service;

import com.heitian.ssm.model.OrderItem;

import java.util.List;

/**
 * Created by ChenJiayang on 2017/7/10.
 */
public interface OrderItemService {
    void addOrderItem(OrderItem orderItem);
    List<OrderItem> selectOrderItemByOCode(String ocode);
}
