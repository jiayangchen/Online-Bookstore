package com.heitian.ssm.dao;

import com.heitian.ssm.model.OrderItem;
import org.springframework.stereotype.Repository;

/**
 * Created by ChenJiayang on 2017/6/15.
 */

@Repository
public interface OrderItemDao {
    void addOrderItem(OrderItem orderItem);
}
