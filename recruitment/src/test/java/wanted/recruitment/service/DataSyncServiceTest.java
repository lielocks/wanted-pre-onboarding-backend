package wanted.recruitment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wanted.recruitment.domain.board.Board;
import wanted.recruitment.domain.board.BoardDocument;
import wanted.recruitment.domain.company.Company;
import wanted.recruitment.repository.board.BoardRepository;
import wanted.recruitment.repository.board.SyncStatusRepository;
import wanted.recruitment.repository.board.document.BoardDocumentRepository;
import wanted.recruitment.repository.company.CompanyRepository;
import wanted.recruitment.service.board.DataSyncService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DataSyncServiceTest {

    @Autowired
    DataSyncService dataSyncService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardDocumentRepository boardDocumentRepository;

    @Autowired
    SyncStatusRepository syncStatusRepository;

    @Autowired
    CompanyRepository companyRepository;

    private Long boardId;

    @BeforeEach
    void dataSetUp() {
        Company company = companyRepository.findById(6L).get();
        Board savedBoard = boardRepository.save(Board.builder().company(company).skill("typescript").position("프론트엔드 개발자")
                .description("프론트엔드 개발자를 구직합니다").reward(10000).build());
        boardId = savedBoard.getId();
    }

    @Test
    @DisplayName("scheuledSave method 호출")
    void testScheduledSave() throws InterruptedException {
        // 검증 위해 지금 시점에서 바로 scehduledSave 메서드 호출
        dataSyncService.scheduledSave();

        // elasticsearch indexing process 조금 기다려주기 위함
        Thread.sleep(5000);

        List<BoardDocument> content = boardDocumentRepository.findAll();
        List<Board> boards = boardRepository.findAll();

        List<Long> boardDocumentIdList = content.stream().map(BoardDocument::getBoardId).toList();
        List<Long> boardIdList = boards.stream().map(Board::getId).toList();

        assertTrue(boardIdList.contains(boardId));
        assertTrue(boardDocumentIdList.contains(boardId));
        assertTrue(boardIdList.containsAll(boardDocumentIdList));
    }
}
