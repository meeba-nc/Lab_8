package ru.ncedu.course.catalog_example.servlet;

import ru.ncedu.course.catalog_example.service.AuthorizationBean;
import ru.ncedu.course.catalog_example.service.CategoriesService;
import ru.ncedu.course.catalog_example.service.OfferingService;
import ru.ncedu.course.catalog_example.util.PathConstants;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PathConstants.CATALOG_PATH)
public class CatalogServlet extends HttpServlet {

    private static final String CATEGORIES_ATTR = "categories";
    private static final String OFFERINGS_ATTR = "offerings";
    private static final String AUTHORIZED_ATTR = "authorized";

    private static final String NAME_PARAM = "name";
    private static final String PRICE_PARAM = "price";
    private static final String CATEGORY_PARAM = "category";

    private static final String CATALOG_JSP = "/catalog.jsp";

    @Inject
    private OfferingService offeringService;

    @Inject
    private CategoriesService categoriesService;

    @Inject
    private AuthorizationBean authorizationBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().setAttribute(CATEGORIES_ATTR, categoriesService.findAll());
        getServletContext().setAttribute(OFFERINGS_ATTR, offeringService.findAll());
        getServletContext().setAttribute(AUTHORIZED_ATTR, authorizationBean.isAuthorized());
        getServletContext().getRequestDispatcher(CATALOG_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(NAME_PARAM);
        long price = Long.parseLong(req.getParameter(PRICE_PARAM));
        long category = Long.parseLong(req.getParameter(CATEGORY_PARAM));

        try {
            offeringService.create(name, price, category);
        } catch (Exception e) {
            e.printStackTrace();
        }

        doGet(req, resp);
    }

}
