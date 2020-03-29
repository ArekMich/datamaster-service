package com.agh.dataminingservice.controller;

import com.agh.dataminingservice.exception.ResourceNotFoundException;
import com.agh.dataminingservice.model.User;
import com.agh.dataminingservice.payload.UserIdentityAvailability;
import com.agh.dataminingservice.payload.UserProfile;
import com.agh.dataminingservice.payload.UserSummary;
import com.agh.dataminingservice.repository.UserRepository;
import com.agh.dataminingservice.security.CurrentUser;
import com.agh.dataminingservice.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * In UserController, We’ll be writing APIs to -
 *
 * Get the currently logged in user.
 * Check if a username is available for registration.
 * Check if an email is available for registration.
 * Get the public profile of a user.
 */

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    //example endpoint: http://localhost:8090/api/user/me
    //Key: Authorization
    //Value: Bearer $ACCESS_TOKEN
    //$ACCESS_TOKEN is a value in body field "accessToken" from response: http://localhost:8090/api/auth/signin
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    //example endpoint: http://localhost:8090/api/user/checkUsernameAvailability?username=arekmich
    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    //example endpoint: http://localhost:8090/api/user/checkEmailAvailability?email=arkadiusz.michalik@gmail.com
    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    //example endpoint: http://localhost:8090/api/users/arekmich
    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt());

        return userProfile;
    }
}
