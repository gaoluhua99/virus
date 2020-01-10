package com.virus.pt.core.controller;

import com.virus.pt.db.service.UserAuthService;
import com.virus.pt.model.dataobject.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class TestController {
    @Autowired
    private UserAuthService userAuthService;

    @GetMapping(value = "/test")
    public UserAuth test() {
        return userAuthService.getById(1);
    }
}
