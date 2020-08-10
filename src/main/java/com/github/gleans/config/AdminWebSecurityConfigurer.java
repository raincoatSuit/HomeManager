//package com.github.gleans.config;
//
//import com.github.gleans.filter.JwtRequestFilter;
//import com.github.gleans.service.AdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
////@Configuration
//@EnableWebSecurity
////@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class AdminWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
//
//    private AdminService adminService;
//    private CustomSuccessHandler customSuccessHandler;
//    private CustomAuthEntryPoint authEntryPoint;
//    private CustomFailureHandler customFailureHandler;
//
//    @Autowired
//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Autowired
//    public void setCustomFailureHandler(CustomFailureHandler customFailureHandler) {
//        this.customFailureHandler = customFailureHandler;
//    }
//
//    @Autowired
//    public void setAuthEntryPoint(CustomAuthEntryPoint authEntryPoint) {
//        this.authEntryPoint = authEntryPoint;
//    }
//
//    @Autowired
//    public void setCustomSuccessHandler(CustomSuccessHandler customSuccessHandler) {
//        this.customSuccessHandler = customSuccessHandler;
//    }
//
//    @Autowired
//    public void setAdminService(AdminService adminService) {
//        this.adminService = adminService;
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//// make sure we use stateless session; session won't be used to
//// store user's state.
////        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////// Add a filter to validate the tokens with every request
////        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        //配置失败的回调处理器
//       /* http.authenticationProvider(authenticationProvider())
//                .authorizeRequests()
////                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
////                    @Override
////                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
//////                        object.setAccessDecisionManager(myDesicionManage);//注入用户登录权限
////                        object.setSecurityMetadataSource(beforeFilter);//注入用户所请求的地址所需要的权限
////                        return object;
////                    }
////                })
//                ////不要验证此特定请求
//                .antMatchers("/authenticate").permitAll()
//                .antMatchers("/static/**", "/images/**", "/js/**", "/admin/login").permitAll() // 非 /admin下的页面可以访问
//                .antMatchers("/**").authenticated() // 限制/admin下的页面必须登录后才可访问
//                .and()
////                .formLogin() // 表示使用自定义登录表单
////                .loginProcessingUrl("/admin/login") //登录的接口url (post请求)
////                .usernameParameter("username")
////                .passwordParameter("password") // 自定义登录表单输入框名称
////                .defaultSuccessUrl("/admin/dashboard") // 登录成功后的默认页
////                .successHandler(customSuccessHandler)
////                .failureHandler(customFailureHandler)
////                .permitAll() // 登录页允许没登录的情况访问
////                .and()
//                .logout() //注销接口(get请求)
//                //注销成功的回调处理器
//                .logoutSuccessHandler((req, resp, e) -> ResUtils.resJson(resp, null, new ResultBean<>().setMsg("注销完成...")))
//                .and()
//                // 表示在登录表单中使用记住我功能
//                .rememberMe()
//                .rememberMeParameter("remember") // 表单记住我的checkbox元素的name
//                .and()
//
//                //没有认证时,在这里处理结果,不进行重定向
//                .exceptionHandling()
//                .authenticationEntryPoint(authEntryPoint)
//                // 允许iframe中发送的请求
//                .and().headers().frameOptions().disable()
//                // 先禁用掉csrf
//                .and().csrf().disable()
//        ;
//        // Add a filter to validate the tokens with every request
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        super.configure(http);*/
//        // We don't need CSRF for this example
//        httpSecurity.csrf().disable()
//// dont authenticate this particular request
//        .authorizeRequests().antMatchers("/authenticate").permitAll().
//// all other requests need to be authenticated
//        anyRequest().authenticated()
//        .and().
//// make sure we use stateless session; session won't be used to
//// store user's state.
//        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//// Add a filter to validate the tokens with every request
//        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//
////    @Bean
////    public AuthenticationProvider authenticationProvider() {
////        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
////        authenticationProvider.setUserDetailsService(adminService);
////        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
////        return authenticationProvider;
////    }
//}
