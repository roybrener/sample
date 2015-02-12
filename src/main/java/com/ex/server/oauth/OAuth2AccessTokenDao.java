package com.ex.server.oauth;

import java.util.List;

public interface OAuth2AccessTokenDao {

    public OAuth2AuthenticationAccessToken findByTokenId (String tokenId);
    public OAuth2AuthenticationAccessToken findByRefreshToken (String refreshToken);

    public void save (OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken);
    public void delete (OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken);

    public OAuth2AuthenticationAccessToken findByAuthenticationId (String authenticationId);
    public List<OAuth2AuthenticationAccessToken> findByClientIdAndUserName (String clientId, String userName);
    public List<OAuth2AuthenticationAccessToken> findByClientId (String clientId);
}
