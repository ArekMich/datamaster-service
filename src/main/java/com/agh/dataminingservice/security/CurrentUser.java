package com.agh.dataminingservice.security;


import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * Spring security provides an annotation called {@link AuthenticationPrincipal} to access the currently
 * authenticated user in the controllers.
 * <p>
 * The following CurrentUser annotation is a wrapper around {@link AuthenticationPrincipal} annotation.
 *
 * @author Arkadiusz Michalik
 * @see AuthenticationPrincipal
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

}
