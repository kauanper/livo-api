package com.livo.oauth2_service.service;

import com.livo.oauth2_service.model.UserInfo;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2UserService {

    public UserInfo extractUserInfo(OAuth2User oAuth2User) {
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String picture = oAuth2User.getAttribute("picture");

        return new UserInfo(name, email, picture);
    }
}
