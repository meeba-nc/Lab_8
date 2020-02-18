package ru.ncedu.course.catalog_example.servlet;

import ru.ncedu.course.catalog_example.service.UserService;
import ru.ncedu.course.catalog_example.util.PathConstants;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PathConstants.LOGOUT_PATH)
public class LogoutServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.logout();
        resp.sendRedirect(PathConstants.CATALOG_PATH);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.logout();
    }

}
