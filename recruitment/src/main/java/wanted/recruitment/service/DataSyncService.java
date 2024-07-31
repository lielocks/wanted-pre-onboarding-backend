package wanted.recruitment.service;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import wanted.recruitment.common.dto.PageResponse;
import wanted.recruitment.domain.Board;
import wanted.recruitment.domain.BoardDocument;
import wanted.recruitment.repository.board.BoardRepository;
import wanted.recruitment.repository.board.document.BoardDocumentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DataSyncService {

    private final BoardRepository boardRepository;
    private final BoardDocumentRepository boardDocumentRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public void save() {
        List<Board> boards = boardRepository.findAllWithAssociations();

        for (Board board : boards) {
            BoardDocument boardDocument = BoardDocument.from(board);
            elasticsearchRestTemplate.save(boardDocument);
        }
    }

    public List<BoardDocument> searchBoardDocument(String keyword, Pageable pageable) {
        return boardDocumentRepository.searchBoardDocument(keyword, pageable);
    }
}
