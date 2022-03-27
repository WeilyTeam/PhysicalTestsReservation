package com.wx.app.handler;/**
 * @author lingqu
 * @date 2022/3/6
 * @apiNote
 */

import com.alibaba.fastjson.JSON;
import com.wx.app.enums.CommonCode;
import com.wx.app.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        StringBuffer requestURL = request.getRequestURL();
        log.info("requestURL {}",requestURL);
        Map<String, Object> map = new HashMap<>();
        map.put("code", CommonCode.FAILED_AUTHORIZED.getCode());
        map.put("msg", CommonCode.FAILED_AUTHORIZED.getmsg());
        String json = JSON.toJSONString(map);
        log.info("json::{}",json);
        WebUtils.renderString(response,json);
    }
}
