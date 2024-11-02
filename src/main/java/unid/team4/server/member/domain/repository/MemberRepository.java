package unid.team4.server.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unid.team4.server.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
