package com.nexus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.Date;

public class UserDTO {
    @NotEmpty(message = "First name is mandatory")
    private String firstName;
    @NotEmpty(message = "Last name is mandatory")
    private String lastName;
    @NotEmpty(message = "Username is mandatory")
    private String username;
    @Email(message = "Enter a valid email")
    private String email;
    @NotEmpty(message = "Address is mandatory")
    private String address;
    @NotEmpty(message = "Role is mandatory")
    private String role;
    @NotEmpty(message = "Password is mandatory")
    private String password;
    private boolean isActive;
    private boolean isLocked;

    public UserDTO() {
    }
    public UserDTO(String firstName, String lastName, String username, String email, String address, String role, String password, boolean isActive, boolean isLocked) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.address = address;
        this.role = role;
        this.password = password;
        this.isActive = isActive;
        this.isLocked = isLocked;
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
