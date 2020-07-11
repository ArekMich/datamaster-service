package com.agh.dataminingservice.security;

import com.agh.dataminingservice.model.User;
import com.agh.dataminingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * To authenticate a User or perform various role-based checks, Spring security needs to load users details somehow.
 * For this purpose, It consists of an interface called {@link UserDetailsService}
 * which has a single method that loads a user based on username {@link UserDetailsService#loadUserByUsername(String)}
 * <p>
 * CustomUserDetailsService implements UserDetailsService interface and provides the implementation for
 * loadUserByUsername() method.
 *
 * @author Arkadiusz Michalik
 * @see UserDetailsService
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Method loadUserByUsername is used by Spring security. Use of findByUsernameOrEmail method  allows users
     * to log in using either username or email.
     *
     * @param usernameOrEmail Username or email
     * @return UserDetails object that Spring Security uses for performing various authentication and role based validations.
     * @throws UsernameNotFoundException Thrown when User is not found by username or email address
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        // Let people login with either username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        return UserPrincipal.create(user);
    }

    /**
     * loadUserById method create {@link UserPrincipal} from User
     * This method is used by JWTAuthenticationFilter
     *
     * @param id User id
     * @return UserDetails object that Spring Security uses for performing various authentication and role based validations.
     * @throws UsernameNotFoundException Thrown when User is not found by id
     */
    @Transactional
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with id : " + id)
                );

        return UserPrincipal.create(user);
    }
}
