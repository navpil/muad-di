package muadservlet.example;

import io.github.navpil.muaddi.core.MuadInject;
import io.github.navpil.muaddi.servlet.MuadServletMapping;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@MuadServletMapping("/books")
public class BookServlet extends HttpServlet {

    private final BookService bookService;

    @MuadInject
    public BookServlet(BookService bookService) {
        this.bookService = bookService;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter pw = resp.getWriter()) {
            pw.write(bookService.books().toString());
            pw.flush();
        }
    }

}
