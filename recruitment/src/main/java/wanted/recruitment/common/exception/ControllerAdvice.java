package wanted.recruitment.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    private static final String DEFAULT_ERROR_MSG = "알 수 없는 에러가 발생했습니다. 운영자에게 문의 바랍니다.";
    private static final ErrorResponseDto DEFAULT_ERROR_RESPONSE =
            new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), DEFAULT_ERROR_MSG);

    private ErrorResponseDto createError(Exception e) {
        log.error(e.getMessage(), e);
        return DEFAULT_ERROR_RESPONSE;
    }

    private ErrorResponseDto createErrorDto(CustomError customError) {
        return ErrorResponseDto.builder().errorCode(customError.getErrorCode())
                .message(customError.getMessage()).build();
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        log.error("CustomException : {}", e.getCustomError().getMessage());
        return new ResponseEntity<>(createErrorDto(e.getCustomError()),
                HttpStatus.valueOf(e.getCustomError().getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return new ResponseEntity<>(createError(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
