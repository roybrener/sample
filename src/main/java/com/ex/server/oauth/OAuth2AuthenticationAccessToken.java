package com.ex.server.oauth;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * Created by roy on 04/02/2015.
 */
public class OAuth2AuthenticationAccessToken {
    private String tokenId;
    private OAuth2AccessToken oAuth2AccessToken;
    private String authenticationId;
    private String userName;
    private String clientId;
    private OAuth2Authentication authentication;
    private String refreshToken;

    public OAuth2AuthenticationAccessToken () {
    }

    public OAuth2AuthenticationAccessToken(final OAuth2AccessToken oAuth2AccessToken, final OAuth2Authentication authentication, final String authenticationId) {
        this.tokenId = oAuth2AccessToken.getValue();
        this.oAuth2AccessToken = oAuth2AccessToken;
        this.authenticationId = authenticationId;
        this.userName = authentication.getName();
        this.clientId = authentication.getOAuth2Request().getClientId();
        this.authentication = authentication;
        final OAuth2RefreshToken refreshToken1 = oAuth2AccessToken.getRefreshToken();
        this.refreshToken = refreshToken1 != null ? refreshToken1.getValue() : null;
    }

    public String getTokenId () {
        return tokenId;
    }

    public OAuth2AccessToken getoAuth2AccessToken () {
        return oAuth2AccessToken;
    }

    public String getAuthenticationId () {
        return authenticationId;
    }

    public String getUserName () {
        return userName;
    }

    public String getClientId () {
        return clientId;
    }

    public OAuth2Authentication getAuthentication () {
        return authentication;
    }

    public String getRefreshToken () {
        return refreshToken;
    }

    @Override
    public String toString () {
        return "OAuth2AuthenticationAccessToken{" +
                "tokenId='" + tokenId + '\'' +
                ", oAuth2AccessToken=" + oAuth2AccessToken +
                ", authenticationId='" + authenticationId + '\'' +
                ", userName='" + userName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", authentication=" + authentication +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
