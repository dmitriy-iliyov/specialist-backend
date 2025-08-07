package com.specialist.user.exceptions;

import com.specialist.core.exceptions.BaseControllerAdvice;
import com.specialist.core.exceptions.mapper.ExceptionMapper;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class UserControllerAdvice extends BaseControllerAdvice {

    public UserControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }
}
