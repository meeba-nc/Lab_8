package ru.ncedu.course.catalog_example.model.dao;

import ru.ncedu.course.catalog_example.model.entity.UserEntity;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class UserDAO extends AbstractDAO<UserEntity, Long> {

    private static final String FIND_BY_LOGIN = "FROM " + UserEntity.class.getName() + " user WHERE user.login = :login";
    private static final String COUNT_BY_LOGIN = "SELECT COUNT(user) FROM " + UserEntity.class.getName() + " user WHERE user.login = :login";

    public UserDAO() {
        init(UserEntity.class);
    }

    public UserEntity findByLogin(String login) {
        Map<String, Object> params = new HashMap<>();
        params.put("login", login);

        return customFindSingleQuery(FIND_BY_LOGIN, params);
    }

    public boolean existsByLogin(String login) {
        Map<String, Object> params = new HashMap<>();
        params.put("login", login);

        return customExistsQuery(COUNT_BY_LOGIN, params);
    }

}
