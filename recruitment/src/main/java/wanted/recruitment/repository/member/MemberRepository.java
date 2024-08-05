package wanted.recruitment.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.recruitment.domain.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
