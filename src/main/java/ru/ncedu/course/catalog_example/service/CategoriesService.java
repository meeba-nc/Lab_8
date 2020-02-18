package ru.ncedu.course.catalog_example.service;

import ru.ncedu.course.catalog_example.model.dao.CategoryDAO;
import ru.ncedu.course.catalog_example.model.dto.CategoryDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CategoriesService {

    @Inject
    private CategoryDAO categoryDAO;

    public List<CategoryDTO> findAll() {
        return categoryDAO.findAll().stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

}
