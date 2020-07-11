package com.agh.dataminingservice.payload;

import lombok.Data;

/**
 * JwtAuthentication payload response.
 * Response is send when user correctly sign in to application.
 *
 * @author Arkadiusz Michalik
 */
@Data
public class JwtAuthenticationResponse {

    /**
     * Json Web Token needed for authorization.
     * The token is a text string, included in the request header.
     */
    private String accessToken;

    /**
     * Information about what type of token the client should use for authorization.
     * Bearer tokens allow requests to authenticate/authorize using an access key, such as a JSON Web Token (JWT).
     */
    private String tokenType = "Bearer";

    /**
     * Creates response object with token type and token value.
     *
     * @param accessToken Json Web Token generated after correctly authentication.
     */
    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
