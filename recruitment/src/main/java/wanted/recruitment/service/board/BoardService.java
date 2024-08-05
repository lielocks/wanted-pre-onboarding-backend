package wanted.recruitment.service.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.recruitment.common.dto.PageResponse;
import wanted.recruitment.common.exception.CustomException;
import wanted.recruitment.domain.board.Board;
import wanted.recruitment.domain.company.Company;
import wanted.recruitment.dto.board.BoardDetailResponseDto;
import wanted.recruitment.dto.board.BoardInfoResponseDto;
import wanted.recruitment.dto.board.BoardRegisterRequestDto;
import wanted.recruitment.dto.board.BoardUpdateRequestDto;
import wanted.recruitment.repository.board.BoardRepository;
import wanted.recruitment.repository.company.CompanyRepository;

import static wanted.recruitment.common.exception.CustomError.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final CompanyRepository companyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long register(BoardRegisterRequestDto requestDto) {
        Company company = companyRepository.findById(requestDto.getCompanyId())
                .orElseThrow(() -> new CustomException(COMPANY_NOT_ALLOWED));

        Board savedBoard = boardRepository.save(Board.builder().company(company).position(requestDto.getPosition())
                .description(requestDto.getDescription()).reward(requestDto.getReward())
                .skill(requestDto.getSkill()).build());
        return savedBoard.getId();
    }

    @Transactional
    public void update(BoardUpdateRequestDto requestDto, Long companyId) {
        Board board = boardRepository.findById(requestDto.getBoardId())
                .orElseThrow(() -> new CustomException(BOARD_NOT_EXIST));

        if (!board.getCompany().getId().equals(companyId)) {
            throw new CustomException(COMPANY_NOT_ALLOWED);
        }

        if (requestDto.getPosition() != null) {
            board.setPosition(requestDto.getPosition());
        }
        if (requestDto.getReward() != null) {
            board.setReward(requestDto.getReward());
        }
        if (requestDto.getDescription() != null) {
            board.setDescription(requestDto.getDescription());
        }
        if (requestDto.getSkill() != null) {
            board.setSkill(requestDto.getSkill());
        }

        boardRepository.save(board);
    }

    @Transactional
    public void delete(Long boardId, Long companyId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(BOARD_NOT_EXIST));

        if (!board.getCompany().getId().equals(companyId)) {
            throw new CustomException(COMPANY_NOT_ALLOWED);
        }
        boardRepository.delete(board);
    }

    public PageResponse<BoardInfoResponseDto> getBoardList(Pageable pageable) {
         return new PageResponse<>(boardRepository.boardInfoResponse(pageable));
    }

    public BoardDetailResponseDto getBoardDetail(Long boardId, Long companyId) {
        return boardRepository.boardDetailResponse(boardId, companyId);
    }
}
