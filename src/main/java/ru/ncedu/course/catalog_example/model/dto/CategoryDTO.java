package ru.ncedu.course.catalog_example.model.dto;

import ru.ncedu.course.catalog_example.model.entity.CategoryEntity;

public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO(CategoryEntity category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
