package com.project.coffeeshopapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/test")
public class TestController {
    @GetMapping
    public String test() {
        return "Hello World";
    }
}
