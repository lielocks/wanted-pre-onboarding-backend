package wanted.recruitment.repository.board;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import wanted.recruitment.common.config.QueryDslConfig;
import wanted.recruitment.dto.board.BoardDetailResponseDto;
import wanted.recruitment.dto.board.BoardInfoResponseDto;

import java.util.List;

import static wanted.recruitment.domain.board.QBoard.board;
import static wanted.recruitment.domain.company.QCompany.company;
import static wanted.recruitment.domain.location.QCity.city;
import static wanted.recruitment.domain.location.QCountry.country;
import static wanted.recruitment.domain.location.QLocation.location;

@Slf4j
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final QueryDslConfig queryDslConfig;

    @Override
    public Page<BoardInfoResponseDto> boardInfoResponse(Pageable pageable) {
        List<BoardInfoResponseDto> results = queryDslConfig.jpaQueryFactory()
                .select(Projections.constructor(BoardInfoResponseDto.class,
                        company.id, company.name, country.name, city.name, board.position, board.reward, board.skill))
                .from(board)
                .leftJoin(board.company, company)
                .leftJoin(company.location, location)
                .leftJoin(location.country, country)
                .leftJoin(location.city, city)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryDslConfig.jpaQueryFactory()
                .select(board.id.count())
                .from(board);

        return PageableExecutionUtils.getPage(results, pageable, count::fetchOne);
    }

    @Override
    public BoardDetailResponseDto boardDetailResponse(Long boardId, Long companyId) {
        BoardDetailResponseDto boardDetailResponseDto = queryDslConfig.jpaQueryFactory()
                .select(Projections.constructor(BoardDetailResponseDto.class,
                        company.id, company.name, country.name, city.name, board.position, board.reward, board.skill, board.description))
                .from(board)
                .leftJoin(board.company, company)
                .leftJoin(company.location, location)
                .leftJoin(location.country, country)
                .leftJoin(location.city, city)
                .where(board.id.eq(boardId).and(board.company.id.eq(companyId)))
                .fetchFirst();

        List<Long> boardIdList = queryDslConfig.jpaQueryFactory()
                .select(board.id)
                .from(board)
                .where(board.company.id.eq(companyId))
                .fetch();

        if (boardDetailResponseDto != null) {
            boardDetailResponseDto.setBoardIdList(boardIdList);
        } else boardDetailResponseDto.setBoardIdList(null);

        return boardDetailResponseDto;
    }

}
