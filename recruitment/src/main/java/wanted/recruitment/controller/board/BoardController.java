package wanted.recruitment.controller.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wanted.recruitment.common.dto.PageResponse;
import wanted.recruitment.dto.board.BoardDetailResponseDto;
import wanted.recruitment.dto.board.BoardInfoResponseDto;
import wanted.recruitment.dto.board.BoardRegisterRequestDto;
import wanted.recruitment.dto.board.BoardUpdateRequestDto;
import wanted.recruitment.service.board.BoardService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/board")
public class BoardController {

    private final BoardService boardService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public Long register(@RequestBody BoardRegisterRequestDto requestDto) {
        return boardService.register(requestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update")
    public void update(@RequestHeader Long companyId, @RequestBody BoardUpdateRequestDto requestDto) {
        boardService.update(requestDto, companyId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{boardId}")
    public void delete(@RequestHeader Long companyId, @PathVariable Long boardId) {
        boardService.delete(boardId, companyId);
    }

    @GetMapping("/list")
    public PageResponse<BoardInfoResponseDto> boardList(Pageable pageable) {
        return boardService.getBoardList(pageable);
    }

    @GetMapping("/detail")
    public BoardDetailResponseDto boardDetail(@RequestParam Long boardId, @RequestHeader Long companyId) {
        return boardService.getBoardDetail(boardId, companyId);
    }

}


