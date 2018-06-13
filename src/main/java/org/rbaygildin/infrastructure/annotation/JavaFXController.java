package org.rbaygildin.infrastructure.annotation;

import org.springframework.stereotype.Component;

/**
 * Mark a bean as JavaFX controller
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 *
 * @see Component
 */
@Component
public @interface JavaFXController {
    String value() default "";
}
