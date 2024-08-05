package wanted.recruitment.repository.board.document;

import wanted.recruitment.domain.BoardDocument;
import wanted.recruitment.dto.BoardDocumentResponseDto;

import java.util.List;

public interface BoardDocumentCustomRepository {
    List<BoardDocumentResponseDto> searchBoardDocument(String keyword);
    List<BoardDocument> findAll();
}
