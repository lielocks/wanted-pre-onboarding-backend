package wanted.recruitment.common.exception;

import lombok.*;

@Data
@Builder
public class ErrorResponseDto {
    private int errorCode;
    private String message;

    public ErrorResponseDto(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
