package com.nexus.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client extends User{
    @Column(name = "join_date")
    @Temporal(TemporalType.DATE)
    private Date joinDate;

    @OneToMany(mappedBy = "client")
    private Set<Card> pages;
    public Client() {
    }

    public Client(Long id, String firstName, String lastName, String username, String email, String address, String role, String password, Date lastLoginDate, boolean isActive, boolean isLocked, Date joinDate, Set<Card> pages) {
        super(id, firstName, lastName, username, email, address, role, password, lastLoginDate, isActive, isLocked);
        this.joinDate = joinDate;
        this.pages = pages;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Set<Card> getPages() {
        return pages;
    }

    public void setPages(Set<Card> pages) {
        this.pages = pages;
    }
}
