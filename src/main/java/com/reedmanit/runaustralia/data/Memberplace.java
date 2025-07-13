package com.reedmanit.runaustralia.data;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "memberplace")
public class Memberplace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "datearrived")
    private LocalDate datearrived;

    @Column(name = "dateleft")
    private LocalDate dateleft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placeid")
    private Place placeid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberid")
    private Member memberid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDatearrived() {
        return datearrived;
    }

    public void setDatearrived(LocalDate datearrived) {
        this.datearrived = datearrived;
    }

    public LocalDate getDateleft() {
        return dateleft;
    }

    public void setDateleft(LocalDate dateleft) {
        this.dateleft = dateleft;
    }

    public Place getPlaceid() {
        return placeid;
    }

    public void setPlaceid(Place placeid) {
        this.placeid = placeid;
    }

    public Member getMemberid() {
        return memberid;
    }

    public void setMemberid(Member memberid) {
        this.memberid = memberid;
    }

}