package wanted.recruitment.repository.board.document;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import wanted.recruitment.domain.BoardDocument;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BoardDocumentCustomRepositoryImpl implements BoardDocumentCustomRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<BoardDocument> searchBoardDocument(String keyword, Pageable pageable) {
        Criteria criteria = new Criteria()
                .or("companyName").contains(keyword)
                .or("position").contains(keyword)
                .or("skill").contains(keyword);
        Query query = new CriteriaQuery(criteria).setPageable(pageable);

        SearchHits<BoardDocument> search = elasticsearchOperations.search(query, BoardDocument.class);
        return search.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
