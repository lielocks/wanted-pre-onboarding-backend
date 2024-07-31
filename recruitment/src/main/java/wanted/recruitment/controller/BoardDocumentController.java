package wanted.recruitment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wanted.recruitment.domain.BoardDocument;
import wanted.recruitment.service.DataSyncService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/document")
public class BoardDocumentController {

    private final DataSyncService dataSyncService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void registerElasticSearchData() {
        dataSyncService.save();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<BoardDocument> searchBoardDocuments(@RequestParam String keyword, Pageable pageable) {
        return dataSyncService.searchBoardDocument(keyword, pageable);
    }



}
