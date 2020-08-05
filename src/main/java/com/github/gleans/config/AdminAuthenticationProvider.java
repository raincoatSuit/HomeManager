package com.github.gleans.config;

import java.util.ArrayList;

import com.github.gleans.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;


@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    private AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName(); // 获取提交的帐号
        String password = authentication.getCredentials().toString(); // 获取提交的密码

        /*
        String usernameDb = "uss"; // 帐号（模拟从数据库中取出来）
        String passwordDb = "$2a$10$nt24iQDnTy0OFgM3i5cS2ufghcP1/T/ygn8NWxfLzMzkFn9bsMoVW"; // 密码是：abc
        boolean isPassword = BCrypt.checkpw(password, passwordDb); // 比较密码是否一致
*/
        UserDetails admin = adminService.loadUserByUsername(username);

        //boolean isPassword = password.equals(admin.getPwd());
        boolean isPassword = BCrypt.checkpw(password, admin.getPassword());

        if (isPassword) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, new ArrayList()); // 创建认证的token
            token.setDetails(admin);  // 将登录帐号的信息放入token，以便Controller可以获取相关信息

            // 登录成功返回
            return token;
        } else {
            // 帐号或密码校验失败
            throw new BadCredentialsException("username or password invalid.");
        }
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }

}