package ru.ncedu.course.catalog_example.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comments")
public class CommentEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "text")
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date date;

    @ManyToOne
    @JoinColumn(name = "author_user_id", nullable = false)
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "offering_id", nullable = false)
    private OfferingEntity offering;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public OfferingEntity getOffering() {
        return offering;
    }

    public void setOffering(OfferingEntity offering) {
        this.offering = offering;
    }

}
