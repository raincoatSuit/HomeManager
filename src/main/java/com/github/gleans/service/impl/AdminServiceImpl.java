package com.github.gleans.service.impl;

import com.github.gleans.model.Admin;
import com.github.gleans.repository.AdminRepository;
import com.github.gleans.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public Admin getById(String id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin getLoginAdminByUsername(String username) {
        return adminRepository.findByUsername(username).orElse(null);
    }


    @Override
    public String save(Admin admin) {
        if (admin.getAdminId() == null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            admin.setPwd(passwordEncoder.encode(admin.getPwd()));
        }
        adminRepository.save(admin);
        return admin.getAdminId();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username).orElse(null);
    }

}