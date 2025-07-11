package com.aidcompass.schedule.exceptions;

import com.aidcompass.core.general.exceptions.BaseControllerAdvice;
import com.aidcompass.core.general.exceptions.mapper.ExceptionMapper;
import com.aidcompass.core.general.exceptions.models.Exception;
import com.aidcompass.core.general.exceptions.models.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.lettuce.core.RedisConnectionException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
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

import java.util.Locale;

@RestControllerAdvice(basePackages = "com.aidcompass.schedule")
public class ScheduleControllerAdvice extends BaseControllerAdvice {

    public ScheduleControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
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
        return super.handleHandlerMethodValidationException(e, locale);
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
}
