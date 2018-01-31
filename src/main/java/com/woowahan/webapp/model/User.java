package com.woowahan.webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {
    @Column(nullable = false, length = 15, unique = true)
    @JsonProperty
    private String userId;
    @JsonProperty
    private String name;
    @JsonIgnore
    private String password;
    @JsonProperty
    private String email;

    public User() {

    }

    public User(String userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public boolean matchId(Long id) {
        if (id == null) {
            return false;
        }

        return id.equals(getId());
    }

    public boolean matchPassword(String newPassword) {
        if (newPassword == null) {
            return false;
        }

        return newPassword.equals(this.password);
    }

    public boolean match(User newUser) {
        if (newUser == null) {
            return false;
        }

        return userId.equals(newUser.userId) && password.equals(newUser.password);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
