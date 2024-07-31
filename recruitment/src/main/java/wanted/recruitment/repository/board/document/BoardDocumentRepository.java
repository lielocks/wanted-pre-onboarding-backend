package wanted.recruitment.repository.board.document;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import wanted.recruitment.domain.BoardDocument;

public interface BoardDocumentRepository extends ElasticsearchRepository<BoardDocument, Long>, BoardDocumentCustomRepository {
}
