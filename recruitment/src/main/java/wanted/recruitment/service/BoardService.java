package wanted.recruitment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.recruitment.common.exception.CustomException;
import wanted.recruitment.domain.Board;
import wanted.recruitment.domain.Company;
import wanted.recruitment.dto.*;
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
    public void register(BoardRegisterRequestDto requestDto) {
        Company company = companyRepository.findById(requestDto.getCompanyId())
                .orElseThrow(() -> new CustomException(COMPANY_NOT_ALLOWED));

        boardRepository.save(Board.builder().company(company).position(requestDto.getPosition())
                .description(requestDto.getDescription()).reward(requestDto.getReward()).skill(requestDto.getSkill()).build());
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

    public Page<BoardInfoResponseDto> getBoardList(Pageable pageable) {
         return boardRepository.boardInfoResponse(pageable);
    }

    public BoardDetailResponseDto getBoardDetail(Long boardId, Long companyId) {
        return boardRepository.boardDetailResponse(boardId, companyId);
    }
}
