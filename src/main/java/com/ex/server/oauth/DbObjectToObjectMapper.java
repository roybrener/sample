package com.ex.server.oauth;

import com.mongodb.DBObject;

/**
 * Created by roy on 04/02/2015.
 */
public interface DbObjectToObjectMapper<T> {

    public DBObject fromObject (T object);
    public T toObject (DBObject dbObject);
}
