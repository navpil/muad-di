package muadservlet.example;

import io.github.navpil.muaddi.servlet.MuadConfig;

@MuadConfig(value = {
        BookService.class,
        BookServlet.class
}, basePath = "/muad-new")
public class MuadConfiguration {
}
