package com.ex.server.oauth;

/**
 * Created by roy on 04/02/2015.
 */
public class OAuth2Conf {

    public static final String COLLECTION_NAME = "access_tokens";


    public interface Schema {
        String TOKEN_ID = "_id";
        String ACCESS_TOKEN_OBJECT = "token_object";
        String USER_NAME = "user_name";
        String CLIENT_ID = "client_id";
        String AUTHENTICATION_OBJECT = "auth_object";
        String AUTHENTICATION_ID = "auth_id";
        String REFRESH_TOKEN = "refresh_token";
    }
}
