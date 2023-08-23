package com.nexus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Client extends User{
    @Column(name = "join_date")
    @Temporal(TemporalType.DATE)
    private Date joinDate;

    public Client() {
    }

    public Client(Long id, String firstName, String lastName, String username, String email, String address, String role, String password, Date lastLoginDate, boolean isActive, boolean isLocked, Date joinDate) {
        super(id, firstName, lastName, username, email, address, role, password, lastLoginDate, isActive, isLocked);
        this.joinDate = joinDate;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}
