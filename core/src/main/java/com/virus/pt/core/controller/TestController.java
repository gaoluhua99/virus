package com.virus.pt.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class TestController {
    @GetMapping(value = "/test")
    public String test() {
        return "Test";
    }
}
