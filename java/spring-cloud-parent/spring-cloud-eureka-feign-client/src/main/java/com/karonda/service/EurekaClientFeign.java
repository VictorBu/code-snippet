package com.karonda.service;

import com.karonda.config.FeignConfig;
import com.karonda.service.impl.HiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "eureka-client", configuration = FeignConfig.class
        , fallback = HiHystrix.class)
public interface EurekaClientFeign {

    @GetMapping("/hi")
    String sayHi(@RequestParam(value = "name") String name);
}
