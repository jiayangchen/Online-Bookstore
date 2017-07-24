package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.OrderItemDao;
import com.heitian.ssm.model.OrderItem;
import com.heitian.ssm.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ChenJiayang on 2017/7/10.
 */

@Service
@Transactional(rollbackFor = Exception.class,timeout = 1,isolation= Isolation.DEFAULT)
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    private OrderItemDao orderItemDao;

    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public void addOrderItem(OrderItem orderItem) {
        orderItemDao.addOrderItem(orderItem);
    }

    @Override
    @Transactional(readOnly=true)
    public List<OrderItem> selectOrderItemByOCode(String ocode) {
        return orderItemDao.selectOrderItemByOCode(ocode);
    }
}
