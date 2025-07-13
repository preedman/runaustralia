package com.reedmanit.runaustralia;

import com.reedmanit.runaustralia.data.Member;
import com.reedmanit.runaustralia.service.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Member activeMember;
    private Member inactiveMember;

    @BeforeEach
    void setUp() {
        activeMember = new Member();
        activeMember.setFirstname("John");
        activeMember.setLastname("Doe");
        activeMember.setStatus("ACTIVE");
        activeMember.setJoindate(LocalDate.now().minusMonths(2));
        entityManager.persist(activeMember);

        inactiveMember = new Member();
        inactiveMember.setFirstname("Jane");
        inactiveMember.setLastname("Smith");
        inactiveMember.setStatus("INACTIVE");
        inactiveMember.setJoindate(LocalDate.now().minusMonths(6));
        inactiveMember.setEnddate(LocalDate.now().minusDays(7));
        entityManager.persist(inactiveMember);

        entityManager.flush();
    }

    @Test
    void findAll_ShouldReturnAllMembers() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Member> result = memberRepository.findAll(pageRequest);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).contains(activeMember, inactiveMember);
    }

    @Test
    void findByNameContainingIgnoreCase_ShouldReturnMatchingMembers() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Member> result = memberRepository.findByNameContainingIgnoreCase("jo", pageRequest);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(activeMember);
    }

    @Test
    void findByFirstnameAndLastname_ShouldReturnCorrectMember() {
        Optional<Member> result = memberRepository.findByFirstnameAndLastname("John", "Doe");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(activeMember);
    }

    @Test
    void findActiveMembers_ShouldReturnOnlyActiveMembers() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Member> result = memberRepository.findActiveMembers(pageRequest);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(activeMember);
    }

    @Test
    void findByStatus_ShouldReturnMembersWithMatchingStatus() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Member> result = memberRepository.findByStatus("ACTIVE", pageRequest);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(activeMember);
    }

    @Test
    void countByStatus_ShouldReturnCorrectCount() {
        Long activeCount = memberRepository.countByStatus("ACTIVE");
        Long inactiveCount = memberRepository.countByStatus("INACTIVE");

        assertThat(activeCount).isEqualTo(1L);
        assertThat(inactiveCount).isEqualTo(1L);
    }
}

