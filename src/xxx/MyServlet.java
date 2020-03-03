package xxx;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet("*.do")
public class MyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        //request.getRequestDispatcher("/WEB-INF/templates/welcome.html").forward(request, response);

        ServletContext application = request.getServletContext();
        WebContext ctx = new WebContext(request, response, application, request.getLocale());
        ctx.setVariable("now", LocalDateTime.now().toString());
        ctx.setVariable("c", new Tiger(new Cat("hahaha")));
        Map<String, Object> map = new HashMap<>();
        map.put("aaa", 111);
        map.put("bbb", 222);
        ctx.setVariables(map);

        HttpSession session = request.getSession();
        session.setAttribute("xxx", new Tiger(new Cat("x")));
        application.setAttribute("ooo", new Tiger(new Cat("o")));

        var templateResolver = new ServletContextTemplateResolver(application);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.process("hi", ctx, response.getWriter()); // 找 /WEB-INF/templates/hi.html
    }
}
