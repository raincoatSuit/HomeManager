package com.github.gleans.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gleans.bean.ResultBean;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        res.setContentType("application/json;charset=utf-8");

        PrintWriter writer = res.getWriter();
        ResultBean<String> result = new ResultBean<>();
        if (e instanceof InsufficientAuthenticationException) {
            result.setCode(-1).setMsg("无权限访问！");
        }
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
