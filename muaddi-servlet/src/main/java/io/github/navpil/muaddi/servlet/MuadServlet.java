package io.github.navpil.muaddi.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;

import java.util.HashSet;
import java.util.Set;

/**
 * Older way to initialize Muad'DI - this servlet should be registered in web.xml for Muad'DI to work.
 */
public class MuadServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) {
        String beans = config.getInitParameter("beans");
        String basePath = config.getInitParameter("base-path");
        String[] classNames = beans.split("\\s+");

        Set<Class<?>> allClasses = new HashSet<>();
        for (String clazz : classNames) {
            allClasses.add(getClassForName(clazz));
        }

        new MuadInitializer().init(allClasses, basePath, config.getServletContext());
    }

    private static Class<?> getClassForName(String s) {
        try {
            return Class.forName(s);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
