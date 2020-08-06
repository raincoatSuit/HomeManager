package com.github.gleans.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gleans.bean.ResultBean;
import com.github.gleans.model.Admin;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        this.onAuthenticationSuccess(request, response, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        ResultBean<Admin> result = new ResultBean<>();
        Admin admin = (Admin) auth.getPrincipal();
        result.setData(admin);
        result.setMsg("登陆成功");
        writer.write(new ObjectMapper().writeValueAsString(result));  //登录成功后返回json 前端根据返回的信息进行页面跳转
        writer.flush();
        writer.close();
    }
}
