package com.wx.app.filter;/**
 * @author lingqu
 * @date 2022/3/1
 * @apiNote
 */

import com.alibaba.fastjson.JSON;
import com.wx.app.entity.LoginUser;
import com.wx.app.enums.CommonCode;
import com.wx.app.utils.JwtUtil;
import com.wx.app.utils.RedisCache;
import com.wx.app.utils.WebUtils;
import com.wx.app.vo.StudentInfoVo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            token = request.getHeader("refreshToken");
            if (!StringUtils.hasText(token)){
                filterChain.doFilter(request, response);
                return;
            }
        }
        //解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("data", CommonCode.TOKEN_ILLEGAL.getmsg());
            map.put("msg", CommonCode.TOKEN_ILLEGAL.getCode().toString());
            String json = JSON.toJSONString(map);
            WebUtils.renderString(response,json);
            return;
        }

        //从redis获取用户信息
        String redisKey = "login:" + userId;
        StudentInfoVo studentTestInfo = redisCache.getCacheObject(redisKey);
        if (studentTestInfo == null){
            Map<String, String> map = new HashMap<>();
            map.put("data", CommonCode.LOGIN_DATE.getmsg());
            map.put("msg", CommonCode.LOGIN_DATE.getCode().toString());
            String json = JSON.toJSONString(map);
            WebUtils.renderString(response,json);
            return;
        }
        LoginUser loginUser = studentTestInfo.getLoginUser();

        //存入SecurityContextHolder
        // 获取权限信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}
