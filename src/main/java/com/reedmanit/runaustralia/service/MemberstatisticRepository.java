package com.reedmanit.runaustralia.service;

import com.reedmanit.runaustralia.data.Member;
import com.reedmanit.runaustralia.data.Memberstatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberstatisticRepository extends JpaRepository<Memberstatistic, Integer> {

    Page<Memberstatistic> findAll(Pageable pageable);

    @Query("SELECT ms FROM Memberstatistic ms WHERE LOWER(ms.nameofstatistic) LIKE LOWER(concat('%', :name, '%'))")
    Page<Memberstatistic> findByNameContaining(@Param("name") String name, Pageable pageable);

    List<Memberstatistic> findByMemberid(Member member);

    @Query("SELECT ms FROM Memberstatistic ms WHERE ms.memberid = :member AND ms.valueofstatistic >= :minValue")
    List<Memberstatistic> findByMemberAndMinimumValue(@Param("member") Member member, @Param("minValue") Float minValue);

    @Query("SELECT ms FROM Memberstatistic ms JOIN FETCH ms.memberid WHERE ms.valueofstatistic >= :threshold")
    List<Memberstatistic> findStatisticsAboveThreshold(@Param("threshold") Float threshold);

    @Query("SELECT AVG(ms.valueofstatistic) FROM Memberstatistic ms WHERE ms.nameofstatistic = :statisticName")
    Float findAverageValueByName(@Param("statisticName") String statisticName);

   // Long countByNameofstatisticAfterAndValueGreaterThanAndValueGreaterThan(String nameofstatistic, Float valueofstatistic);
}

