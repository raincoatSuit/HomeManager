package com.github.gleans.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class DecisionManager implements AccessDecisionManager {

    /*
     * @methodName: decide
     * @description 判断当前登录用户是否具备访问接口所需角色
     * @param:
     * @return: authentication 当前登录用户
     *          configAttributes 访问的当前接口所需要的角色集合
     **/
    @Override
    public void decide(Authentication auth, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {


        for (ConfigAttribute attribute : configAttributes) {
            //访问该接口所需要的角色
            String needRole = attribute.getAttribute();
            if (needRole.equals("ROLE_LOGIN")) {
                if (auth instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("尚未登录,请登录");
                } else {
                    return;
                }
            }

            //当前用户所具备的角色
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                //如果当前用户具备接口所需一种角色 放行 否则抛出异常
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足,请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

