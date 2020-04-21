package com.karonda.service2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.karonda.model.Order;
import com.karonda.service2.page.OrderPage;

//public interface OrderMapper {
//    void insert(Order order);
//}

public interface OrderMapper extends BaseMapper<Order> {
    IPage<Order> pageList(OrderPage orderPage);
}
