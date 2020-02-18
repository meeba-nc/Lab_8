package ru.ncedu.course.catalog_example.service;

import ru.ncedu.course.catalog_example.model.dao.UserDAO;
import ru.ncedu.course.catalog_example.model.entity.UserEntity;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Optional;

@Stateful
@SessionScoped
public class AuthorizationBean implements Serializable {

    @Inject
    private UserDAO userDAO;

    private Long userId;

    public void setUser(UserEntity user) {
        this.userId = Optional.ofNullable(user).map(UserEntity::getId).orElse(null);
    }

    public Optional<UserEntity> getUser() {
        if(userId == null) {
            return Optional.empty();
        } else {
            return userDAO.findById(userId);
        }
    }

    public boolean isAuthorized() {
        return userId != null;
    }
}
