package com.agh.dataminingservice.payload;

import lombok.Data;

/**
 * Api payload response.
 *
 * @author Arkadiusz Michalik
 */
@Data
public class ApiResponse {

    /**
     * Is the operation successful?
     * True if yes and false if not.
     */
    private Boolean success;

    /**
     * The message is sent along with the result of the operation.
     */
    private String message;

    /**
     * Creates application response object.
     *
     * @param success Operation success.
     * @param message Operation message.
     */
    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
