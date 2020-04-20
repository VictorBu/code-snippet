package com.karonda.service2.service.impl;

import com.karonda.dubboservice.OrderService;
import com.karonda.dubboservice.StorageService;
import com.karonda.model.Order;
import com.karonda.service2.service.BusinessService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Reference
    private StorageService storageService;
    @Reference
    private OrderService orderService;

    @GlobalTransactional
    @Override
    public void purchase(Order order) throws Exception {
        storageService.deduct(order.getCommodityCode(), order.getCount());
        orderService.create(order);
    }
}
