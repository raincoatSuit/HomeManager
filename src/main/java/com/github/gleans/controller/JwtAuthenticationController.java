package com.github.gleans.controller;

import java.util.Objects;

import com.github.gleans.model.Admin;
import com.github.gleans.service.AdminService;
import com.github.gleans.utils.JwtTokenUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Admin admin) throws Exception {
        authenticate(admin.getUsername(), admin.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(admin.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        admin.setToken(token);

        return ResponseEntity.ok(admin);
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