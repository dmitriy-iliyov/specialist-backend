package com.aidcompass.user.exceptions;

import com.aidcompass.core.exceptions.BaseControllerAdvice;
import com.aidcompass.core.exceptions.mapper.ExceptionMapper;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class UserControllerAdvice extends BaseControllerAdvice {

    public UserControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }
}
