package com.karonda.dubboservice;

import com.karonda.model.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    Order create(Order order) throws Exception;
}
