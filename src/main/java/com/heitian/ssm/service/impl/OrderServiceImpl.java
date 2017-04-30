package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.OrderDao;
import com.heitian.ssm.model.Order;
import com.heitian.ssm.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService{

    @Resource
    private OrderDao orderDao;

    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public void addOrder(Order order) {
        this.orderDao.addOrder(order);
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
    public List<Order> getAllOrder() {
        return orderDao.selectAllOrder();
    }
}
