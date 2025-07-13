package com.reedmanit.runaustralia.data;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "progress")
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "datearrived")
    private LocalDate datearrived;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placeid")
    private Place placeid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberid")
    private Member memberid;

    @Column(name = "dateleft")
    private LocalDate dateleft;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public LocalDate getDatearrived() {
        return datearrived;
    }

    public void setDatearrived(LocalDate datearrived) {
        this.datearrived = datearrived;
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

    public LocalDate getDateleft() {
        return dateleft;
    }

    public void setDateleft(LocalDate dateleft) {
        this.dateleft = dateleft;
    }

}