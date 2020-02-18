package ru.ncedu.course.catalog_example.model.dao;

import ru.ncedu.course.catalog_example.model.entity.CategoryEntity;

import javax.ejb.Stateless;

@Stateless
public class CategoryDAO extends AbstractDAO<CategoryEntity, Long> {

    public CategoryDAO() {
        init(CategoryEntity.class);
    }

}
