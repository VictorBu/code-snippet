package com.karonda.springboot2elasticsearch.service.impl;

import com.karonda.springboot2elasticsearch.entity.User;
import com.karonda.springboot2elasticsearch.service.UserService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("ElasticsearchTemplate")
@Service
public class UserServiceElasticsearchTemplate implements UserService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public long count() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .build();
        return elasticsearchTemplate.count(searchQuery, User.class);
    }

    @Override
    public User save(User user) {

        IndexQuery indexQuery = new IndexQueryBuilder().withId(user.getId().toString()).withObject(user).build();
        elasticsearchTemplate.index(indexQuery);

        return user;
    }

    @Override
    public void deleteById(String id) {

        elasticsearchTemplate.delete(User.class, id);
    }

    @Override
    public void deleteAll() {

        elasticsearchTemplate.deleteIndex(User.class);
    }

    @Override
    public Iterable<User> findAll() {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .build();

        return elasticsearchTemplate.queryForList(searchQuery, User.class);
    }

    @Override
    public Iterable<User> findAll(Integer pageNum, Integer pageSize) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(pageNum, pageSize))
                .build();

        return elasticsearchTemplate.queryForList(searchQuery, User.class);
    }

    @Override
    public List<User> findAllByName(String name) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("name", name))
                .build();

        return elasticsearchTemplate.queryForList(searchQuery, User.class);
    }

    @Override
    public Page<User> findAllByName(Integer pageNum, Integer pageSize, String name) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("name", name))
                .withPageable(PageRequest.of(pageNum, pageSize))
                .build();

        return elasticsearchTemplate.queryForPage(searchQuery, User.class);
    }
}
