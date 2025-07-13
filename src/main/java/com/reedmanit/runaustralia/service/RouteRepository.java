package com.reedmanit.runaustralia.service;

import com.reedmanit.runaustralia.data.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    // Basic paging
    Page<Route> findAll(Pageable pageable);

    // Find routes by name (partial match)
    @Query("SELECT r FROM Route r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Route> findByNameContaining(String name, Pageable pageable);

    // Find routes with distance greater than or equal to
    @Query("SELECT r FROM Route r WHERE r.distance >= :distance")
    Page<Route> findByDistanceGreaterThanEqual(Float distance, Pageable pageable);

    // Find routes with places (eager loading)
    @Query("SELECT r FROM Route r LEFT JOIN FETCH r.placeStart ps LEFT JOIN FETCH r.placeEnd pe")
    Page<Route> findAllWithPlaces(Pageable pageable);
}
