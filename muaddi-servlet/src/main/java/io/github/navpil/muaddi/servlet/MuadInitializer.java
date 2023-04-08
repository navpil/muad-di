package io.github.navpil.muaddi.servlet;

import io.github.navpil.muaddi.core.MuadContext;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;

import java.util.Collection;

public class MuadInitializer {

    public void init(Collection<Class<?>> allClasses, String basePath, ServletContext servletContext) {
        //Initialize Muad Context
        MuadContext muadContext = new MuadContext();
        for (Class<?> clazz : allClasses) {
            muadContext.register(clazz);
        }
        muadContext.initialize();

        //Register servlets
        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(MuadServletMapping.class)) {
                String path = clazz.getAnnotation(MuadServletMapping.class).value();
                servletContext
                        .addServlet(basePath + clazz.getName(), (HttpServlet)muadContext.get(clazz))
                        .addMapping(basePath + path);
            }
        }

    }
}
