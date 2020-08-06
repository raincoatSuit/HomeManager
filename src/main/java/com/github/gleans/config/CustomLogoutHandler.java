package com.github.gleans.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gleans.bean.ResultBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomLogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
        res.setContentType("application/json;charset=utf-8");
        PrintWriter writer = res.getWriter();
        ResultBean<String> result = new ResultBean<>();
        result.setMsg("注销成功");
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
