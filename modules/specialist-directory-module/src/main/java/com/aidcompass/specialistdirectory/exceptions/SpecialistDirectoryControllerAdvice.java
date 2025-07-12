package com.aidcompass.specialistdirectory.exceptions;

import com.aidcompass.core.general.exceptions.BaseControllerAdvice;
import com.aidcompass.core.general.exceptions.mapper.ExceptionMapper;
import com.aidcompass.core.general.exceptions.models.*;
import com.aidcompass.core.general.exceptions.models.Exception;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;
import com.aidcompass.core.general.utils.ErrorUtils;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.f4b6a3.uuid.exception.InvalidUuidException;
import io.lettuce.core.RedisConnectionException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.aidcompass.specialistdirectory")
public class SpecialistDirectoryControllerAdvice extends BaseControllerAdvice {

    public SpecialistDirectoryControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
            NoResourceFoundException.class,
            org.springframework.web.HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<?> handleNoHandlerOrNoResourceFoundException(java.lang.Exception e) {
        return super.handleNoHandlerOrNoResourceFoundException(e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e, Locale locale) {
        return super.handleIllegalArgumentException(e, locale);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleThrowable(Throwable throwable, Locale locale) {
        return super.handleThrowable(throwable, locale);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(Exception e){
        return super.handleDataIntegrityViolationException(e);
    }

    @ExceptionHandler(BaseNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(BaseNotFoundException e, Locale locale) {
        return super.handleNotFoundException(e, locale);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e){
        return super.handleIllegalStateException(e);
    }

    @ExceptionHandler(BaseInvalidInputException.class)
    public ResponseEntity<?> handleInvalidInputException(BaseInvalidInputException e, Locale locale){
        return super.handleInvalidInputException(e, locale);
    }

    @ExceptionHandler(BaseInvalidInputExceptionList.class)
    public ResponseEntity<?> handleInvalidContactUpdateException(BaseInvalidInputExceptionList e, Locale locale) {
        return super.handleInvalidContactUpdateException(e, locale);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        return super.handleBadCredentialsException(e);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return super.handleMissingServletRequestParameterException(e);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleHandlerMethodValidationException(HandlerMethodValidationException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                getMessageSource().getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", ErrorUtils.fromParameterErrorstoListErrorDtoList(e.getBeanResults())));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Locale locale) {
        return super.handleMethodArgumentNotValidException(e, locale);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e, Locale locale) {
        return super.handleConstraintViolationException(e, locale);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, Locale locale) {
        return super.handleMethodArgumentTypeMismatchException(e, locale);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, Locale locale) {
        return super.handleHttpMessageNotReadableException(e, locale);
    }

    @ExceptionHandler(RedisConnectionException.class)
    public ResponseEntity<?> handleRedisConnectionException(RedisConnectionException e, Locale locale) {
        return super.handleRedisConnectionException(e, locale);
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<?> handelJsonMappingException(JsonMappingException e, Locale locale) {
        return super.handelJsonMappingException(e, locale);
    }

    @ExceptionHandler(BaseInternalServerException.class)
    public ResponseEntity<?> handleBaseInternalServiceException(BaseInternalServerException e, Locale locale) {
        return super.handleBaseInternalServiceException(e, locale);
    }

    @ExceptionHandler(InvalidUuidException.class)
    public ResponseEntity<?> handleInvalidUuidException(InvalidUuidException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                getMessageSource().getMessage("400", null, "error.400", locale));
        String [] em = e.getMessage().split(":");
        problemDetail.setProperty("properties", List.of(new ErrorDto(em[0], em[1])));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }
}
