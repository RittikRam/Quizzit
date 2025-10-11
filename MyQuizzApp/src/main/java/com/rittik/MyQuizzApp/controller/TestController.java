package com.rittik.MyQuizzApp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PostMapping("/test")
    public String print(){
        return "Hello World";
    }
}
