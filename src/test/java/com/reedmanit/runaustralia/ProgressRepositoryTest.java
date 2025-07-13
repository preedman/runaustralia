package com.reedmanit.runaustralia;

import com.reedmanit.runaustralia.data.Member;
import com.reedmanit.runaustralia.data.Progress;
import com.reedmanit.runaustralia.data.Place;
import com.reedmanit.runaustralia.service.ProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProgressRepositoryTest {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Member testMember;
    private Place testPlace1;
    private Place testPlace2;
    private Progress progress1;
    private Progress progress2;

    @BeforeEach
    void setUp() {
        // Create and persist test member
        testMember = new Member();
        testMember.setFirstname("Test");
        testMember.setLastname("Member");
        testMember = entityManager.persist(testMember);

        // Create and persist test places
        testPlace1 = new Place();
        testPlace1.setName("Sydney");
        testPlace1.setLatitude(-33.8688f);
        testPlace1.setLongitude(151.2093f);
        testPlace1 = entityManager.persist(testPlace1);

        testPlace2 = new Place();
        testPlace2.setName("Melbourne");
        testPlace2.setLatitude(-37.8136f);
        testPlace2.setLongitude(144.9631f);
        testPlace2 = entityManager.persist(testPlace2);

        // Create and persist test progress records
        progress1 = new Progress();
        progress1.setMemberid(testMember);
        progress1.setPlaceid(testPlace1);
        progress1.setLatitude(-33.8688f);
        progress1.setLongitude(151.2093f);
        progress1.setDatearrived(LocalDate.now().minusDays(5));
        progress1.setDateleft(LocalDate.now().minusDays(2));
        progress1 = entityManager.persist(progress1);

        progress2 = new Progress();
        progress2.setMemberid(testMember);
        progress2.setPlaceid(testPlace2);
        progress2.setLatitude(-37.8136f);
        progress2.setLongitude(144.9631f);
        progress2.setDatearrived(LocalDate.now().minusDays(1));
        progress2 = entityManager.persist(progress2);

        entityManager.flush();
    }

    @Test
    void findAll_ShouldReturnAllProgress() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // When
        Page<Progress> progressPage = progressRepository.findAll(pageRequest);

        // Then
        assertThat(progressPage.getContent()).hasSize(2);
        assertThat(progressPage.getContent()).contains(progress1, progress2);
    }

    @Test
    void findByMemberWithDetails_ShouldReturnMemberProgress() {
        // When
        List<Progress> progressList = progressRepository.findByMemberWithDetails(testMember);

        // Then
        assertThat(progressList).hasSize(2);
        assertThat(progressList).contains(progress1, progress2);
        // Verify eager loading
        assertThat(progressList.get(0).getPlaceid().getName()).isNotNull();
        assertThat(progressList.get(0).getMemberid().getFirstname()).isNotNull();
    }

    @Test
    void findByDateArrivedBetween_ShouldReturnProgressInDateRange() {
        // Given
        LocalDate startDate = LocalDate.now().minusDays(6);
        LocalDate endDate = LocalDate.now();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // When
        Page<Progress> progressPage = progressRepository.findByDateArrivedBetween(startDate, endDate, pageRequest);

        // Then
        assertThat(progressPage.getContent()).hasSize(2);
        assertThat(progressPage.getContent()).contains(progress1, progress2);
    }

    @Test
    void findByPlace_ShouldReturnProgressForPlace() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // When
        Page<Progress> progressPage = progressRepository.findByPlace(testPlace1, pageRequest);

        // Then
        assertThat(progressPage.getContent()).hasSize(1);
        assertThat(progressPage.getContent().get(0)).isEqualTo(progress1);
    }

    @Test
    void findCurrentProgressByMember_ShouldReturnCurrentProgress() {
        // When
        Progress currentProgress = progressRepository.findCurrentProgressByMember(testMember);

        // Then
        assertThat(currentProgress).isNotNull();
        assertThat(currentProgress).isEqualTo(progress2);
        assertThat(currentProgress.getDateleft()).isNull();
    }

    @Test
    void findWithinBounds_ShouldReturnProgressInGeographicalBounds() {
        // Given
        Float minLat = -38.0f;
        Float maxLat = -37.0f;
        Float minLong = 144.0f;
        Float maxLong = 145.0f;
        PageRequest pageRequest = PageRequest.of(0, 10);

        // When
        Page<Progress> progressPage = progressRepository.findWithinBounds(minLat, maxLat, minLong, maxLong, pageRequest);

        // Then
        assertThat(progressPage.getContent()).hasSize(1);
        assertThat(progressPage.getContent().get(0)).isEqualTo(progress2);
    }

    @Test
    void saveProgress_ShouldPersistProgress() {
        // Given
        Progress newProgress = new Progress();
        newProgress.setMemberid(testMember);
        newProgress.setPlaceid(testPlace1);
        newProgress.setLatitude(-33.8688f);
        newProgress.setLongitude(151.2093f);
        newProgress.setDatearrived(LocalDate.now());

        // When
        Progress savedProgress = progressRepository.save(newProgress);

        // Then
        assertThat(savedProgress).isNotNull();
        assertThat(savedProgress.getId()).isNotNull();
        assertThat(savedProgress.getMemberid()).isEqualTo(testMember);
        assertThat(savedProgress.getPlaceid()).isEqualTo(testPlace1);
    }
}


