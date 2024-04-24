package miu.edu.oktion.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiRequestException extends RuntimeException {
    private HttpStatus httpStatus;

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
