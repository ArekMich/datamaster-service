package com.agh.dataminingservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtAuthenticationEntryPoint class is used to return a 401 unauthorized error to clients that try to access
 * a protected resource without proper authentication. It implements Spring Security’s {@link AuthenticationEntryPoint}
 * interface and provides the implementation for its
 * {@link JwtAuthenticationEntryPoint#commence(HttpServletRequest, HttpServletResponse, AuthenticationException)}  method.
 *
 * @author Arkadiusz Michalik
 * @see AuthenticationEntryPoint
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    /**
     * Commence method is called whenever an exception is thrown due to an unauthenticated user trying to access
     * a resource that requires authentication.
     * <p>
     * In this case, we’ll simply respond with a 401 error containing the exception message.
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());

        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Authentication failed. Enter correct login and password.");
    }
}
