package com.reedmanit.runaustralia;

import com.reedmanit.runaustralia.data.Member;
import com.reedmanit.runaustralia.data.Memberstatistic;
import com.reedmanit.runaustralia.service.MemberstatisticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class MemberstatisticRepositoryTest {

    @Autowired
    private MemberstatisticRepository memberstatisticRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Member testMember;
    private Memberstatistic statistic1;
    private Memberstatistic statistic2;

    @BeforeEach
    void setUp() {
        testMember = new Member();
        testMember.setFirstname("John");
        testMember.setLastname("Doe");
        entityManager.persist(testMember);

        statistic1 = new Memberstatistic();
        statistic1.setNameofstatistic("Distance");
        statistic1.setValueofstatistic(100.0f);
        statistic1.setMemberid(testMember);
        entityManager.persist(statistic1);

        statistic2 = new Memberstatistic();
        statistic2.setNameofstatistic("Time");
        statistic2.setValueofstatistic(50.0f);
        statistic2.setMemberid(testMember);
        entityManager.persist(statistic2);

        entityManager.flush();
    }

    @Test
    void findAll_ShouldReturnAllStatistics() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Memberstatistic> result = memberstatisticRepository.findAll(pageRequest);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).contains(statistic1, statistic2);
    }

    @Test
    void findByNameContaining_ShouldReturnMatchingStatistics() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Memberstatistic> result = memberstatisticRepository.findByNameContaining("Dist", pageRequest);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(statistic1);
    }

    @Test
    void findByMemberid_ShouldReturnMemberStatistics() {
        List<Memberstatistic> result = memberstatisticRepository.findByMemberid(testMember);

        assertThat(result).hasSize(2);
        assertThat(result).contains(statistic1, statistic2);
    }

    @Test
    void findByMemberAndMinimumValue_ShouldReturnFilteredStatistics() {
        List<Memberstatistic> result = memberstatisticRepository.findByMemberAndMinimumValue(testMember, 75.0f);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(statistic1);
    }

    @Test
    void findStatisticsAboveThreshold_ShouldReturnFilteredStatistics() {
        List<Memberstatistic> result = memberstatisticRepository.findStatisticsAboveThreshold(75.0f);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(statistic1);
    }

    @Test
    void findAverageValueByName_ShouldReturnCorrectAverage() {
        Float result = memberstatisticRepository.findAverageValueByName("Distance");

        assertThat(result).isEqualTo(100.0f);
    }

 //   @Test
 //   void countByNameAndValueGreaterThan_ShouldReturnCorrectCount() {
 //       Long result = memberstatisticRepository.countByNameAndValueGreaterThan("Distance", 75.0f);

  //      assertThat(result).isEqualTo(1L);
   // }
}

