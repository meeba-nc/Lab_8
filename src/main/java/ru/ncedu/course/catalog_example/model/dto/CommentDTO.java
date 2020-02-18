package ru.ncedu.course.catalog_example.model.dto;

import ru.ncedu.course.catalog_example.model.entity.CommentEntity;

import java.util.Date;

public class CommentDTO {

    private Long id;
    private Date date;
    private String message;
    private UserDTO author;

    public CommentDTO(CommentEntity entity) {
        this.id = entity.getId();
        this.date = entity.getDate();
        this.message = entity.getMessage();
        this.author = new UserDTO(entity.getAuthor());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

}
