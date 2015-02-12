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

package com.ex.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {


    public static void main (String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public Module objectIdModule () {
//        SimpleModule module = new SimpleModule("ObjectIdModule", new Version(1, 0, 0, null, null, null));
//        module.addSerializer(ObjectId.class,
//                             new JsonSerializer<ObjectId>() {
//                                 @Override
//                                 public void serialize (ObjectId objectId, JsonGenerator jgen, SerializerProvider provider)
//                                         throws IOException, JsonProcessingException {
//                                     jgen.writeString(objectId == null ? null : objectId.toString());
//                                 }
//                             }
//        );
//        module.addDeserializer(ObjectId.class,
//                               new JsonDeserializer<ObjectId>() {
//                                   @Override
//                                   public ObjectId deserialize (JsonParser jp, DeserializationContext ctxt)
//                                           throws IOException, JsonProcessingException {
//                                       return new ObjectId(jp.readValueAs(String.class));
//                                   }
//                               }
//        ).addSerializer(Privacy.class,
//                        new JsonSerializer<Privacy>() {
//                            @Override
//                            public void serialize (Privacy value, JsonGenerator jgen, SerializerProvider provider)
//                                    throws IOException, JsonProcessingException {
//                                jgen.writeString(value != null ? value.name() : null);
//                            }
//                        }
//        ).addDeserializer(Privacy.class,
//                          new JsonDeserializer<Privacy>() {
//                              @Override
//                              public Privacy deserialize (JsonParser jp, DeserializationContext ctxt)
//                                      throws IOException, JsonProcessingException {
//                                  final String val = jp.readValueAs(String.class);
//
//                                  if(val == null){
//                                      return null;
//                                  }
//
//                                  //case number
//                                  if(val.length() == 1){
//                                      return Privacy.values()[Integer.parseInt(val)];
//                                  }
//
//                                  return Privacy.valueOf(val);
//                              }
//                          }
//        );
//
//
//        return module;
//    }
//
//    @Bean
//    public MongoBackup backup () {
//        return new MongoBackup();
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
