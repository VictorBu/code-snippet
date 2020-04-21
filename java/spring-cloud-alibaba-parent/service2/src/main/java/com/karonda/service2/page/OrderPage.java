package com.karonda.service2.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.karonda.model.Order;

public class OrderPage extends Page<Order> {

    private String commodityCode;

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }
}
