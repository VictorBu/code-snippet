package com.karonda.util;

import com.karonda.model.UserInfoModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserUtil {

    public static UserInfoModel getUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            if(authentication instanceof UsernamePasswordAuthenticationToken) {
                return (UserInfoModel)authentication.getPrincipal();
            }
        }

        return null;
    }
}
