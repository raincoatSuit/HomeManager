package com.github.gleans.controller;

import com.github.gleans.bean.ResultBean;
import com.github.gleans.model.Admin;
import com.github.gleans.service.AdminService;
import com.github.gleans.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AdminService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResultBean<?> createAuthenticationToken(@RequestBody Admin admin) throws Exception {
        authenticate(admin.getUsername(), admin.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(admin.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        admin.setToken(token);
        admin.setPassword(null);

        return new ResultBean<>(admin);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}