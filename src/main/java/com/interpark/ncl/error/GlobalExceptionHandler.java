package com.interpark.ncl.error;

import com.interpark.ncl.error.exception.BlankKewordException;
import com.interpark.ncl.error.exception.NotExistProductException;
import com.interpark.ncl.error.exception.NotFoundUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundUserException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundUserException(NotFoundUserException e) {
        log.error("유저가 올바르지 않습니다.", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_FOUND_USER);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(BlankKewordException.class)
    protected ResponseEntity<ErrorResponse> handleBlankKewordException(BlankKewordException e) {
        log.error("키워드가 비어져 있습니다.", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.BLANK_KEYWORD);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(NotExistProductException.class)
    protected ResponseEntity<ErrorResponse> handleNotExistProductException(NotExistProductException e) {
        log.error("상품목록이 0개 입니다.", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NOT_EXITS_PRODUCT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
