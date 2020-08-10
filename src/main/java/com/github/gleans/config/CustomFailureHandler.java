package com.github.gleans.config;

import com.github.gleans.bean.ResultBean;
import com.github.gleans.utils.ResUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
        ResultBean<Object> result = new ResultBean<>();
        result.setCode(401);
        if (e instanceof BadCredentialsException) {
            result.setMsg("用户名或密码错误,请重新输入");
        } else if (e instanceof LockedException) {
            result.setMsg("账户被锁定,请联系管理员");
        } else if (e instanceof DisabledException) {
            result.setMsg("账户被禁用,请联系管理员");
        } else if (e instanceof CredentialsExpiredException) {
            result.setMsg("密码已过期,登录失败");
        } else if (e instanceof AccountExpiredException) {
            result.setMsg("账户已过期,登录失败");
        } else if (e instanceof InternalAuthenticationServiceException) {
            result.setMsg(e.getCause().getMessage());
        }

        ResUtils.resJson(resp, null, result);
    }
}
