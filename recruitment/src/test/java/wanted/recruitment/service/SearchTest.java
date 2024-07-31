package wanted.recruitment.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wanted.recruitment.domain.BoardDocument;
import wanted.recruitment.repository.board.BoardRepository;
import wanted.recruitment.repository.board.document.BoardDocumentRepository;

import java.util.List;

@SpringBootTest
public class SearchTest {
    @Autowired
    DataSyncService dataSyncService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardDocumentRepository boardDocumentRepository;

    @Test
    void test() {
        dataSyncService.save();
        Page<BoardDocument> boardDocuments = boardDocumentRepository.findAll(Pageable.ofSize(20));
        List<BoardDocument> content = boardDocuments.getContent();
        content.forEach(c -> System.out.println(c.getBoardId() + c.getCompanyName() + c.getCountryName() + c.getCityName()));
    }

}
