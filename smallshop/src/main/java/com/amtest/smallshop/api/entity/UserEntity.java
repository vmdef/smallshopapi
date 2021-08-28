package com.amtest.smallshop.api.entity;

import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "Username is required.")
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ADMIN_STATUS")
    private String adminStatus = "ACTIVE";

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.USER;

    public UUID getId() {
        return id;
    }

    public UserEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public UserEntity setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
        return this;
    }

    public RoleEnum getRole() {
        return role;
    }

    public UserEntity setRole(RoleEnum role) {
        this.role = role;
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", adminStatus='" + adminStatus + '\'' +
                ", role=" + role +
                '}';
    }

}
