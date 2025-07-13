package com.reedmanit.runaustralia.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "memberstatistic")
public class Memberstatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 200)
    @Column(name = "nameofstatistic", length = 200)
    private String nameofstatistic;

    @Column(name = "valueofstatistic")
    private Float valueofstatistic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberid")
    private Member memberid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameofstatistic() {
        return nameofstatistic;
    }

    public void setNameofstatistic(String nameofstatistic) {
        this.nameofstatistic = nameofstatistic;
    }

    public Float getValueofstatistic() {
        return valueofstatistic;
    }

    public void setValueofstatistic(Float valueofstatistic) {
        this.valueofstatistic = valueofstatistic;
    }

    public Member getMemberid() {
        return memberid;
    }

    public void setMemberid(Member memberid) {
        this.memberid = memberid;
    }

}