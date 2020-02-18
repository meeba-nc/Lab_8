package ru.ncedu.course.catalog_example.servlet;

import ru.ncedu.course.catalog_example.exception.InvalidLoginPasswordException;
import ru.ncedu.course.catalog_example.service.UserService;
import ru.ncedu.course.catalog_example.util.PathConstants;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PathConstants.LOGIN_PATH)
public class LoginServlet extends HttpServlet {

    private static final String LOGIN_JSP = "/login.jsp";

    private static final String LOGIN_ATTR = "login";
    private static final String PASSWORD_ATTR = "password";

    private static final String FROM_OFFERING_ATTR = "fromOffering";

    private static final String INVALID_CREDENTIALS = "invalidCredentials";

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().removeAttribute(INVALID_CREDENTIALS);
        getServletContext().getRequestDispatcher(LOGIN_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN_ATTR);
        String password = req.getParameter(PASSWORD_ATTR);

        if(login == null || password == null) {
            getServletContext().setAttribute(INVALID_CREDENTIALS, true);
            getServletContext().getRequestDispatcher(LOGIN_JSP).forward(req, resp);
        } else {
            try {
                userService.login(login, password);

                getServletContext().removeAttribute(INVALID_CREDENTIALS);

                String fromOffering = req.getParameter(FROM_OFFERING_ATTR);
                if(fromOffering == null) {
                    resp.sendRedirect(PathConstants.CATALOG_PATH);
                } else {
                    resp.sendRedirect(PathConstants.OFFERING_PATH + "?id=" + fromOffering);
                }
            } catch (InvalidLoginPasswordException e) {
                getServletContext().setAttribute(INVALID_CREDENTIALS, true);
                getServletContext().getRequestDispatcher(LOGIN_JSP).forward(req, resp);
            }
        }
    }

}
