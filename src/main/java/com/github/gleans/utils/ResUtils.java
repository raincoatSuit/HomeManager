package com.github.gleans.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gleans.bean.ResultBean;
import com.github.gleans.model.Admin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResUtils {

    public static void resJson(HttpServletResponse resp, Authentication auth, ResultBean<Object> resultBean) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();

        if (null != auth) {
            Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            admin.setPassword(null);
            resultBean.setData(admin);
        }

        //登录成功后返回json 前端根据返回的信息进行页面跳转
        writer.write(new ObjectMapper().writeValueAsString(resultBean));
        writer.flush();
        writer.close();
    }
}
