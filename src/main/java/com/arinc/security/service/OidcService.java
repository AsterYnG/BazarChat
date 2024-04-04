package com.arinc.security.service;

import com.arinc.database.entity.User;
import com.arinc.dto.UserOAuthRegistrationDto;
import com.arinc.dto.UserRegistrationDto;
import com.arinc.service.UserService;
import com.arinc.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class OidcService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final UserService userService;
    private final ImageUtils imageUtils;
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcIdToken idToken = userRequest.getIdToken();
        String email = idToken.getClaim("email");

        if (locallyExist(email)){ // check if user is locally saved
            UserDetails userDetails = userService.loadUserByUsername(email);

            return new DefaultOidcUser(userDetails.getAuthorities(),idToken);
        }
        UserDetails createdUserDetails = createUser(idToken); //create new if not
        return new DefaultOidcUser(createdUserDetails.getAuthorities(), idToken);
    }

    private UserDetails createUser(OidcIdToken idToken) {
        String picPath = imageUtils.loadImage(idToken.getPicture());
        UserOAuthRegistrationDto oAuthUser = UserOAuthRegistrationDto.builder()
                .userPic(picPath)
                .login(idToken.getEmail())
                .password(String.valueOf(Math.random()))
                .build();
        userService.saveUser(oAuthUser);
        return userService.loadUserByUsername(idToken.getEmail());
    }

    private boolean locallyExist(String userName) {
        return userService.findUser(userName).isPresent();
    }


}
