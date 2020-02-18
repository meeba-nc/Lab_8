package ru.ncedu.course.catalog_example.service;

import ru.ncedu.course.catalog_example.exception.CategoryNotFoundException;
import ru.ncedu.course.catalog_example.exception.OfferingNotFoundException;
import ru.ncedu.course.catalog_example.exception.UnauthorizedException;
import ru.ncedu.course.catalog_example.model.dao.CategoryDAO;
import ru.ncedu.course.catalog_example.model.dao.OfferingDAO;
import ru.ncedu.course.catalog_example.model.dto.OfferingDTO;
import ru.ncedu.course.catalog_example.model.entity.OfferingEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OfferingService {

    @Inject
    private OfferingDAO offeringDAO;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private AuthorizationBean authorizationBean;

    public OfferingDTO create(String name, Long price, Long category) throws CategoryNotFoundException, UnauthorizedException {
        OfferingEntity entity = new OfferingEntity();

        entity.setName(name);
        entity.setPrice(price);
        entity.setCategory(categoryDAO.findById(category).orElseThrow(CategoryNotFoundException::new));
        entity.setOwner(authorizationBean.getUser().orElseThrow(UnauthorizedException::new));

        offeringDAO.create(entity);

        return new OfferingDTO(entity);
    }

    public OfferingDTO findByIdOrThrow(long id) throws OfferingNotFoundException {
        return offeringDAO.findById(id).map(OfferingDTO::new).orElseThrow(OfferingNotFoundException::new);
    }

    public List<OfferingDTO> findAll() {
        return offeringDAO.findAll().stream().map(OfferingDTO::new).collect(Collectors.toList());
    }

}
