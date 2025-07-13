package com.reedmanit.runaustralia.service;

import com.reedmanit.runaustralia.data.Member;
import com.reedmanit.runaustralia.data.Progress;
import com.reedmanit.runaustralia.data.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Integer> {
    // Basic paging
    Page<Progress> findAll(Pageable pageable);

    // Find progress by member with eager loading of related entities
    @Query("SELECT p FROM Progress p LEFT JOIN FETCH p.placeid place LEFT JOIN FETCH p.memberid member WHERE p.memberid = :member")
    List<Progress> findByMemberWithDetails(Member member);

    // Find progress by date range
    @Query("SELECT p FROM Progress p WHERE p.datearrived BETWEEN :startDate AND :endDate")
    Page<Progress> findByDateArrivedBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // Find progress by place
    @Query("SELECT p FROM Progress p WHERE p.placeid = :place")
    Page<Progress> findByPlace(Place place, Pageable pageable);

    // Find current progress (where dateleft is null)
    @Query("SELECT p FROM Progress p WHERE p.memberid = :member AND p.dateleft IS NULL")
    Progress findCurrentProgressByMember(Member member);

    // Find progress within geographical bounds
    @Query("SELECT p FROM Progress p WHERE p.latitude BETWEEN :minLat AND :maxLat AND p.longitude BETWEEN :minLong AND :maxLong")
    Page<Progress> findWithinBounds(Float minLat, Float maxLat, Float minLong, Float maxLong, Pageable pageable);
}
