package com.karonda.springbootswagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试接口")
public class HiController {

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    @ApiOperation(value="打招呼")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "firstName", value = "名字", paramType = "query", required = true),
            @ApiImplicitParam(name = "lastName", value = "姓氏", paramType = "query", required = true)
    })
    public String sayHi(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){
        return "Hi, " + firstName + " " + lastName;
    }
}
