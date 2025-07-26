package com.reedmanit.runaustralia.service;

import com.reedmanit.runaustralia.data.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Page<Member> findAll(Pageable pageable);

    @Query("SELECT m FROM Member m WHERE LOWER(m.firstname) LIKE LOWER(concat('%', :name, '%')) " +
            "OR LOWER(m.lastname) LIKE LOWER(concat('%', :name, '%'))")
    Page<Member> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    Optional<Member> findByFirstnameAndLastname(String firstname, String lastname);

    @Query("SELECT m FROM Member m WHERE m.enddate IS NULL")
    Page<Member> findActiveMembers(Pageable pageable);

    Page<Member> findByStatus(String status, Pageable pageable);

    Page<Member> findByJoindateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

  //  @Query("SELECT m FROM Member m JOIN Activity a WHERE a.datedone >= :since ORDER BY a.datedone DESC")
  //  Page<Member> findMembersWithRecentActivities(@Param("since") LocalDate since, Pageable pageable);

  //  @Query("SELECT DISTINCT m FROM Member m JOIN Activity a ON m.id = a.memberid.id WHERE a.datedone >= :since ORDER BY a.datedone DESC")
  //  Page<Member> findMembersWithRecentActivities(@Param("since") LocalDate since, Pageable pageable);

    @Query("SELECT DISTINCT m FROM Member m JOIN Activity a ON m.id = a.memberid.id WHERE a.datedone >= :since ORDER BY m.id")
    Page<Member> findMembersWithRecentActivities(@Param("since") LocalDate since, Pageable pageable);


    Long countByStatus(String status);
}

