package wanted.recruitment.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.recruitment.domain.board.SyncStatus;

@Repository
public interface SyncStatusRepository extends JpaRepository<SyncStatus, Long> {
}
