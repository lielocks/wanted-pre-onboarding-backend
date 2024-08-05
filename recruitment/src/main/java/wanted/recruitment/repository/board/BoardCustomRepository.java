package wanted.recruitment.repository.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wanted.recruitment.dto.board.BoardDetailResponseDto;
import wanted.recruitment.dto.board.BoardInfoResponseDto;

public interface BoardCustomRepository {
    Page<BoardInfoResponseDto> boardInfoResponse(Pageable pageable);
    BoardDetailResponseDto boardDetailResponse(Long boardId, Long companyId);
}
