package ru.ncedu.course.catalog_example.model.dto;

import ru.ncedu.course.catalog_example.model.entity.UserEntity;

public class UserDTO {

    private Long id;
    private String login;

    public UserDTO(UserEntity entity) {
        this.id = entity.getId();
        this.login = entity.getLogin();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
