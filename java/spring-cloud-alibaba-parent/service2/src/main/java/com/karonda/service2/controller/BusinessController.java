package com.karonda.service2.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.karonda.model.Order;
import com.karonda.service2.mapper.OrderMapper;
import com.karonda.service2.page.OrderPage;
import com.karonda.service2.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BusinessService businessService;

    @RequestMapping("/purchase")
    public void purchase(@RequestBody Order order) throws Exception {
        businessService.purchase(order);
    }

    @RequestMapping("/page")
    public IPage<Order> purchase(@RequestBody OrderPage orderPage) {
        return orderMapper.pageList(orderPage);
    }
}