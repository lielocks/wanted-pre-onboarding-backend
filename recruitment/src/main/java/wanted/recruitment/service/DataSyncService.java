package wanted.recruitment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.recruitment.domain.Board;
import wanted.recruitment.domain.BoardDocument;
import wanted.recruitment.domain.SyncStatus;
import wanted.recruitment.dto.BoardDocumentResponseDto;
import wanted.recruitment.repository.board.BoardRepository;
import wanted.recruitment.repository.board.SyncStatusRepository;
import wanted.recruitment.repository.board.document.BoardDocumentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataSyncService {

    private final BoardRepository boardRepository;
    private final BoardDocumentRepository boardDocumentRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final SyncStatusRepository syncStatusRepository;
    private static final Long SYNC_STATUS_ID = 1L;
    private LocalDateTime lastSyncDate;

    @Transactional
    public void initializeLastSyncDate() {
        SyncStatus syncStatus = syncStatusRepository.findById(SYNC_STATUS_ID)
                .orElseGet(() -> {
                    SyncStatus newStatus =
                            SyncStatus.builder().id(SYNC_STATUS_ID).lastSyncDate(LocalDateTime.now().minusHours(12)).build();
                    return syncStatusRepository.save(newStatus);
                });
        lastSyncDate = syncStatus.getLastSyncDate();
        log.info("sync id {}", syncStatus.getId());
    }

    @Transactional
    public void manualSave() {
        List<Board> boards = boardRepository.findAll();
        boards.stream().map(BoardDocument::from).forEach(elasticsearchRestTemplate::save);
    }

    @Scheduled(fixedRate = 300000) // 300000 milliseconds = 5 minutes
    @Transactional
    public void scheduledSave() {
        initializeLastSyncDate();

        LocalDateTime currentSyncDate = LocalDateTime.now();
        List<Board> boards = boardRepository.findAllModifiedOrCreatedSince(lastSyncDate);
        boards.stream().map(BoardDocument::from).forEach(elasticsearchRestTemplate::save);

        SyncStatus syncStatus = syncStatusRepository.findById(SYNC_STATUS_ID).get();
        syncStatus.setLastSyncDate(currentSyncDate);
        syncStatusRepository.save(syncStatus);
    }

    public List<BoardDocumentResponseDto> searchBoardDocument(String keyword) {
        return boardDocumentRepository.searchBoardDocument(keyword);
    }

}
