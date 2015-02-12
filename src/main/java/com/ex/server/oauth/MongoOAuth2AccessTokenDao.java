package com.ex.server.oauth;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.ex.server.utils.SerializationUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;


/**
 * Created by roy on 04/02/2015.
 */
@Repository
public class MongoOAuth2AccessTokenDao implements OAuth2AccessTokenDao {
    private static Logger logger = LogManager.getLogger(MongoOAuth2AccessTokenDao.class);

    @Autowired
    private MongoTemplate template;
    private DBCollection accessTokensCollection;

    private DbObjectToObjectMapper<OAuth2AuthenticationAccessToken> mapper = new DbObjectToObjectMapper<OAuth2AuthenticationAccessToken>() {
        @Override
        public DBObject fromObject (OAuth2AuthenticationAccessToken object) {
            if(object == null){
                return null;
            }

            DBObject dbObject = new BasicDBObject();
            dbObject.put(Schema.AUTHENTICATION_ID, object.getAuthenticationId());
            dbObject.put(Schema.CLIENT_ID, object.getClientId());
            dbObject.put(Schema.USER_NAME, object.getUserName());

            final OAuth2AccessToken oAuth2AccessToken = object.getoAuth2AccessToken();

            if(oAuth2AccessToken != null){
                final byte[] bytes = SerializationUtils.serializeToByteArray(oAuth2AccessToken);
                dbObject.put(Schema.ACCESS_TOKEN_OBJECT, bytes);
            }

            final OAuth2Authentication authentication = object.getAuthentication();

            if(authentication != null){
                final byte[] bytes = SerializationUtils.serializeToByteArray(authentication);
                dbObject.put(Schema.AUTHENTICATION_OBJECT, bytes);
            }

            dbObject.put(Schema.TOKEN_ID, object.getTokenId());

            return dbObject;
        }

        @Override
        public OAuth2AuthenticationAccessToken toObject (DBObject dbObject) {
            if(dbObject == null){
                return null;
            }

            byte [] bytes = (byte[]) dbObject.get(Schema.AUTHENTICATION_OBJECT);

            OAuth2Authentication authentication = null;
            if(bytes != null){
                authentication = SerializationUtils.deserializeFromByte(bytes);
            }

            bytes = (byte[]) dbObject.get(Schema.ACCESS_TOKEN_OBJECT);

            OAuth2AccessToken oAuth2AccessToken = null;
            if(bytes != null){
                oAuth2AccessToken = SerializationUtils.deserializeFromByte(bytes);
            }

            String authId = (String) dbObject.get(Schema.AUTHENTICATION_ID);

            OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken = new OAuth2AuthenticationAccessToken(oAuth2AccessToken, authentication, authId);

            logger.debug("@toObject > oAuthToken: " + oAuth2AuthenticationAccessToken);

            return oAuth2AuthenticationAccessToken;
        }
    };

    @PostConstruct
    public void init(){
        accessTokensCollection = template.getCollection(COLLECTION_NAME);

    }

    private static DBObject getById(String id){
        return new BasicDBObject("_id", id);
    }
    private static DBObject getByKey(String key, String val){
        return new BasicDBObject(key, val);
    }
    public static DBObject keyval(String ... keyVal){
        assert keyVal.length % 2 == 0;

        BasicDBObject object = new BasicDBObject();

        for (int i = 0; i < keyVal.length; i += 2) {
            object.append(keyVal[i], keyVal[i + 1]);
        }

        return object;
    }

    @Override
    public OAuth2AuthenticationAccessToken findByTokenId (String tokenId) {
        final DBObject dbObject = accessTokensCollection.findOne(getById(tokenId));

        final OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken = mapper.toObject(dbObject);

        logger.debug("@findByTokenId > accessToken: " + oAuth2AuthenticationAccessToken);
        return oAuth2AuthenticationAccessToken;
    }

    @Override
    public OAuth2AuthenticationAccessToken findByRefreshToken (String refreshToken) {
        return null;
    }

    @Override
    public void save (OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken) {
        final DBObject dbObject = mapper.fromObject(oAuth2AuthenticationAccessToken);
        accessTokensCollection.save(dbObject);
    }

    @Override
    public void delete (OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken) {
        accessTokensCollection.remove(getById(oAuth2AuthenticationAccessToken.getTokenId()));
    }

    @Override
    public OAuth2AuthenticationAccessToken findByAuthenticationId (String authenticationId) {
        final DBObject dbObject = accessTokensCollection.findOne(getByKey(Schema.AUTHENTICATION_ID, authenticationId));
        final OAuth2AuthenticationAccessToken oAuth2AuthenticationAccessToken = mapper.toObject(dbObject);

        logger.debug("@findByAuthenticationId > accessToken: " + oAuth2AuthenticationAccessToken);
        return oAuth2AuthenticationAccessToken;
    }

    @Override
    public List<OAuth2AuthenticationAccessToken> findByClientIdAndUserName (String clientId, String userName) {
        final DBCursor cursor = accessTokensCollection.find(keyval(Schema.CLIENT_ID, clientId, Schema.USER_NAME, userName));
        List<OAuth2AuthenticationAccessToken> list = new ArrayList<>(cursor.count());

        while (cursor.hasNext()){
            final DBObject next = cursor.next();
            list.add(mapper.toObject(next));
        }

        logger.debug("@findByClientIdAndUserName > return " + list.size() + " tokens");
        return list;
    }

    @Override
    public List<OAuth2AuthenticationAccessToken> findByClientId (String clientId) {
        final DBCursor cursor = accessTokensCollection.find(getByKey(Schema.CLIENT_ID, clientId));
        List<OAuth2AuthenticationAccessToken> list = new ArrayList<>(cursor.count());

        while (cursor.hasNext()){
            final DBObject next = cursor.next();
            list.add(mapper.toObject(next));
        }

        logger.debug("@findByClientId > return " + list.size() + " tokens");
        return list;
    }
}
