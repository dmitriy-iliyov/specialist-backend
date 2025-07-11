package com.aidcompass.exceptions;

import com.aidcompass.general.exceptions.BaseControllerAdvice;
import com.aidcompass.general.exceptions.mapper.ExceptionMapper;
import com.aidcompass.general.utils.ErrorUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Locale;
import java.util.Map;

@ControllerAdvice(basePackages = "com.aidcompass.domain")
public class SpecialistDirectoryControllerAdvice extends BaseControllerAdvice {

    public SpecialistDirectoryControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }

    @Override
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleHandlerMethodValidationException(HandlerMethodValidationException e, Locale locale) {
        System.out.println("Override");
        System.out.println("handleHandlerMethodValidationException");
        System.out.println(e.getBeanResults());
        System.out.println(e.getValueResults());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                getMessageSource().getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", ErrorUtils.toErrorDtoList(e.getValueResults())));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);

    }
}
