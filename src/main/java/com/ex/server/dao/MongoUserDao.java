package com.ex.server.dao;

import com.ex.server.models.User;
import com.mongodb.DBCollection;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.annotation.PostConstruct;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by roy on 03/02/2015.
 */
@Repository
public class MongoUserDao implements UserDao {

    static final Logger log = LogManager.getLogger(MongoUserDao.class.getSimpleName());
    @Autowired
    MongoTemplate template;

    private DBCollection users;

    @Autowired
    private MongoTemplate mongo;

    @Autowired
    PasswordEncoder encoder;

    @PostConstruct
    public void init(){
        final DBCollection collection = mongo.getDb().getCollection("users");

        if(collection.count() == 0){
            mongo.save(new User(new ObjectId(), "roy@gib.me", "Roy B", encoder.encode("123456"), null, null));
        }
    }

    @Override
    public User findByEmail (String login) {
        return mongo.findOne(query(where("email").is(login)), User.class);
    }

    @Override
    public User findById (String clientId) {
        return mongo.findById(new ObjectId(clientId), User.class);
    }

    @Override
    public User getUserById (ObjectId id) {
        return template.findOne(query(where("_id").is(id)), User.class);
    }



    @Override
    public List<User> findUsersByName (String name, Integer start, Integer numOfMatches) {

        return template.find(query(where("name").is(name)), User.class);
    }





}
