package com.ex.server.conf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by roy on 10/02/2015.
 */
public class MyInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LogManager.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final Enumeration<String> headerNames = request.getHeaderNames();
        HashMap<String, String> headers = new HashMap<>();

        while(headerNames.hasMoreElements()){
            final String name = headerNames.nextElement();
            final String val = request.getHeader(name);

            headers.put(name, val);

        }
        logger.info("@preHandle > request: " + request + ", headers: " + headers);
        return super.preHandle(request, response, handler);
    }
}
