package wanted.recruitment.common.exception;

import org.springframework.http.HttpStatus;

public enum CustomError {
    SUBMIT_TILL_ONCE(1000, "사용자는 1회만 지원 가능합니다.", HttpStatus.FORBIDDEN.value()),
    BOARD_NOT_EXIST(2000, "존재하지 않는 채용 공고입니다.", HttpStatus.BAD_REQUEST.value()),
    COMPANY_NOT_ALLOWED(3000, "접근 권한이 없는 회사입니다.", HttpStatus.FORBIDDEN.value()),
    CHOOSE_ALL_LOCATIONS(4000, "국가, 도시, 지역을 모두 선택해주세요", HttpStatus.BAD_REQUEST.value()),
    COUNTRY_NOT_EXIST(4001, "존재하지 않는 국가입니다.", HttpStatus.BAD_REQUEST.value()),
    CITY_NOT_EXIST(4002, "존재하지 않는 도시입니다.", HttpStatus.BAD_REQUEST.value()),
    REGION_NOT_EXIST(4003, "존재하지 않는 지역입니다.", HttpStatus.BAD_REQUEST.value());

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
