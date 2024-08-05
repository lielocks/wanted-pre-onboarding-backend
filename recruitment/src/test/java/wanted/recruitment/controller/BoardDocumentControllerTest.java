package wanted.recruitment.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wanted.recruitment.dto.BoardDocumentResponseDto;
import wanted.recruitment.repository.board.BoardRepository;
import wanted.recruitment.repository.board.document.BoardDocumentRepository;
import wanted.recruitment.service.DataSyncService;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BoardDocumentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DataSyncService dataSyncService;

    @MockBean
    private BoardRepository boardRepository;

    @MockBean
    private BoardDocumentRepository boardDocumentRepository;

    @Test
    @DisplayName("skill Keyword")
    void searchBoardDocumentWithSkillKeyword() throws Exception {
        String keyword = "spring";

        List<BoardDocumentResponseDto> expectedResponse = Arrays.asList(
                BoardDocumentResponseDto.builder().boardId(36L).companyName("카카오").countryName("한국").cityName("서울")
                        .position("백엔드 시니어 개발자").reward(25000).skill("spring").build()
        );

        when(dataSyncService.searchBoardDocument(keyword)).thenReturn(expectedResponse);

        mvc.perform(MockMvcRequestBuilders.get("/document/search")
                        .param("keyword", keyword)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].boardId").value(36L))
                .andExpect(jsonPath("$[0].companyName").value("카카오"))
                .andExpect(jsonPath("$[0].countryName").value("한국"))
                .andExpect(jsonPath("$[0].cityName").value("서울"))
                .andExpect(jsonPath("$[0].position").value("백엔드 시니어 개발자"))
                .andExpect(jsonPath("$[0].reward").value(25000))
                .andExpect(jsonPath("$[0].skill").value("spring"));
    }

    @Test
    @DisplayName("company name Keyword")
    void searchBoardDocumentWithCompanyNameKeyword() throws Exception {
        String keyword = "인프런";

        List<BoardDocumentResponseDto> expectedResponse = Arrays.asList(
                BoardDocumentResponseDto.builder().boardId(35L).companyName("인프런").countryName("한국").cityName("서울")
                        .position("프론트엔드 주니어 개발자").reward(1000).skill("react").build()
        );

        when(dataSyncService.searchBoardDocument(keyword)).thenReturn(expectedResponse);

        mvc.perform(MockMvcRequestBuilders.get("/document/search")
                        .param("keyword", keyword)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].boardId").value(35L))
                .andExpect(jsonPath("$[0].companyName").value("인프런"))
                .andExpect(jsonPath("$[0].countryName").value("한국"))
                .andExpect(jsonPath("$[0].cityName").value("서울"))
                .andExpect(jsonPath("$[0].position").value("프론트엔드 주니어 개발자"))
                .andExpect(jsonPath("$[0].reward").value(1000))
                .andExpect(jsonPath("$[0].skill").value("react"));
    }

    @Test
    @DisplayName("position Keyword")
    void searchBoardDocumentWithPositionKeyword() throws Exception {
        String keyword = "디자이너";

        List<BoardDocumentResponseDto> expectedResponse = Arrays.asList(
                BoardDocumentResponseDto.builder().boardId(37L).companyName("토스").countryName("한국").cityName("서울")
                        .position("디자이너 직군 인턴").reward(25000).skill("blender").build()
        );

        when(dataSyncService.searchBoardDocument(keyword)).thenReturn(expectedResponse);

        mvc.perform(MockMvcRequestBuilders.get("/document/search")
                        .param("keyword", keyword)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].boardId").value(37L))
                .andExpect(jsonPath("$[0].companyName").value("토스"))
                .andExpect(jsonPath("$[0].countryName").value("한국"))
                .andExpect(jsonPath("$[0].cityName").value("서울"))
                .andExpect(jsonPath("$[0].position").value("디자이너 직군 인턴"))
                .andExpect(jsonPath("$[0].reward").value(25000))
                .andExpect(jsonPath("$[0].skill").value("blender"));
    }

}