package com.karonda.springbootreturnandaspect.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.karonda.springbootreturnandaspect.core.BusinessException;
import com.karonda.springbootreturnandaspect.core.ResultEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/empty")
    public void empty() {

    }

    @RequestMapping("/exception")
    public void exception() throws BusinessException {
        throw new BusinessException(ResultEnum.PARAM_ERROR);
    }

    @RequestMapping("/object")
    public TestResult object() {
        TestResult testResult = new TestResult();
        testResult.setId(1L);
        testResult.setName("name");
        return testResult;
    }

    class TestResult {
        @JsonSerialize(using= ToStringSerializer.class)
        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
