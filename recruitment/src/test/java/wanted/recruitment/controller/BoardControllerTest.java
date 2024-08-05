package wanted.recruitment.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wanted.recruitment.common.dto.PageResponse;
import wanted.recruitment.dto.board.BoardDetailResponseDto;
import wanted.recruitment.dto.board.BoardInfoResponseDto;
import wanted.recruitment.repository.board.BoardRepository;
import wanted.recruitment.service.board.BoardService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BoardRepository boardRepository;

    @MockBean
    private BoardService boardService;

    @Test
    @DisplayName("get board list")
    void getBoardList() throws Exception {
        Pageable pageable = PageRequest.of(0, 4);

        List<BoardInfoResponseDto> data = Arrays.asList(
                new BoardInfoResponseDto(8L, "우버", "한국", "서울", "언리얼 팀장 개발자", 40000, "c++"),
                new BoardInfoResponseDto(6L, "인프런", "한국", "서울", "프론트엔드 주니어 개발자", 1000, "react"),
                new BoardInfoResponseDto(7L, "카카오", "한국", "서울", "백엔드 시니어 개발자", 25000, "spring"),
                new BoardInfoResponseDto(10L, "토스", "한국", "서울", "디자이너 직군 인턴", 25000, "blender")
        );

        Page<BoardInfoResponseDto> page = new PageImpl<>(data, pageable, data.size());
        PageResponse<BoardInfoResponseDto> boardInfoResponseDtoPageResponse = new PageResponse<>(page);

        when(boardService.getBoardList(pageable)).thenReturn(boardInfoResponseDtoPageResponse);

        mvc.perform(MockMvcRequestBuilders.get("/public/board/list")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].companyId").value(8L))
                .andExpect(jsonPath("$.data[0].companyName").value("우버"))
                .andExpect(jsonPath("$.data[0].countryName").value("한국"))
                .andExpect(jsonPath("$.data[0].cityName").value("서울"))
                .andExpect(jsonPath("$.data[0].position").value("언리얼 팀장 개발자"))
                .andExpect(jsonPath("$.data[0].reward").value(40000))
                .andExpect(jsonPath("$.data[0].skill").value("c++"))
                .andExpect(jsonPath("$.data[1].companyId").value(6L))
                .andExpect(jsonPath("$.data[1].companyName").value("인프런"))
                .andExpect(jsonPath("$.data[1].countryName").value("한국"))
                .andExpect(jsonPath("$.data[1].cityName").value("서울"))
                .andExpect(jsonPath("$.data[1].position").value("프론트엔드 주니어 개발자"))
                .andExpect(jsonPath("$.data[1].reward").value(1000))
                .andExpect(jsonPath("$.data[1].skill").value("react"))
                .andExpect(jsonPath("$.data[2].companyId").value(7L))
                .andExpect(jsonPath("$.data[2].companyName").value("카카오"))
                .andExpect(jsonPath("$.data[2].countryName").value("한국"))
                .andExpect(jsonPath("$.data[2].cityName").value("서울"))
                .andExpect(jsonPath("$.data[2].position").value("백엔드 시니어 개발자"))
                .andExpect(jsonPath("$.data[2].reward").value(25000))
                .andExpect(jsonPath("$.data[2].skill").value("spring"))
                .andExpect(jsonPath("$.data[3].companyId").value(10L))
                .andExpect(jsonPath("$.data[3].companyName").value("토스"))
                .andExpect(jsonPath("$.data[3].countryName").value("한국"))
                .andExpect(jsonPath("$.data[3].cityName").value("서울"))
                .andExpect(jsonPath("$.data[3].position").value("디자이너 직군 인턴"))
                .andExpect(jsonPath("$.data[3].reward").value(25000))
                .andExpect(jsonPath("$.data[3].skill").value("blender"))
                .andExpect(jsonPath("$.pageInfo.pageNumber").value(0))
                .andExpect(jsonPath("$.pageInfo.pageSize").value(4))
                .andExpect(jsonPath("$.pageInfo.totalPages").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(4))
                .andExpect(jsonPath("$.pageInfo.first").value(true))
                .andExpect(jsonPath("$.pageInfo.last").value(true));
    }

    @Test
    @DisplayName("get one particular board's detail")
    void getOneBoardDetailResponse() throws Exception {
        Long boardId = 35L;
        Long companyId = 6L;
        List<Long> boardIdList = Arrays.asList(35L, 39L);
        BoardDetailResponseDto data =
                new BoardDetailResponseDto(6L, "인프런", "한국", "서울", "프론트엔드 주니어 개발자", 1000, "react", "프론트 엔드 주니어 개발자 뽑아요", boardIdList);

        when(boardService.getBoardDetail(boardId, companyId)).thenReturn(data);

        mvc.perform(MockMvcRequestBuilders.get("/public/board/detail")
                        .header("companyId", String.valueOf(companyId))
                        .param("boardId", String.valueOf(boardId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").value(6L))
                .andExpect(jsonPath("$.companyName").value("인프런"))
                .andExpect(jsonPath("$.countryName").value("한국"))
                .andExpect(jsonPath("$.cityName").value("서울"))
                .andExpect(jsonPath("$.position").value("프론트엔드 주니어 개발자"))
                .andExpect(jsonPath("$.reward").value(1000))
                .andExpect(jsonPath("$.skill").value("react"))
                .andExpect(jsonPath("$.description").value("프론트 엔드 주니어 개발자 뽑아요"))
                .andExpect(jsonPath("$.boardIdList[0]").value(35L))
                .andExpect(jsonPath("$.boardIdList[1]").value(39L));
    }
}
