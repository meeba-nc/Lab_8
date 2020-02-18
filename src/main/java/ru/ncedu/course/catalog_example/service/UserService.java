package ru.ncedu.course.catalog_example.service;

import ru.ncedu.course.catalog_example.exception.InvalidLoginPasswordException;
import ru.ncedu.course.catalog_example.exception.UserAlreadyExistsException;
import ru.ncedu.course.catalog_example.model.dao.UserDAO;
import ru.ncedu.course.catalog_example.model.dto.UserDTO;
import ru.ncedu.course.catalog_example.model.entity.UserEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Stateless
public class UserService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private AuthorizationBean authorizationBean;

    private String digest(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            digest.update(salt.getBytes());
            return DatatypeConverter.printHexBinary(digest.digest()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO login(String login, String password) throws InvalidLoginPasswordException {
        UserEntity user = userDAO.findByLogin(login);
        if(user == null) {
            throw new InvalidLoginPasswordException();
        }

        if(digest(password, user.getSalt()).equals(user.getPassword())) {
            authorizationBean.setUser(user);
            return new UserDTO(user);
        } else {
            throw new InvalidLoginPasswordException();
        }
    }

    public void logout() {
        authorizationBean.setUser(null);
    }

    public UserDTO create(String login, String password) throws UserAlreadyExistsException {
        if(userDAO.existsByLogin(login)) {
            throw new UserAlreadyExistsException();
        }

        UserEntity user = new UserEntity();
        user.setLogin(login);
        user.setSalt(UUID.randomUUID().toString());
        user.setPassword(digest(password, user.getSalt()));
        userDAO.create(user);
        return new UserDTO(user);
    }

}
