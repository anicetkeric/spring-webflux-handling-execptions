package com.example.springwebfluxhandlingexecptions.exception;

import com.example.springwebfluxhandlingexecptions.enums.ErrorAttributesKey;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

record ExceptionRule(Class<?> exceptionClass, HttpStatus status){}

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private final List<ExceptionRule> exceptionsRules = List.of(
            new ExceptionRule(MethodNotAllowedException.class, HttpStatus.METHOD_NOT_ALLOWED),
            new ExceptionRule(UnAuthorizedException.class, HttpStatus.UNAUTHORIZED),
            new ExceptionRule(ServerErrorException.class, HttpStatus.INTERNAL_SERVER_ERROR),
            new ExceptionRule(NotAcceptableStatusException.class, HttpStatus.NOT_ACCEPTABLE),
            new ExceptionRule(UnsupportedMediaTypeStatusException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    );



    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = getError(request);

        final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        Optional<ExceptionRule> exceptionRuleOptional = exceptionsRules.stream()
                .map(exceptionRule -> exceptionRule.exceptionClass().isInstance(error) ? exceptionRule : null)
                .filter(Objects::nonNull)
                .findFirst();

       return exceptionRuleOptional.<Map<String, Object>>map(exceptionRule -> Map.of(ErrorAttributesKey.CODE.getKey(), exceptionRule.status().value(), ErrorAttributesKey.MESSAGE.getKey(), error.getMessage(),  ErrorAttributesKey.TIME.getKey(), timestamp))
               .orElseGet(() -> Map.of(ErrorAttributesKey.CODE.getKey(), HttpStatus.BAD_REQUEST.value(),  ErrorAttributesKey.MESSAGE.getKey(), error.getMessage(), ErrorAttributesKey.TIME.getKey(), timestamp));
    }
}