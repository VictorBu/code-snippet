package com.karonda.springboot2datasourcesmybatis.dao.second;

import com.karonda.springboot2datasourcesmybatis.entity.Stock;

public interface StockMapper {

    Stock getOneByProductId(int productId);
}
