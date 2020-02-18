package ru.ncedu.course.catalog_example.model.dto;

import ru.ncedu.course.catalog_example.model.entity.OfferingEntity;

public class OfferingDTO {

    private Long id;
    private String name;
    private long price;
    private CategoryDTO category;
    private UserDTO owner;

    public OfferingDTO(OfferingEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.category = new CategoryDTO(entity.getCategory());
        this.owner = new UserDTO(entity.getOwner());
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

}
