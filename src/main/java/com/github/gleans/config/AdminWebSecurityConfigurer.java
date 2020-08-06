package com.github.gleans.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gleans.bean.ResultBean;
import com.github.gleans.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class AdminWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private AdminService adminService;
    private CustomSuccessHandler customSuccessHandler;
    private CustomAuthEntryPoint authEntryPoint;
    private CustomLogoutHandler logoutHandler;

    @Autowired
    public void setLogoutHandler(CustomLogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    @Autowired
    public void setAuthEntryPoint(CustomAuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Autowired
    public void setCustomSuccessHandler(CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置失败的回调处理器
        http.authenticationProvider(authenticationProvider())
                .authorizeRequests()
                .antMatchers("/static/**", "/images/**", "/js/**", "/admin/login").permitAll() // 非 /admin下的页面可以访问
                .antMatchers("/**").authenticated() // 限制/admin下的页面必须登录后才可访问
                .and()
                .formLogin() // 表示使用自定义登录表单
                .loginProcessingUrl("/admin/login") //登录的接口url (post请求)
                .usernameParameter("username")
                .passwordParameter("password") // 自定义登录表单输入框名称
                .defaultSuccessUrl("/admin/dashboard") // 登录成功后的默认页
                .successHandler(customSuccessHandler)
                .failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = resp.getWriter();
                    ResultBean<String> result = new ResultBean<>();
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
                    }
                    writer.write(new ObjectMapper().writeValueAsString(result));
                    writer.flush();
                    writer.close();
                })
                .permitAll() // 登录页允许没登录的情况访问
                .and()
                .logout() //注销接口(get请求)
                //注销成功的回调处理器
                .logoutSuccessHandler(logoutHandler)
                .and()
                // 表示在登录表单中使用记住我功能
                .rememberMe()
                .rememberMeParameter("remember") // 表单记住我的checkbox元素的name
                .and()
                //没有认证时,在这里处理结果,不进行重定向
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                // 允许iframe中发送的请求
                .and().headers().frameOptions().disable()
                // 先禁用掉csrf
                .and().csrf().disable();

        super.configure(http);
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(adminService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }
}
