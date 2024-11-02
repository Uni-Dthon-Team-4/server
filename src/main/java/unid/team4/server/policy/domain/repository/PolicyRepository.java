package unid.team4.server.policy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unid.team4.server.policy.domain.Policy;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByAge(Policy.AgeGroup ageGroup);
    List<Policy> findByAgeAndCategory(Policy.AgeGroup age, Policy.Category category);
    @Query("SELECT p FROM Policy p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Policy> findByKeyword(@Param("keyword") String keyword);
    List<Policy> findByIsScraped(boolean isScraped);
}
