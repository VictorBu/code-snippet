package com.karonda.controller;

import com.karonda.service.EurekaClientFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    EurekaClientFeign eurekaClientFeign;

    @GetMapping("/hi")
    public String sayHi(@RequestParam String name){
        return eurekaClientFeign.sayHi(name);
    }
}
