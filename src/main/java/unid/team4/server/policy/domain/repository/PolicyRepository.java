package unid.team4.server.policy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unid.team4.server.policy.domain.Policy;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByAge(Policy.AgeGroup ageGroup);
    List<Policy> findByAgeAndCategory(Policy.AgeGroup age, Policy.Category category);
}
