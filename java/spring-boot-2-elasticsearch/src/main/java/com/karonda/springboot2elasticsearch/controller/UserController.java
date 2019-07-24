package com.karonda.springboot2elasticsearch.controller;

import com.karonda.springboot2elasticsearch.entity.User;
import com.karonda.springboot2elasticsearch.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value="用户数量")
    public long count(){
        return userService.count();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value="新增用户")
    public User save(User user){
        return userService.save(user);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value="删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id", paramType = "query", required = true),
    })
    public boolean deleteById(String id){
        userService.deleteById(id);
        return true;
    }

    @RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
    @ApiOperation(value="删除所有用户")
    public boolean deleteAll(){
        userService.deleteAll();
        return true;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="用户列表")
    public Iterable<User> findAll(){
        return userService.findAll();
    }

    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ApiOperation(value="用户分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "数量", paramType = "query", required = true),
    })
    public Iterable<User> findAll(Integer pageNum, Integer pageSize){
        return userService.findAll(pageNum, pageSize);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ApiOperation(value="查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名字", paramType = "query", required = true),
    })
    public List<User> findAllByName(String name){
        return userService.findAllByName(name);
    }

    @RequestMapping(value = "/page/find", method = RequestMethod.GET)
    @ApiOperation(value="查询用户分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "数量", paramType = "query", required = true),
            @ApiImplicitParam(name = "name", value = "名字", paramType = "query", required = true),
    })
    public Page<User> findAllByName(Integer pageNum, Integer pageSize, String name){
        return userService.findAllByName(pageNum, pageSize, name);
    }
}
