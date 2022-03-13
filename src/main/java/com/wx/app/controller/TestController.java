package com.wx.app.controller;/**
 * @author lingqu
 * @date 2022/2/28
 * @apiNote
 */

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('admin','test','student')")
    public String hello(){
        return "hello";
    }
}
