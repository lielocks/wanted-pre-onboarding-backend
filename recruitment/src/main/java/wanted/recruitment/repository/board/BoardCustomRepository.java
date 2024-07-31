package wanted.recruitment.repository.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wanted.recruitment.dto.BoardDetailResponseDto;
import wanted.recruitment.dto.BoardInfoResponseDto;

import java.util.List;

public interface BoardCustomRepository {
    Page<BoardInfoResponseDto> boardInfoResponse(Pageable pageable);
    BoardDetailResponseDto boardDetailResponse(Long boardId, Long companyId);
}
