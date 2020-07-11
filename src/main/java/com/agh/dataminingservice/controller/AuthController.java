package com.agh.dataminingservice.controller;

import com.agh.dataminingservice.payload.ApiResponse;
import com.agh.dataminingservice.exception.AppException;
import com.agh.dataminingservice.model.Role;
import com.agh.dataminingservice.model.RoleName;
import com.agh.dataminingservice.model.User;
import com.agh.dataminingservice.payload.JwtAuthenticationResponse;
import com.agh.dataminingservice.payload.LoginRequest;
import com.agh.dataminingservice.payload.SignUpRequest;
import com.agh.dataminingservice.repository.RoleRepository;
import com.agh.dataminingservice.repository.UserRepository;
import com.agh.dataminingservice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

/**
 * Rest API for user login and registration.
 *
 * @author Arkadiusz Michalik
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    /**
     * Endpoint method responsible for authentication user.
     * <p>
     * If user successful sign in then method generate JSON Web Token with appropriate properties put in a jwt subject
     * like user principal values i.e. roles, id, username etc.
     *
     * @param loginRequest RequestBody object which accept username and also email to authenticate user.
     * @return Json Web Token needed for authorization.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    /**
     * Endpoint method responsible for register user.
     * <p>
     * If user send in request body {@link SignUpRequest} object with correctly filled fields then user get information
     * that account create successful. It is possible that username or email exist, so in this case application gives
     * response to client that he need to change this values.
     *
     * @param signUpRequest RequestBody object which gives information about user information to register.
     * @return ApiResponse object inside ResponseEntity which inform client if registration ended successful or not.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

}
