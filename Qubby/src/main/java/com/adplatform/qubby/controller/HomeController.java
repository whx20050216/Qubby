package com.adplatform.qubby.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/discussion";  // 访问首页自动跳转到讨论列表
    }
}