package ru.ncedu.course.catalog_example.service;

import ru.ncedu.course.catalog_example.exception.OfferingNotFoundException;
import ru.ncedu.course.catalog_example.exception.UnauthorizedException;
import ru.ncedu.course.catalog_example.model.dao.CommentDAO;
import ru.ncedu.course.catalog_example.model.dao.OfferingDAO;
import ru.ncedu.course.catalog_example.model.dto.CommentDTO;
import ru.ncedu.course.catalog_example.model.entity.CommentEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CommentService {

    @Inject
    private AuthorizationBean authorizationBean;

    @Inject
    private CommentDAO commentDAO;

    @Inject
    private OfferingDAO offeringDAO;

    public CommentDTO create(String message, long offering) throws UnauthorizedException, OfferingNotFoundException {
        CommentEntity entity = new CommentEntity();
        entity.setMessage(message);
        entity.setOffering(offeringDAO.findById(offering).orElseThrow(OfferingNotFoundException::new));
        entity.setAuthor(authorizationBean.getUser().orElseThrow(UnauthorizedException::new));
        commentDAO.create(entity);
        return new CommentDTO(entity);
    }

    public List<CommentDTO> findAllByOffering(long offering) {
        return commentDAO.findByOfferingId(offering).stream().map(CommentDTO::new).collect(Collectors.toList());
    }

}
