package com.github.gleans.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gleans.bean.ResultBean;
import com.github.gleans.utils.ResUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        ResultBean<Object> result = new ResultBean<>().setCode(401).setMsg(e.getMessage());
        ResUtils.resJson(res, null, result);
    }
}
