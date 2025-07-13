package com.reedmanit.runaustralia.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "datedone")
    private LocalDate datedone;

    @Column(name = "distance")
    private Float distance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberid")
    private Member memberid;

    @Size(max = 200)
    @Column(name = "type", length = 200)
    private String type;

    @Column(name = "activitytime")
    private Float activitytime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDatedone() {
        return datedone;
    }

    public void setDatedone(LocalDate datedone) {
        this.datedone = datedone;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Member getMemberid() {
        return memberid;
    }

    public void setMemberid(Member memberid) {
        this.memberid = memberid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getActivitytime() {
        return activitytime;
    }

    public void setActivitytime(Float activitytime) {
        this.activitytime = activitytime;
    }

}