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
 * Rest API for getting the currently logged in user, checking if a username is available for registration,
 * checking if an email is available for registration and getting user profile.
 *
 * @author Arkadiusz Michalik
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * User repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint method responsible for returning information about currently log in user.
     * <p>
     * Method uses annotation {@link CurrentUser} which gives access the currently authenticated user.
     * In that fact client don't need to put other information in request body.
     * <p>
     * Access to method has only authorize clients with role {@link com.agh.dataminingservice.model.RoleName#ROLE_USER}.
     *
     * @param currentUser Currently authenticated user in application.
     * @return User summary object only with major information about him.
     */
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    /**
     * Endpoint method responsible for checking if username is available.
     * <p>
     * If username exist in database method send response to client that user identity is not available.
     * In another case client get information in object {@link UserIdentityAvailability} that username is available.
     *
     * @param username Username value which client wants to use for registration.
     * @return {@link UserIdentityAvailability } object with boolean value "available". If "available" is set to true,
     * it means that username does not exist in database. In another case "available" value is set to false.
     */
    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    /**
     * Endpoint method responsible for checking if email is available.
     * <p>
     * If email exist in database method send response to client that user identity is not available.
     * In another case client get information in object {@link UserIdentityAvailability} that email is available.
     *
     * @param email Email value which client wants to use for registration.
     * @return {@link UserIdentityAvailability } object with boolean value "available". If "available" is set to true,
     * it means that email does not exist in database. In another case "available" value is set to false.
     */
    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    /**
     * Endpoint method responsible for returning information about selected user.
     * <p>
     * When user exist in database searched by username, method create {@link UserProfile} object response with major
     * and basic information about him like identifier, username, first and last name also additional information is date
     * when user account was created.
     *
     * @param username User name value.
     * @return {@link UserProfile} object with major information about searched user.
     */
    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt());

        return userProfile;
    }
}
