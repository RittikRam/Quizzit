package com.rittik.MyQuizzApp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String print(){
        return "Hello World";
    }
}
