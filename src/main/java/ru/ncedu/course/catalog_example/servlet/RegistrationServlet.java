package ru.ncedu.course.catalog_example.servlet;

import ru.ncedu.course.catalog_example.exception.UserAlreadyExistsException;
import ru.ncedu.course.catalog_example.service.UserService;
import ru.ncedu.course.catalog_example.util.PathConstants;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PathConstants.REGISTRATION_PATH)
public class RegistrationServlet extends HttpServlet {

    private static final String REGISTRATION_JSP = "/registration.jsp";

    private static final String LOGIN_ATTR = "login";
    private static final String PASSWORD_ATTR = "password";

    private static final String INVALID_CREDENTIALS = "invalidCredentials";

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().removeAttribute(INVALID_CREDENTIALS);
        getServletContext().getRequestDispatcher(REGISTRATION_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN_ATTR);
        String password = req.getParameter(PASSWORD_ATTR);

        if(login == null || password == null) {
            getServletContext().setAttribute(INVALID_CREDENTIALS, true);
            getServletContext().getRequestDispatcher(REGISTRATION_JSP).forward(req, resp);
        } else {
            try {
                userService.create(login, password);

                getServletContext().removeAttribute(INVALID_CREDENTIALS);
                resp.sendRedirect(PathConstants.CATALOG_PATH);
            } catch (UserAlreadyExistsException e) {
                getServletContext().setAttribute(INVALID_CREDENTIALS, true);
                getServletContext().getRequestDispatcher(REGISTRATION_JSP).forward(req, resp);
            }
        }
    }

}
