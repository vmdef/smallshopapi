package com.amtest.smallshop.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name="customer")
public class CustomerEntity {
    @Id
    @GeneratedValue
    @Column(name="ID", updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "User name is required.")
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;

    @NotNull(message = "Surname is required.")
    @Basic(optional = false)
    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "photourl")
    private String photourl;

    public Long getId() {
        return id;
    }

    public CustomerEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public CustomerEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public CustomerEntity setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPhotourl() {
        return photourl;
    }

    public CustomerEntity setImageUrl(String photourl) {
        this.photourl = photourl;
        return this;
    }
}
