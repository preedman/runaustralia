package com.reedmanit.runaustralia.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_start")
    private Place placeStart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_end")
    private Place placeEnd;

    @Column(name = "distance")
    private Float distance;

    @Size(max = 200)
    @Column(name = "name", length = 200)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Place getPlaceStart() {
        return placeStart;
    }

    public void setPlaceStart(Place placeStart) {
        this.placeStart = placeStart;
    }

    public Place getPlaceEnd() {
        return placeEnd;
    }

    public void setPlaceEnd(Place placeEnd) {
        this.placeEnd = placeEnd;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}