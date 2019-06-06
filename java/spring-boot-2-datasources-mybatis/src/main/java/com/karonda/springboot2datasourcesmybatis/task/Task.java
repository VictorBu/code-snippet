package com.karonda.springboot2datasourcesmybatis.task;

import com.karonda.springboot2datasourcesmybatis.dao.first.ProductMapper;
import com.karonda.springboot2datasourcesmybatis.dao.second.StockMapper;
import com.karonda.springboot2datasourcesmybatis.entity.Product;
import com.karonda.springboot2datasourcesmybatis.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StockMapper stockMapper;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void job(){

        final int productId = 1;
        Product product = productMapper.getOneById(productId);
        Stock stock = stockMapper.getOneByProductId(productId);

        System.out.println("产品名称: " + product.getName() + ", 库存: " + stock.getStockCount());
    }

}
