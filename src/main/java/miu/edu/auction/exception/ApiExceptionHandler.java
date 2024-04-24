package miu.edu.auction.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        ApiException exc = new ApiException(e.getMessage(), e.getHttpStatus(), ZonedDateTime.now());
        return new ResponseEntity<>(exc, e.getHttpStatus());
    }
}
