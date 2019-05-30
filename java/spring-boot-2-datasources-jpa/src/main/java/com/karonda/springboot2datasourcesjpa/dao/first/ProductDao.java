package com.karonda.springboot2datasourcesjpa.dao.first;

import com.karonda.springboot2datasourcesjpa.entity.first.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductDao extends JpaRepository<Product, Integer> {

    @Query(value = "select p from Product p where p.id = id")
    Product findById(@Param("id") int id);
}
