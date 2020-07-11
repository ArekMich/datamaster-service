package com.agh.dataminingservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom Spring Security AuthenticationFilter
 * <p>
 * JWTAuthenticationFilter used to implement a filter that:
 * <ol>
 *      <li>reads JWT authentication token from the Authorization header of all the requests</li>
 *      <li>validates the token</li>
 *      <li>loads the user details associated with that token.</li>
 *      <li>Sets the user details in Spring Security’s SecurityContext. Spring Security uses the user details to perform
 *          authorization checks. We can also access the user details stored in the SecurityContext in our controllers
 *          to perform our business logic.</li>
 * </ol>
 *
 * @author Arkadiusz Michalik
 * @see OncePerRequestFilter
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Object for generating and verifying JWT.
     */
    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * Service to authenticate a User or perform various role-based checks.
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    /**
     * doFilterInternal method first parsing the JWT retrieved from the Authorization header
     * ({@link JwtAuthenticationFilter#getJwtFromRequest(HttpServletRequest)}) of the request
     * and obtaining the user’s Id. After that, loading the user’s details from the database and setting
     * the authentication inside spring security’s context.
     * <p>
     * Database hit in the doFilterInternal is optional. It is possible to encode the user’s username and roles
     * inside JWT claims and create the UserDetails object by parsing those claims from the JWT.
     * <p>
     * However, loading the current details of the user from the database might still be helpful.
     * For example, you might wanna disallow login with this JWT if the user’s role has changed,
     * or the user has updated his password after the creation of this JWT.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(httpServletRequest);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);

                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * Method getJwtFromRequest retrieve JWT from Authorization header request.
     *
     * @param request HttpServlet Request
     * @return jwt token from Authorization header
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
