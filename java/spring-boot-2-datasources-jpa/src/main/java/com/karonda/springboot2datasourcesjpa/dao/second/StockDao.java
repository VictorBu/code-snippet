package com.karonda.springboot2datasourcesjpa.dao.second;

import com.karonda.springboot2datasourcesjpa.entity.second.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockDao extends JpaRepository<Stock, Integer> {

    @Query(value = "select s from Stock s where s.productId = productId")
    Stock findByProductId(@Param("productId") int productId);
}
