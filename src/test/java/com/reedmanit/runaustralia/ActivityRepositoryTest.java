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
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")

public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private MemberRepository memberRepository;

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
        System.out.println("!!!!!!!!!!!!!!!! " + foundActivity.getDescription());

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



}
