package com.github.gleans.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;


@Data
@Entity(name = "admin")
public class Admin implements UserDetails {

    @Id
    @GeneratedValue(generator = "customGenerationId")
    @GenericGenerator(name = "customGenerationId", strategy = "com.github.gleans.utils.CustomGenerationId",
            parameters = {@Parameter(name = "idPrefix", value = "ADMIN")})
    private String adminId;

    private Long insertTime;

    private Long updateTime;

    private Integer isDeleted;

    private String username;

    private String password;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /*账号是否没过期*/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*账号是否没过期*/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*密码是否没过期*/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}