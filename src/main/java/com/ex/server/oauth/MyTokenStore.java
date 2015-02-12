package com.ex.server.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by roy on 04/02/2015.
 */
@Service
public class MyTokenStore implements TokenStore {

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Autowired
    OAuth2AccessTokenDao accessTokenDao;

    @Override
    public OAuth2Authentication readAuthentication (OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication (String token) {
        return accessTokenDao.findByTokenId(token).getAuthentication();
    }

    @Override
    public void storeAccessToken (OAuth2AccessToken token, OAuth2Authentication authentication) {
        OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken = new OAuth2AuthenticationAccessToken(token, authentication,
                                                                                                              authenticationKeyGenerator.extractKey(
                                                                                                                      authentication
                                                                                                              )
        );
        accessTokenDao.save(oAuth2AuthenticationAccessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken (String tokenValue) {
        final OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken = accessTokenDao.findByTokenId(tokenValue);
        return oAuth2AuthenticationAccessToken != null ? oAuth2AuthenticationAccessToken.getoAuth2AccessToken() : null;
    }

    @Override
    public void removeAccessToken (OAuth2AccessToken token) {
        final OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken = accessTokenDao.findByTokenId(token.getValue());

        if (oAuth2AuthenticationAccessToken != null) {
            accessTokenDao.delete(oAuth2AuthenticationAccessToken);
        }
    }

    @Override
    public void storeRefreshToken (OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {

    }

    @Override
    public OAuth2RefreshToken readRefreshToken (String tokenValue) {
        return null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken (OAuth2RefreshToken token) {
        return null;
    }

    @Override
    public void removeRefreshToken (OAuth2RefreshToken token) {

    }

    @Override
    public void removeAccessTokenUsingRefreshToken (OAuth2RefreshToken refreshToken) {

    }

    @Override
    public OAuth2AccessToken getAccessToken (OAuth2Authentication authentication) {
        OAuth2AuthenticationAccessToken token = accessTokenDao.findByAuthenticationId(authenticationKeyGenerator.extractKey(authentication)
        );
        return token == null ? null : token.getoAuth2AccessToken();
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName (String clientId, String userName) {
        final List<OAuth2AuthenticationAccessToken> byClientIdAndUserName = accessTokenDao.findByClientIdAndUserName(clientId, userName);

        return byClientIdAndUserName.stream()
                                    .map(OAuth2AuthenticationAccessToken::getoAuth2AccessToken)
                                    .collect(Collectors.toList());
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId (String clientId) {
        final List<OAuth2AuthenticationAccessToken> byClientId = accessTokenDao.findByClientId(clientId);

        return byClientId.stream()
                         .map(OAuth2AuthenticationAccessToken::getoAuth2AccessToken)
                         .collect(Collectors.toList());
    }
}
