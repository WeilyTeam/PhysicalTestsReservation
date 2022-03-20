package com.wx.app.controller;/**
 * @author lingqu
 * @date 2022/2/28
 * @apiNote
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/hello")
    //@PreAuthorize("hasAnyAuthority('admin','test','student')")
    public String hello(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("token");
        log.info("token:{}",token);
        return "hello";
    }
}
