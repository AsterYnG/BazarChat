package com.arinc.security.service;

import com.arinc.dto.UserDto;
import com.arinc.dto.UserOAuthRegistrationDto;
import com.arinc.service.UserService;
import com.arinc.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class OidcService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final UserService userService;
    private final ImageUtils imageUtils;
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUserService oidcUserService = new OidcUserService();
        OidcUser requestOidcUser = oidcUserService.loadUser(userRequest);
        Optional<UserDto> localSavedUser = userService.findUser(requestOidcUser.getEmail());
        if (localSavedUser.isEmpty()){
            createUser(requestOidcUser.getIdToken());
        }

        UserDetails userDetails = userService.loadUserByUsername(requestOidcUser.getEmail());
        DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), requestOidcUser.getIdToken(), requestOidcUser.getUserInfo());
        Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());

        return (OidcUser) Proxy.newProxyInstance(OidcService.class.getClassLoader(), new Class[]{UserDetails.class, OidcUser.class},
                (proxy, method, args) ->
                    userDetailsMethods.contains(method)
                            ? method.invoke(userDetails, args)
                            : method.invoke(oidcUser, args)
                );
    }

    private void createUser(OidcIdToken idToken) {
        String picPath = imageUtils.loadImageByUrl(idToken.getPicture());
        UserOAuthRegistrationDto oAuthUser = UserOAuthRegistrationDto.builder()
                .userPic(picPath)
                .email(idToken.getEmail())
                .login(idToken.getEmail())
                .password(String.valueOf(Math.random()))
                .roleId(1)
                .build();
        userService.saveUser(oAuthUser);
    }



}
