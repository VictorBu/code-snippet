package com.karonda.springboot2datasourcesjpa.task;

import com.karonda.springboot2datasourcesjpa.dao.first.ProductDao;
import com.karonda.springboot2datasourcesjpa.dao.second.StockDao;
import com.karonda.springboot2datasourcesjpa.entity.first.Product;
import com.karonda.springboot2datasourcesjpa.entity.second.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private StockDao stockDao;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void job(){

        final int productId = 1;
        Product product = productDao.findById(productId);
        Stock stock = stockDao.findByProductId(productId);

        System.out.println("产品名称: " + product.getName() + ", 库存: " + stock.getStockCount());
    }

}
