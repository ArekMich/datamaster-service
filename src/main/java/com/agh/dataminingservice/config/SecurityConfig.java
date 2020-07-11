package com.agh.dataminingservice.config;

import com.agh.dataminingservice.security.CustomUserDetailsService;
import com.agh.dataminingservice.security.JwtAuthenticationEntryPoint;
import com.agh.dataminingservice.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.agh.dataminingservice.config.SwaggerConfig.AUTH_WHITELIST;

/**
 * SecurityConfig class is the crux of our security implementation.
 * It contains almost all the security configurations that are required for our project.
 * <p>
 * Annotations:
 * EnableWebSecurity - This is the primary spring security annotation that is used to enable web security in a project.
 * EnableGlobalMethodSecurity - This is used to enable method level security based on annotations.
 * securedEnabled: It enables the Secured annotation using which you can protect your controller/service methods.
 * jsr250Enabled: It enables the RolesAllowed annotation that can be used.
 * prePostEnabled: It enables more complex expression based access control syntax with PreAuthorize and PostAuthorize annotations.
 *
 * @author Arkadiusz Michalik
 * @see WebSecurityConfigurerAdapter
 * WebSecurityConfigurerAdapter class implements Spring Security’s WebSecurityConfigurer interface.
 * It provides default security configurations and allows other classes to extend it and customize the security
 * configurations by overriding its methods.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * JwtAuthenticationEntryPoint object is used to return a 401 unauthorized error to clients that try to access
     * a protected resource without proper authentication.
     */
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    /**
     * CustomUserDetailsService object authenticate a User or perform various role-based checks.
     */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Creates Security config object for all security configurations that are required for our project.
     *
     * @param customUserDetailsService Custom UserDetailsService object
     * @param unauthorizedHandler      JwtAuthenticationEntryPoint object
     */
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    /**
     * @return Custom Spring Security AuthenticationFilter.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Method configure use the configured AuthenticationManager to authenticate a user in the login API.
     * <p>
     * AuthenticationManagerBuilder is used to build in-memory authentication, LDAP authentication, JDBC authentication,
     * or add your custom authentication provider.
     * <p>
     * In configure method it is provided our {@link CustomUserDetailsService} and {@link SecurityConfig#passwordEncoder()}
     * to build the AuthenticationManager.
     *
     * @param authenticationManagerBuilder AuthenticationManagerBuilder is used to create an AuthenticationManager
     *                                     instance which is the main Spring Security interface for authenticating a user.
     * @throws Exception When customUserDetailsService cannot be apply to DaoAuthenticationConfigurer
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * The HttpSecurity configurations are used to configure security functionalities like csrf, sessionManagement,
     * and add rules to protect resources based on various conditions.
     * <p>
     * HttpSecurity permitting access to {@link SwaggerConfig#AUTH_WHITELIST} and few other public APIs to everyone
     * like api/auth/ or api/user/ and restricting access to other APIs to authenticated users only.
     * <p>
     * It is also added the JWTAuthenticationEntryPoint and the custom JWTAuthenticationFilter in the
     * HttpSecurity configuration.
     *
     * @param http Specific http requests.
     * @throws Exception Thrown when configuration failed.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST)
                .permitAll()
                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers("/api/user/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
