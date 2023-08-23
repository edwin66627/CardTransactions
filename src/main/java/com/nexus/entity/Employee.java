package com.nexus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Employee extends User{
    @Column(name = "hire_date")
    @Temporal(TemporalType.DATE)
    private Date hireDate;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, String username, String email, String address, String role, String password, Date lastLoginDate, boolean isActive, boolean isLocked, Date hireDate) {
        super(id, firstName, lastName, username, email, address, role, password, lastLoginDate, isActive, isLocked);
        this.hireDate = hireDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
}
