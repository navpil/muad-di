package io.github.navpil.muaddi.servlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation to use on the HttpServlet class so it's picked up by Muad'DI servlet system
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MuadServletMapping {
    String value();
}
