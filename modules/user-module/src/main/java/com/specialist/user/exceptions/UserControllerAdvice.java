package com.specialist.user.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.f4b6a3.uuid.exception.InvalidUuidException;
import com.specialist.core.exceptions.BaseControllerAdvice;
import com.specialist.core.exceptions.ErrorUtils;
import com.specialist.core.exceptions.mapper.ExceptionMapper;
import com.specialist.core.exceptions.models.Exception;
import com.specialist.core.exceptions.models.*;
import com.specialist.core.exceptions.models.dto.ErrorDto;
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

import java.lang.reflect.Field;
import java.util.*;

@RestControllerAdvice(basePackages = "com.specialist.user")
public class UserControllerAdvice extends BaseControllerAdvice {

    public UserControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<?> handleJsonProcessingException(JsonProcessingException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                getMessageSource().getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", List.of(
                new ErrorDto("data", "Data have invalid format.")))
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
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

    @ExceptionHandler(BaseBadRequestException.class)
    public ResponseEntity<?> handleInvalidInputException(BaseBadRequestException e, Locale locale){
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
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                getMessageSource().getMessage("400", null, "error.400", locale));
        List<ErrorDto> errors = new ArrayList<>();
        Object o = e.getBindingResult().getTarget();
        Class<?> targetClass = o != null ? o.getClass() : null;
        if (targetClass != null) {
            e.getBindingResult().getFieldErrors().forEach(fieldError -> {
                if (!Objects.requireNonNull(fieldError.getDefaultMessage()).isBlank()) {
                    String fieldName;
                    try {
                        Field field = targetClass.getDeclaredField(fieldError.getField());
                        JsonProperty annotation = field.getAnnotation(JsonProperty.class);
                        fieldName = annotation != null ? annotation.value() : fieldError.getField();
                    } catch (NoSuchFieldException nse) {
                        fieldName = fieldError.getField();
                    }
                    errors.add(new ErrorDto(fieldName, Objects.requireNonNull(fieldError.getDefaultMessage())));
                }
            });
            Collections.reverse(errors);
            problemDetail.setProperty("properties", Map.of("errors", errors));
        } else {
            problemDetail.setProperty("properties", Map.of("errors", ErrorUtils.toErrorDtoList(e.getBindingResult())));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
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

    @ExceptionHandler(BaseForbiddenException.class)
    public ResponseEntity<?> handleBaseForbiddenException(BaseForbiddenException e, Locale locale) {
        return super.handleBaseForbiddenException(e, locale);
    }
}
