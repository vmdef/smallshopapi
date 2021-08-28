package com.amtest.smallshop.api.entity;

import com.amtest.smallshop.api.security.Auditable;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customer")
public class CustomerEntity extends Auditable<String> {
    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "Name is required.")
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "Surname is required.")
    @Basic(optional = false)
    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "PHOTO")
    private String photo;

/*    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Timestamp createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Timestamp modifiedDate;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;*/

    public UUID getId() {
        return id;
    }

    public CustomerEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public CustomerEntity setSurname(String surname) {
        this.surname = surname;
        return this;
    }


    public String getPhoto() {
        return photo;
    }

    public CustomerEntity setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + photo + '\'' +
                '}';
    }

}
