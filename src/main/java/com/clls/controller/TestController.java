package com.clls.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("test")
public class TestController {

    @RequestMapping("test")
    public String test(HttpServletResponse response, HttpServletRequest request){
        if(request.getCookies()!=null){
            Cookie[] cookies=request.getCookies();
            for (Cookie cookie1 : cookies) {
                System.out.println(cookie1.getValue());
            }
        }
        return "success";
    }
}
