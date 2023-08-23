package com.nexus.entity;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String address;
    private String role;
    private String password;
    @Column(name = "last_login_date")
    @Temporal(TemporalType.DATE)
    private Date lastLoginDate;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "is_locked")
    private boolean isLocked;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String username, String email, String address, String role, String password, Date lastLoginDate, boolean isActive, boolean isLocked) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.address = address;
        this.role = role;
        this.password = password;
        this.lastLoginDate = lastLoginDate;
        this.isActive = isActive;
        this.isLocked = isLocked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
