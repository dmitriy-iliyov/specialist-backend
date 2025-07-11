package com.aidcompass.core.contact.exceptions;

import com.aidcompass.core.general.exceptions.BaseControllerAdvice;
import com.aidcompass.core.general.exceptions.mapper.ExceptionMapper;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ContactControllerAdvice extends BaseControllerAdvice {

    public ContactControllerAdvice(ExceptionMapper exceptionMapper, MessageSource messageSource) {
        super(exceptionMapper, messageSource);
    }
}
