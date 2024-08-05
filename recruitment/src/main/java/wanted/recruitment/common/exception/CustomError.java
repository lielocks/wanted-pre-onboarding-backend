package wanted.recruitment.common.exception;

import org.springframework.http.HttpStatus;

public enum CustomError {
    SUBMIT_TILL_ONCE(1000, "사용자는 1회만 지원 가능합니다.", HttpStatus.FORBIDDEN.value()),
    MEMBER_NOT_FOUND(1001, "해당 사용자는 존재하지 않습니다.", HttpStatus.FORBIDDEN.value()),
    BOARD_NOT_EXIST(2000, "존재하지 않는 채용 공고입니다.", HttpStatus.BAD_REQUEST.value()),
    COMPANY_NOT_ALLOWED(3000, "접근 권한이 없는 회사입니다.", HttpStatus.FORBIDDEN.value()),
    CHOOSE_ALL_LOCATIONS(4000, "국가, 도시, 지역을 모두 선택해주세요", HttpStatus.BAD_REQUEST.value()),
    COUNTRY_NOT_EXIST(4001, "존재하지 않는 국가입니다.", HttpStatus.BAD_REQUEST.value()),
    CITY_NOT_EXIST(4002, "존재하지 않는 도시입니다.", HttpStatus.BAD_REQUEST.value()),
    REGION_NOT_EXIST(4003, "존재하지 않는 지역입니다.", HttpStatus.BAD_REQUEST.value()),
    KOREA_CITY_NOT_MATCH(4004, "한국에 속하는 도시가 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    SEOUL_REGION_NOT_MATCH(4005, "서울에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    BUSAN_REGION_NOT_MATCH(4006, "부산에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    DAEGU_REGION_NOT_MATCH(4007, "대구에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    INCHEON_REGION_NOT_MATCH(4008, "인천에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    GWANGJU_REGION_NOT_MATCH(4009, "광주에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    DAEJEON_REGION_NOT_MATCH(4010, "대전에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    ULSAN_REGION_NOT_MATCH(4011, "울산에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    SAEJONG_REGION_NOT_MATCH(4012, "세종에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    KYUNGKI_REGION_NOT_MATCH(4013, "경기에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    KANGWON_REGION_NOT_MATCH(4014, "강원에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    CHUNGBOOK_REGION_NOT_MATCH(4015, "충북에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    CHUNGNAM_REGION_NOT_MATCH(4016, "충남에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    JEONBOOK_REGION_NOT_MATCH(4017, "전북에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    JEONNAM_REGION_NOT_MATCH(4018, "전남에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    KYEONGBOOK_REGION_NOT_MATCH(4019, "경북에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    KYEONGNAM_REGION_NOT_MATCH(4020, "경남에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value()),
    JEJU_REGION_NOT_MATCH(4021, "제주에 속하는 지역이 아닙니다.", HttpStatus.BAD_REQUEST.value());


    private int errorCode;
    private String message;
    private int statusCode;

    CustomError(int errorCode, String message, int statusCode) {
        this.errorCode = errorCode;
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
