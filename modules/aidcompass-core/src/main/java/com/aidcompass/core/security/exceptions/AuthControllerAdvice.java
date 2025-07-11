package com.aidcompass.core.security.exceptions;

import com.aidcompass.core.general.exceptions.BaseControllerAdvice;
import com.aidcompass.core.general.exceptions.mapper.ExceptionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;


@RestControllerAdvice(basePackages = {"com.aidcompass.security"})
@Slf4j
public class AuthControllerAdvice extends BaseControllerAdvice {

    public AuthControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }

    @ExceptionHandler(BaseAuthorizationException.class)
    public ResponseEntity<?> handleBaseAuthorizationException(BaseAuthorizationException e, Locale locale) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
