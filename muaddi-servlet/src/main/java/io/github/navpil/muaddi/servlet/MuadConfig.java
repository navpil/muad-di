package io.github.navpil.muaddi.servlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate some class in your application with this and it will be picked up by the MuadServletContainerInitializer
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MuadConfig {

    Class[] value();

    String basePath();
}
