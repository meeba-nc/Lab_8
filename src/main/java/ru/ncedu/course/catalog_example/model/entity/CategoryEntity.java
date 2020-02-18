package ru.ncedu.course.catalog_example.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "categories")
public class CategoryEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

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
