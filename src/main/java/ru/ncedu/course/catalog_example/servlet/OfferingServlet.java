package ru.ncedu.course.catalog_example.servlet;

import ru.ncedu.course.catalog_example.model.dto.OfferingDTO;
import ru.ncedu.course.catalog_example.service.AuthorizationBean;
import ru.ncedu.course.catalog_example.service.CommentService;
import ru.ncedu.course.catalog_example.service.OfferingService;
import ru.ncedu.course.catalog_example.util.PathConstants;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PathConstants.OFFERING_PATH)
public class OfferingServlet extends HttpServlet {

    private static final String OFFERING_JSP = "/offering.jsp";

    private static final String OFFERING_ATTR = "offering";
    private static final String COMMENTS_ATTR = "comments";
    private static final String AUTHORIZED_ATTR = "authorized";

    private static final String OFFERING_ID_PARAM = "id";
    private static final String MESSAGE_PARAM = "message";

    @Inject
    private AuthorizationBean authorizationBean;

    @Inject
    private OfferingService offeringService;

    @Inject
    private CommentService commentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long offeringId = Long.parseLong(req.getParameter(OFFERING_ID_PARAM));

            OfferingDTO offeringDTO = offeringService.findByIdOrThrow(offeringId);

            getServletContext().setAttribute(OFFERING_ATTR, offeringDTO);
            getServletContext().setAttribute(COMMENTS_ATTR, commentService.findAllByOffering(offeringId));
            getServletContext().setAttribute(AUTHORIZED_ATTR, authorizationBean.isAuthorized());
            getServletContext().getRequestDispatcher(OFFERING_JSP).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String message = req.getParameter(MESSAGE_PARAM);
            long offeringId = Long.parseLong(req.getParameter(OFFERING_ID_PARAM));

            commentService.create(message, offeringId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        doGet(req, resp);
    }

}
