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

package com.ex.server.conf;

import com.ex.server.oauth.CustomUserDetailsService;
import com.ex.server.oauth.MyTokenStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

@Configuration
public class OAuth2ServerConfiguration {

    private static final String RESOURCE_ID = "restservice";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends
                                                       ResourceServerConfigurerAdapter {

        @Override
        public void configure (ResourceServerSecurityConfigurer resources) {
            // @formatter:off
            resources
                    .resourceId(RESOURCE_ID);
            // @formatter:on
        }

        @Override
        public void configure (HttpSecurity http) throws Exception {
            // @formatter:off
            http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/greeting").access("#oauth2.clientHasRole('ROLE_USER')")
                .antMatchers("/cp/**").access("#oauth2.clientHasRole('ROLE_USER')")
                .antMatchers("/quests/**").access("#oauth2.clientHasRole('ROLE_USER')")
                .antMatchers("/follow/**").access("#oauth2.clientHasRole('ROLE_USER') and authenticated")
                .antMatchers("/admin/**").access("#oauth2.clientHasRole('ROLE_ADMIN')");

            // @formatter:on
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends
                                                            AuthorizationServerConfigurerAdapter {

        @Autowired
        private CustomUserDetailsService clientService;

        @Autowired
        private MyTokenStore myTokenStore;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Override
        public void configure (AuthorizationServerSecurityConfigurer security) throws Exception {
            security.passwordEncoder(new BCryptPasswordEncoder());
        }

        @Override
        public void configure (ClientDetailsServiceConfigurer clients) throws Exception {//
            clients.withClientDetails(clientService).build();
        }

        @Override
        public void configure (AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            // @formatter:off
            endpoints
                    .tokenStore(myTokenStore)
                    .authenticationManager(this.authenticationManager);
            // @formatter:on
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices () {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(false);
            tokenServices.setTokenStore(myTokenStore);
            return tokenServices;
        }

    }

}
