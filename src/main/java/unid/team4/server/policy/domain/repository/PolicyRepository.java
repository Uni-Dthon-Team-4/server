package unid.team4.server.policy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unid.team4.server.policy.domain.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
