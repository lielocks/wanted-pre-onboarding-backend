package wanted.recruitment.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wanted.recruitment.domain.Board;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {
    @Query("select b from Board b JOIN FETCH b.company c JOIN FETCH c.location l JOIN FETCH l.country co JOIN FETCH l.city ci")
    List<Board> findAllWithAssociations();
}
