package wanted.recruitment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wanted.recruitment.common.dto.PageResponse;
import wanted.recruitment.dto.*;
import wanted.recruitment.service.BoardService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/board")
public class BoardController {

    private final BoardService boardService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@RequestBody BoardRegisterRequestDto requestDto) {
        boardService.register(requestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update")
    public void update(@RequestHeader Long companyId, @RequestBody BoardUpdateRequestDto requestDto) {
        boardService.update(requestDto, companyId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete")
    public void delete(@RequestHeader Long companyId, @RequestBody BoardIdRequestDto requestDto) {
        boardService.delete(requestDto.getBoardId(), companyId);
    }

    @GetMapping("/list")
    public PageResponse<BoardInfoResponseDto> boardList(Pageable pageable) {
        return new PageResponse<>(boardService.getBoardList(pageable));
    }

    @GetMapping("/detail")
    public BoardDetailResponseDto boardDetail(@RequestParam Long boardId, @RequestHeader Long companyId) {
        return boardService.getBoardDetail(boardId, companyId);
    }

}


