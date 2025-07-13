package com.reedmanit.runaustralia.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 200)
    @Column(name = "firstname", length = 200)
    private String firstname;

    @Size(max = 200)
    @Column(name = "lastname", length = 200)
    private String lastname;

    @Column(name = "joindate")
    private LocalDate joindate;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "enddate")
    private LocalDate enddate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getJoindate() {
        return joindate;
    }

    public void setJoindate(LocalDate joindate) {
        this.joindate = joindate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

}