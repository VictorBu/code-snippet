package com.karonda.springboot2elasticsearch.service.impl;

import com.karonda.springboot2elasticsearch.entity.User;
import com.karonda.springboot2elasticsearch.repository.UserElasticsearchRepository;
import com.karonda.springboot2elasticsearch.service.UserService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Profile("ElasticsearchRepository")
@Service
public class UserServiceElasticsearchRepository implements UserService {

    @Autowired
    private UserElasticsearchRepository userElasticsearchRepository;

    @Override
    public long count() {
        return userElasticsearchRepository.count();
    }

    @Override
    public User save(User user) {
        return userElasticsearchRepository.save(user);
    }

    @Override
    public void deleteById(String id) {
        userElasticsearchRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userElasticsearchRepository.deleteAll();
    }

    @Override
    public Iterable<User> findAll() {
        return userElasticsearchRepository.findAll();
    }

    @Override
    public Iterable<User> findAll(Integer pageNum, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userElasticsearchRepository.findAll(pageable);
    }

    @Override
    public List<User> findAllByName(String name) {

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", name);

        Iterable<User> userIterable =  userElasticsearchRepository.search(matchQueryBuilder);

        List<User> userList = new ArrayList<>();
        userIterable.forEach(u -> userList.add(u));

        return userList;
    }

    @Override
    public Page<User> findAllByName(Integer pageNum, Integer pageSize, String name) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("name", name))
                .withPageable(PageRequest.of(pageNum, pageSize))
                .build();
        return userElasticsearchRepository.search(searchQuery);
    }
}
