package wanted.recruitment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wanted.recruitment.dto.BoardDocumentResponseDto;
import wanted.recruitment.service.DataSyncService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/document")
public class BoardDocumentController {

    private final DataSyncService dataSyncService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void registerElasticSearchData() {
        dataSyncService.manualSave();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<BoardDocumentResponseDto> searchBoardDocuments(@RequestParam String keyword) {
        return dataSyncService.searchBoardDocument(keyword);
    }

}
