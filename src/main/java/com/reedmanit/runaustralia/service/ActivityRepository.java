package com.reedmanit.runaustralia.service;

import com.reedmanit.runaustralia.data.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    // Basic paging - doesn't need @Query
//    @Query("SELECT a FROM Activity a")
//    Page<Activity> findAllActivities(Pageable pageable);


    Page<Activity> findAll(Pageable pageable);

    // Example with @Query - for custom query
    @Query("SELECT a FROM Activity a WHERE a.type = :type")
    Page<Activity> findByActivityType(String type, Pageable pageable);

    // Example with join fetch - for loading related entities
    @Query("SELECT a FROM Activity a LEFT JOIN FETCH a.memberid WHERE a.type = :type")
    Page<Activity> findByActivityTypeWithMember(String type, Pageable pageable);


    //@Query("SELECT a FROM Activity a WHERE " +
    //        "(:memberId IS NULL OR a.memberid.id = :memberId) AND " +
    //        "(:startDate IS NULL OR a.datedone >= :startDate) AND " +
    //        "(:endDate IS NULL OR a.datedone <= :endDate)")
    //Page<Activity> findByFilters(@Param("memberId") Integer memberId,
    //                             @Param("startDate") LocalDate startDate,
    //                             @Param("endDate") LocalDate endDate,
    //                             Pageable pageable);

    @Query("SELECT a FROM Activity a WHERE " +
            "(:memberId IS NULL OR a.memberid.id = :memberId) AND " +
            "(CAST(:startDate as date) IS NULL OR a.datedone >= :startDate) AND " +
            "(CAST(:endDate as date) IS NULL OR a.datedone <= :endDate)")
    Page<Activity> findByFilters(@Param("memberId") Integer memberId,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate,
                                 Pageable pageable);



}

