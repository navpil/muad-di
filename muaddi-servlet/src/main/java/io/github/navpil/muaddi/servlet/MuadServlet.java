package io.github.navpil.muaddi.servlet;

import io.github.navpil.muaddi.core.MuadContext;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;

public class MuadServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) {
        String beans = config.getInitParameter("beans");
        String[] allClasses = beans.split("\\s+");

        //Initialize Muad Context
        MuadContext muadContext = new MuadContext();
        for (String className : allClasses) {
            Class<?> clazz = getClassForName(className);
            muadContext.register(clazz);
        }
        muadContext.initialize();

        //Register servlets
        for (String className : allClasses) {
            Class<?> clazz = getClassForName(className);
            if (clazz.isAnnotationPresent(MuadServletMapping.class)) {
                String path = clazz.getAnnotation(MuadServletMapping.class).value();
                config.getServletContext()
                        .addServlet(className, (HttpServlet)muadContext.get(clazz))
                        //Good behaving servlets will usually prepend it with own path, such as /muad + /books
                        //MuadServlet is not a good behaving one
                        .addMapping(path);
            }
        }
    }

    private static Class<?> getClassForName(String s) {
        try {
            return Class.forName(s);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
