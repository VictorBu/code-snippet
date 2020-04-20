package com.karonda.service2.service.impl;

import com.karonda.dubboservice.OrderService;
import com.karonda.model.Order;
import com.karonda.service2.mapper.OrderMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order create(Order order) throws Exception {
        if(order.getMoney() < 0) {
            throw new Exception("金额不能小于0");
        }

        orderMapper.insert(order);
        return order;
    }
}
