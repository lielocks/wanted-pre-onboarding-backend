package wanted.recruitment.repository.board.document;

import wanted.recruitment.domain.board.BoardDocument;
import wanted.recruitment.dto.board.BoardDocumentResponseDto;

import java.util.List;

public interface BoardDocumentCustomRepository {
    List<BoardDocumentResponseDto> searchBoardDocument(String keyword);
    List<BoardDocument> findAll();
}
