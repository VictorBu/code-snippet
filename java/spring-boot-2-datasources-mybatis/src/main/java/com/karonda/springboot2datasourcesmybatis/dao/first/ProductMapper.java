package com.karonda.springboot2datasourcesmybatis.dao.first;

import com.karonda.springboot2datasourcesmybatis.entity.Product;

public interface ProductMapper {

    Product getOneById(int id);
}
