package com.gzw.auth.config;

import com.gzw.auth.domain.TokenTemplate;
import com.gzw.auth.utils.JdbcOperateUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by qzj on 2018/3/13
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    private static String getApprovalKey(OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication() == null ? "" : authentication.getUserAuthentication().getName();
        return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
    }

    private static String getApprovalKey(String clientId, String userName) {
        return clientId + (userName == null ? "" : ":" + userName);
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String principal = "";
        if (authentication.getPrincipal() instanceof UserDetails) {
            principal = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken token = ((DefaultOAuth2AccessToken) accessToken);
            token.setValue(getNewToken());

            OAuth2RefreshToken refreshToken = token.getRefreshToken();
            if (refreshToken instanceof DefaultOAuth2RefreshToken) {
                token.setRefreshToken(new DefaultOAuth2RefreshToken(getNewToken()));
            }

            Map<String, Object> additionalInformation = new HashMap<>();
            additionalInformation.put("client_id", authentication.getOAuth2Request().getClientId());
            token.setAdditionalInformation(additionalInformation);
            TokenTemplate tokenTemplate = new TokenTemplate();
            tokenTemplate.setPrincipal(principal);
            tokenTemplate.setAccess(token.getValue());
            tokenTemplate.setAuth_to_access(this.authenticationKeyGenerator.extractKey(authentication));
            tokenTemplate.setUname_to_access(getApprovalKey(authentication));
            tokenTemplate.setClient_id_to_access(authentication.getOAuth2Request().getClientId());
            tokenTemplate.setTokenType(token.getTokenType());
            tokenTemplate.setExpiration(token.getExpiration());
            JdbcOperateUtils.update(tokenTemplate);
            return token;
        }
        return accessToken;
    }

    private String getNewToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
