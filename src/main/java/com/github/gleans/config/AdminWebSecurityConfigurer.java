package com.github.gleans.config;

import com.github.gleans.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AdminWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers("/static/**", "/images/**", "/js/**", "/css/**").permitAll() // 非 /admin下的页面可以访问
                .antMatchers("/**").authenticated() // 限制/admin下的页面必须登录后才可访问
                .and()
                .formLogin() // 表示使用自定义登录表单
                .loginPage("/admin/login") // 自定义登录页面（使用controller），页面上的表单要post到/admin/loginPage
                .usernameParameter("uname").passwordParameter("pwd") // 自定义登录表单输入框名称
                .defaultSuccessUrl("/admin/dashboard") // 登录成功后的默认页
                .permitAll() // 登录页允许没登录的情况访问

                .and()
                .rememberMe() // 表示在登录表单中使用记住我功能
                .rememberMeParameter("remember") // 表单记住我的checkbox元素的name
                .userDetailsService(adminService) // 记住我功能要用到，需要用到UserDetailsService

                .and()
                .logout() // 表示使用自定义登出表单
                .logoutUrl("/admin/logout") // 自定义登出页面（使用controller），页面上的表单要POST到/admin/logoutPage
                .logoutSuccessUrl("/admin/login?logout") // 登出后跳转到的页面
                .permitAll() // 登出页允许没登录的情况访问

                .and().headers().frameOptions().disable() // 允许iframe中发送的请求

                .and().csrf().disable(); // 先禁用掉csrf，否则POST的时候会报403

        super.configure(http); //To change body of generated methods, choose Tools | Templates.
    }

}
