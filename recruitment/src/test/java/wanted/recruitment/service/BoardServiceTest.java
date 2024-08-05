package wanted.recruitment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import wanted.recruitment.common.config.QueryDslConfig;
import wanted.recruitment.common.exception.CustomException;
import wanted.recruitment.domain.board.Board;
import wanted.recruitment.dto.board.BoardRegisterRequestDto;
import wanted.recruitment.dto.board.BoardUpdateRequestDto;
import wanted.recruitment.repository.board.BoardRepository;
import wanted.recruitment.repository.company.CompanyRepository;
import wanted.recruitment.service.board.BoardService;

import static org.junit.jupiter.api.Assertions.*;
import static wanted.recruitment.common.exception.CustomError.BOARD_NOT_EXIST;
import static wanted.recruitment.common.exception.CustomError.COMPANY_NOT_ALLOWED;

@DataJpaTest // for data rollback <- @transactional
@Import({BoardServiceTest.TestConfig.class, QueryDslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CompanyRepository companyRepository;

    private Long companyId;

    @BeforeEach
    void setUp() {
        companyId = 6L;
    }

    @Test
    @DisplayName("register board")
    void registerBoard() {
        BoardRegisterRequestDto registerRequestDto =
                new BoardRegisterRequestDto(companyId, "백엔드 주니어 개발자", 500000, "주니어 백엔드 개발자를 모집합니다.", "spring");
        Long registeredBoardId = boardService.register(registerRequestDto);
        Board board = boardRepository.findById(registeredBoardId).orElseThrow();

        assertEquals(companyId, board.getCompany().getId());
        assertEquals("백엔드 주니어 개발자", board.getPosition());
        assertEquals(500000, board.getReward());
        assertEquals("주니어 백엔드 개발자를 모집합니다.", board.getDescription());
        assertEquals("spring", board.getSkill());
    }

    @Test
    @DisplayName("register board throws COMPANY_NOT_ALLOWED error")
    void registerBoardThrowsErrorWhenBoardDoesNotMatchCompany() {
        Long invalidCompanyId = -1111L;
        BoardRegisterRequestDto invalidCompanyIdRegisterRequestDto =
                new BoardRegisterRequestDto(invalidCompanyId, "백엔드 주니어 개발자", 500000, "주니어 백엔드 개발자를 모집합니다.", "spring");
        CustomException exception = assertThrows(CustomException.class, () -> {
            boardService.register( invalidCompanyIdRegisterRequestDto);
        });
        assertEquals(exception.getCustomError(), COMPANY_NOT_ALLOWED);
    }

    @Test
    @DisplayName("update board with partial fields")
    void updateBoardWithPartialFields() {
        BoardRegisterRequestDto registerRequestDto =
                new BoardRegisterRequestDto(companyId, "백엔드 주니어 개발자", 500000, "주니어 백엔드 개발자를 모집합니다.", "spring");
        Long registeredBoardId = boardService.register(registerRequestDto);
        Board board = boardRepository.findById(registeredBoardId).orElseThrow();

        BoardUpdateRequestDto boardUpdateRequestDto = new BoardUpdateRequestDto();
        boardUpdateRequestDto.setBoardId(board.getId());
        boardUpdateRequestDto.setPosition("시니어 프론트엔드 개발자");
        boardUpdateRequestDto.setSkill("react");
        boardUpdateRequestDto.setDescription("시니어 프론트엔드 개발자를 모집합니다.");
        boardService.update(boardUpdateRequestDto, companyId);

        Board updatedBoard = boardRepository.findById(board.getId()).orElseThrow();
        assertEquals(board.getId(), updatedBoard.getId());
        assertEquals("시니어 프론트엔드 개발자", updatedBoard.getPosition());
        assertEquals("react", updatedBoard.getSkill());
        assertEquals("시니어 프론트엔드 개발자를 모집합니다.", updatedBoard.getDescription());
    }

    @Test
    @DisplayName("update board throws BOARD_NOT_EXIST error")
    void updateBoardThrowsErrorWhenBoardNotExist() {
        BoardUpdateRequestDto boardUpdateRequestDto = new BoardUpdateRequestDto();
        boardUpdateRequestDto.setBoardId(-1111L); // 존재하지 않는 boardId
        boardUpdateRequestDto.setPosition("시니어 프론트엔드 개발자");

        CustomException exception = assertThrows(CustomException.class, () -> {
            boardService.update(boardUpdateRequestDto, companyId);
        });

        assertEquals(BOARD_NOT_EXIST, exception.getCustomError());
    }

    @Test
    @DisplayName("update board throws COMPANY_NOT_ALLOWED error")
    void updateBoardThrowsErrorWhenBoardNotMatchCompany() {
        BoardUpdateRequestDto boardUpdateRequestDto = new BoardUpdateRequestDto();
        boardUpdateRequestDto.setBoardId(35L);
        boardUpdateRequestDto.setPosition("시니어 프론트엔드 개발자");

        CustomException exception = assertThrows(CustomException.class, () -> {
            boardService.update(boardUpdateRequestDto, -1111L); // 존재하지 않는 companyId
        });

        assertEquals(COMPANY_NOT_ALLOWED, exception.getCustomError());
    }

    @Test
    @DisplayName("delete board")
    void deleteBoard() {
        BoardRegisterRequestDto registerRequestDto =
                new BoardRegisterRequestDto(companyId, "백엔드 주니어 개발자", 500000, "주니어 백엔드 개발자를 모집합니다.", "spring");

        Long registeredBoardId = boardService.register(registerRequestDto);
        Board board = boardRepository.findById(registeredBoardId).orElseThrow();

        boardService.delete(board.getId(), companyId);
        assertFalse(boardRepository.findById(board.getId()).isPresent());
    }

    @Test
    @DisplayName("delete board throws BOARD_NOT_EXIST error")
    void deleteBoardThrowsErrorWithInvalidBoardId() {
        Long invalidBoardId = -1111L;

        CustomException exception = assertThrows(CustomException.class, () -> {
            boardService.delete(invalidBoardId, companyId);
        });

        assertEquals(BOARD_NOT_EXIST, exception.getCustomError());
    }

    @Test
    @DisplayName("delete board throws COMPANY_NOT_ALLOWED error")
    void deleteBoardThrowsErrorWithInvalidCompanyId() {
        Long invalidCompanyId = -1111L;

        CustomException exception = assertThrows(CustomException.class, () -> {
            boardService.delete(35L, invalidCompanyId);
        });

        assertEquals(COMPANY_NOT_ALLOWED, exception.getCustomError());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BoardService boardService(CompanyRepository companyRepository, BoardRepository boardRepository) {
            return new BoardService(companyRepository, boardRepository);
        }
    }

}
