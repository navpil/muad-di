package io.github.navpil.muaddi.servlet;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@HandlesTypes({MuadConfig.class})
public class MuadServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {

        if (set != null) {
            for (Class<?> aClass : set) {
                MuadConfig annotation = aClass.getAnnotation(MuadConfig.class);
                List<Class<?>> classes = Arrays.asList(annotation.value());
                String basePath = annotation.basePath();
                new MuadInitializer().init(classes, basePath, servletContext);
            }
        }

    }
}
