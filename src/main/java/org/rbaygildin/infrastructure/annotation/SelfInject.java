package org.rbaygildin.infrastructure.annotation;

import java.lang.annotation.*;

/**
 * Inject an reference to bean proxy in the field
 *
 * @author Yarnykh Roman (rvyarnykh@edu.hse.ru, egdeveloper@mail.ru)
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.FIELD)
public @interface SelfInject
{

}
