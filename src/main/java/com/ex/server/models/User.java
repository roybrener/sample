/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ex.server.models;

import com.ex.server.utils.Sets;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Document(collection ="users")
@TypeAlias("user")
public class User implements ClientDetails {

    private static final Set<String> RESOURCE_IDS;
    private static final Set<GrantedAuthority> AUTHORITIES;
    private static final Set<String> GRANTS;

    static {
        RESOURCE_IDS = Sets.getNewHashSetWithValues("QUESTTER");
        AUTHORITIES = Sets.getNewHashSetWithValues(new SimpleGrantedAuthority("ROLE_USER"));
        GRANTS = Sets.getNewHashSetWithValues("refresh_token", "password", "authorization_code");
    }

    private ObjectId id;
    private String email;
    private String name;
    private String password;
    private Set<String> rIds;
    private String secret;
    private Set<String> scope;
    private Set<String> auths;


    public User (ObjectId id,
                 String email,
                 String name,
                 String password,
                 Set<String> rIds,
                 String secret
                 ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.rIds = rIds;
        this.secret = secret;
    }

    public User () {
    }

    public User (User user) {
        super();
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }


    @Override
    public String getClientId () {
        return email;
    }

    @Override
    public Set<String> getResourceIds () {
        return null;
    }

    @Override
    public boolean isSecretRequired () {
        return false;
    }

    @Override
    public String getClientSecret () {
        return secret;
    }

    @Override
    public boolean isScoped () {
        return false;
    }

    @Override
    public Set<String> getScope () {
        return scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes () {
        return GRANTS;
    }

    @Override
    public Set<String> getRegisteredRedirectUri () {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities () {
        if(auths != null){
            final Set<GrantedAuthority> set = new HashSet<>();

            for (String auth : auths) {
                set.add(new SimpleGrantedAuthority(auth));
            }

            return set;
        }
        return AUTHORITIES;
    }

    @Override
    public Integer getAccessTokenValiditySeconds () {
        return null;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds () {
        return null;
    }

    @Override
    public boolean isAutoApprove (String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation () {
        return null;
    }

    public void setScope (Set<String> scope) {
        this.scope = scope;
    }

    public ObjectId getId () {
        return id;
    }

    public void setId (ObjectId id) {
        this.id = id;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public Set<String> getrIds () {
        return rIds;
    }

    public void setrIds (Set<String> rIds) {
        this.rIds = rIds;
    }

    public String getSecret () {
        return secret;
    }

    public void setSecret (String secret) {
        this.secret = secret;
    }

    public Set<String> getGrants () {
        return GRANTS;
    }

}