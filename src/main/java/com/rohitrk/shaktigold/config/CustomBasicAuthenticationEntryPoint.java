package com.rohitrk.shaktigold.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohitrk.shaktigold.expceptionHandler.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Autowired
    ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ExceptionResponse errResponse = new ExceptionResponse();
        errResponse.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
        errResponse.setErrorMessage("Bad Credentials");
        errResponse.setErrorDescription("Invalid Credentials specified.");
        response.getOutputStream().println(mapper.writeValueAsString(errResponse));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName(SecurityConfiguration.REALM);
        super.afterPropertiesSet();
    }
}