package wanted.recruitment.repository.board.document;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import wanted.recruitment.domain.board.BoardDocument;
import wanted.recruitment.dto.board.BoardDocumentResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BoardDocumentCustomRepositoryImpl implements BoardDocumentCustomRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<BoardDocumentResponseDto> searchBoardDocument(String keyword) {
        Criteria criteria = new Criteria()
                .or("companyName").contains(keyword)
                .or("position").contains(keyword)
                .or("skill").contains(keyword);
        Query query = new CriteriaQuery(criteria);

        SearchHits<BoardDocument> search = elasticsearchOperations.search(query, BoardDocument.class);
        return search.stream()
                .map(hit -> {
                    BoardDocument document = hit.getContent();
                    return BoardDocumentResponseDto.builder()
                            .boardId(document.getBoardId())
                            .companyName(document.getCompanyName())
                            .countryName(document.getCountryName())
                            .cityName(document.getCityName())
                            .position(document.getPosition())
                            .reward(document.getReward())
                            .skill(document.getSkill()).build();
                })
                .toList();
    }

    @Override
    public List<BoardDocument> findAll() {
        Query query = new CriteriaQuery(Criteria.where("id").exists());
        SearchHits<BoardDocument> searchHits = elasticsearchOperations.search(query, BoardDocument.class);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
