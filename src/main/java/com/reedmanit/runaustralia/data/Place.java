package com.reedmanit.runaustralia.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 200)
    @Column(name = "name", length = 200)
    private String name;

    @Size(max = 200)
    @Column(name = "reference", length = 200)
    private String reference;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stateid")
    private State stateid;

    @OneToMany(mappedBy = "placeid")
    private Set<Progress> progresses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "placeStart")
    private Set<Route> routestart = new LinkedHashSet<>();

    @OneToMany(mappedBy = "placeEnd")
    private Set<Route> routeend = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public State getStateid() {
        return stateid;
    }

    public void setStateid(State stateid) {
        this.stateid = stateid;
    }

    public Set<Progress> getProgresses() {
        return progresses;
    }

    public void setProgresses(Set<Progress> progresses) {
        this.progresses = progresses;
    }

    public Set<Route> getRoutestart() {
        return routestart;
    }

    public void setRoutestart(Set<Route> routestart) {
        this.routestart = routestart;
    }

    public Set<Route> getRouteend() {
        return routeend;
    }

    public void setRouteend(Set<Route> routeend) {
        this.routeend = routeend;
    }

}