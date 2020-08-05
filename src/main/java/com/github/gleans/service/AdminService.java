package com.github.gleans.service;

import com.github.gleans.model.Admin;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface AdminService extends UserDetailsService {
    Admin getById(String id);

    Admin getLoginAdminByUsername(String username);

    String save(Admin admin);
}