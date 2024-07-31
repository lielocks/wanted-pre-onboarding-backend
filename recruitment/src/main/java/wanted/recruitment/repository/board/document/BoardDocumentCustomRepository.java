package wanted.recruitment.repository.board.document;

import org.springframework.data.domain.Pageable;
import wanted.recruitment.domain.BoardDocument;

import java.util.List;

public interface BoardDocumentCustomRepository {
    List<BoardDocument> searchBoardDocument(String keyword, Pageable pageable);
}
