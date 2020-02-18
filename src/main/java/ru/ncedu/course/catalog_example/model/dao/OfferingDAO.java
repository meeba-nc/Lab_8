package ru.ncedu.course.catalog_example.model.dao;

import ru.ncedu.course.catalog_example.model.entity.OfferingEntity;

import javax.ejb.Stateless;

@Stateless
public class OfferingDAO extends AbstractDAO<OfferingEntity, Long> {

    public OfferingDAO() {
        init(OfferingEntity.class);
    }

}
