package com.geo.spring.geospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/getUser")
    public String getUser(String user) {
        System.out.println(user + ":getUser");
        return "user2019";
    }
}
