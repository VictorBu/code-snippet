package com.karonda.springboot2elasticsearch.repository;

import com.karonda.springboot2elasticsearch.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserElasticsearchRepository extends ElasticsearchRepository<User, String> {
}
