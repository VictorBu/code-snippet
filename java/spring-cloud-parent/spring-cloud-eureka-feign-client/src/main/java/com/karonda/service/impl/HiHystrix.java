package com.karonda.service.impl;

import com.karonda.service.EurekaClientFeign;
import org.springframework.stereotype.Component;

@Component
public class HiHystrix implements EurekaClientFeign {

    @Override
    public String sayHi(String name) {
        return "error! sorry, " + name;
    }
}
