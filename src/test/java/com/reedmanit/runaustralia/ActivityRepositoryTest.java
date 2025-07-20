package com.reedmanit.runaustralia;

import com.reedmanit.runaustralia.data.Activity;
import com.reedmanit.runaustralia.data.Member;
import com.reedmanit.runaustralia.service.ActivityRepository;
import com.reedmanit.runaustralia.service.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")

public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private MemberRepository memberRepository;

    private void createTestDataforFiltering() {
        Member member1 = new Member();
        member1.setFirstname("Joy");
        member1.setLastname("Doe");
        member1.setJoindate(LocalDate.now());
        member1.setStatus("Active");
        memberRepository.save(member1);



        Member member2 = new Member();
        member2.setFirstname("Jane");
        member2.setLastname("Smith");
        member2.setJoindate(LocalDate.now());
        member2.setStatus("Active");
        memberRepository.save(member2);

        Activity activity1 = new Activity();
        activity1.setMemberid(member1);
        activity1.setDatedone(LocalDate.of(2025, 1, 1));
        activity1.setType("Running");

        Activity activity2 = new Activity();
        activity2.setMemberid(member1);
        activity2.setDatedone(LocalDate.of(2025, 2, 1));
        activity2.setType("Walking");

        Activity activity3 = new Activity();
        activity3.setMemberid(member2);
        activity3.setDatedone(LocalDate.of(2025, 3, 1));
        activity3.setType("Running");

        activityRepository.saveAll(List.of(activity1, activity2, activity3));

    }

    private Activity createSampleActivity() {
        Activity activity = new Activity();
        activity.setDescription("Test Run");
        activity.setDatedone(LocalDate.now());
        activity.setDistance(5.0f);
        activity.setType("Running");
        activity.setActivitytime(30.0f);
        activity.setMemberid(createSampleMember());
        return activity;
    }

    private Member createSampleMember() {
        Member member = new Member();
        member.setFirstname("John");
        member.setLastname("Doe");
        member.setJoindate(LocalDate.now());
        member.setStatus("Active");
        memberRepository.save(member);
        return member;
    }

    @Test
    void shouldSaveActivity() {
        // given
        Activity activity = createSampleActivity();

        // when
        Activity savedActivity = activityRepository.save(activity);

        // then
        assertThat(savedActivity.getId()).isNotNull();
        assertThat(savedActivity.getDescription()).isEqualTo("Test Run");
        assertThat(savedActivity.getType()).isEqualTo("Running");
    }

    @Test
    void shouldFindActivityById() {
        // given
        Activity activity = createSampleActivity();
        activity = activityRepository.save(activity);

        // when
        Activity foundActivity = activityRepository.findById(activity.getId()).orElse(null);

        // then


        assertThat(foundActivity).isNotNull();
        assertThat(foundActivity.getDescription()).isEqualTo("Test Run");
    }

    @Test
    void shouldFindAllActivitiesWithPaging() {
        // given
        for (int i = 0; i < 20; i++) {
            Activity activity = createSampleActivity();
            activity.setDescription("Test Run " + i);
            activityRepository.save(activity);
        }

        // when
        Pageable pageable = PageRequest.of(0, 10); // first page, 10 items
        Page<Activity> activityPage = activityRepository.findAll(pageable);

        // then
        assertThat(activityPage).isNotNull();
        assertThat(activityPage.getContent()).hasSize(10);
        assertThat(activityPage.getTotalElements()).isEqualTo(20);
        assertThat(activityPage.getTotalPages()).isEqualTo(2);
    }

    @Test
    void shouldDeleteActivity() {
        // given
        Activity activity = createSampleActivity();
        activity = activityRepository.save(activity);
        Integer id = activity.getId();

        // when
        activityRepository.deleteById(id);

        // then
        assertThat(activityRepository.findById(id)).isEmpty();
    }

    @Test
    void shouldUpdateActivity() {
        // given
        Activity activity = createSampleActivity();
        activity = activityRepository.save(activity);

        // when
        activity.setDescription("Updated Run");
        Activity updatedActivity = activityRepository.save(activity);

        // then
        assertThat(updatedActivity.getDescription()).isEqualTo("Updated Run");
    }

    @Test
    void findByFilters_WithMemberId_ShouldReturnMemberActivities() {

        createTestDataforFiltering();  // set up for filtering tests
        // Arrange
        Optional<Member> memberResult = memberRepository.findByFirstnameAndLastname("Joy", "Doe");
        Integer memberId = memberResult.get().getId();
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Activity> result = activityRepository.findByFilters(
                memberId,
                null,
                null,
                pageable
        );

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent())
                .isNotEmpty()
                .allMatch(activity -> activity.getMemberid().getId().equals(memberId));
    }

    @Test
    void findByFilters_WithDateRange_ShouldReturnActivitiesInRange() {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 2, 1);
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Activity> result = activityRepository.findByFilters(
                null,
                startDate,
                endDate,
                pageable
        );

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent())
                .isNotEmpty()
                .allMatch(activity ->
                        !activity.getDatedone().isBefore(startDate) &&
                                !activity.getDatedone().isAfter(endDate)
                );
    }

    @Test
    void findByFilters_WithAllParameters_ShouldReturnFilteredActivities() {
        // Arrange
        Integer memberId = 1;
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Activity> result = activityRepository.findByFilters(
                memberId,
                startDate,
                endDate,
                pageable
        );

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent())
                .isNotEmpty()
                .allMatch(activity ->
                        activity.getMemberid().getId().equals(memberId) &&
                                !activity.getDatedone().isBefore(startDate) &&
                                !activity.getDatedone().isAfter(endDate)
                );
    }

    @Test
    void findByFilters_WithNoFilters_ShouldReturnAllActivities() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Activity> result = activityRepository.findByFilters(
                null,
                null,
                null,
                pageable
        );

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(3);
    }






}
