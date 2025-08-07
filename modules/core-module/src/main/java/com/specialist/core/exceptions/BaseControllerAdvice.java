package com.specialist.core.exceptions;

import com.specialist.core.exceptions.mapper.ExceptionMapper;
import com.specialist.core.exceptions.models.*;
import com.specialist.core.exceptions.models.Exception;
import com.specialist.core.exceptions.models.dto.ErrorDto;
import com.specialist.core.exceptions.models.dto.ExceptionResponseDto;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.lettuce.core.RedisConnectionException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;

import static com.fasterxml.jackson.databind.util.ClassUtil.getRootCause;

@Slf4j
@RequiredArgsConstructor
@Getter
public abstract class BaseControllerAdvice {

    private final ExceptionMapper exceptionMapper;
    private final MessageSource messageSource;


    public ResponseEntity<?> handleNoHandlerOrNoResourceFoundException(java.lang.Exception e) {
        String url = null;

        if (e instanceof NoHandlerFoundException) {
            url = ((NoHandlerFoundException) e).getRequestURL();
        }

        if (e instanceof NoResourceFoundException) {
            url = ((NoResourceFoundException) e).getResourcePath();
        }
        log.error(e.getMessage());
        log.error(Arrays.toString(e.getStackTrace()));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseDto(
                        "404",
                        "Not Found",
                        "The " + url + " was not found. " + e.getClass())
                );
    }

    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e, Locale locale) {
        log.error("Exception 500: {}", e.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                messageSource.getMessage("500", null, "error.500", locale));
        problemDetail.setProperty("properties", Map.of("errors", List.of(new ErrorDto("fromCode()", "Illegal argument exception!"))));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }

    public ResponseEntity<?> handleThrowable(Throwable throwable, Locale locale) {
        if (throwable instanceof UsernameNotFoundException) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                    getMessageSource().getMessage("404", null, "error.404", locale));
            problemDetail.setDetail(throwable.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(problemDetail);
        } else if (throwable instanceof AccessDeniedException) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,
                    getMessageSource().getMessage("403", null, "error.403", locale));
            problemDetail.setDetail(throwable.getMessage());
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(problemDetail);
        }
        ExceptionResponseDto exceptionDto = new ExceptionResponseDto(
                "500",
                "Unexpected server error.",
                null);
        log.error(Arrays.toString(throwable.getStackTrace()));
        log.error(throwable.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDto);
    }

    public ResponseEntity<?> handleDataIntegrityViolationException(Exception e){
        ExceptionResponseDto exceptionDto = new ExceptionResponseDto(
                "400",
                e.getMessage(),
                Arrays.toString(e.getStackTrace()));
        log.error("Exception 400: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionDto);
    }

    public ResponseEntity<?> handleNotFoundException(BaseNotFoundException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                messageSource.getMessage("404", null, "error.404", locale));
        problemDetail.setProperty("properties", Map.of("errors", List.of(e.getErrorDto())));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e){
        ExceptionResponseDto exceptionDto = new ExceptionResponseDto(
                "500",
                "Illegal State Exception",
                null);
        log.error("Exception 500: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDto);
    }

    public ResponseEntity<?> handleInvalidInputException(BaseBadRequestException e, Locale locale){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", List.of(e.getErrorDto())));
        System.out.println(problemDetail);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    public ResponseEntity<?> handleInvalidContactUpdateException(BaseInvalidInputExceptionList e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                this.getMessageSource().getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", e.getErrorDtoList()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        ExceptionResponseDto exceptionDto = new ExceptionResponseDto(
                "400",
                e.getMessage(),
                null);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionDto);
    }

    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String pN = e.getParameterName();
        String message = String.valueOf(pN.charAt(0)).toUpperCase() + pN.substring(1) + " should be present!";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDto("404", message, null));
    }

    //validation exception handling
    public ResponseEntity<?> handleHandlerMethodValidationException(HandlerMethodValidationException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", ErrorUtils.toErrorDtoList(e.getValueResults())));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);

    }

    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                getMessageSource().getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", ErrorUtils.toErrorDtoList(e.getBindingResult())));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e, Locale locale) {
        Set<ConstraintViolation<?>> bindingResult = e.getConstraintViolations();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", ErrorUtils.toErrorDtoList(bindingResult)));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", List.of(new ErrorDto(e.getName(), "Has invalid type."))));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, Locale locale) {

        Throwable root = getRootCause(e);

        if (root instanceof Exception exception) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                    getMessageSource().getMessage("500", null, "error.500", locale));
            problemDetail.setProperty("properties", Map.of("errors", List.of(exception.getErrorDto())));
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(problemDetail);
        }

        ProblemDetail generic = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Can't serialize request data!"
        );

        generic.setProperty("properties", Map.of("errors", e.getMessage().split(":")[0]));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(generic);
    }

    public ResponseEntity<?> handleRedisConnectionException(RedisConnectionException e, Locale locale) {
        log.error(e.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                messageSource.getMessage("500", null, "error.500", locale));
        problemDetail.setProperty("properties", Map.of("errors", List.of(new ErrorDto("", ""))));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }

    public ResponseEntity<?> handelJsonMappingException(JsonMappingException e, Locale locale) {
        Exception exception = (Exception) e.getCause();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                messageSource.getMessage("400", null, "error.400", locale));
        problemDetail.setProperty("properties", Map.of("errors", List.of(exception.getErrorDto())));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    public ResponseEntity<?> handleBaseInternalServiceException(BaseInternalServerException e, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                messageSource.getMessage("500", null, "error.500", locale));
        problemDetail.setProperty("properties", Map.of("errors", List.of(e.getErrorDto())));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }
}