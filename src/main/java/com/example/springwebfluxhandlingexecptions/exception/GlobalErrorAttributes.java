package com.example.springwebfluxhandlingexecptions.exception;

import com.example.springwebfluxhandlingexecptions.enums.ErrorAttributesKey;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

record ExceptionRule(Class<?> exceptionClass, HttpStatus status){}

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private final List<ExceptionRule> exceptionsRules = List.of(
            new ExceptionRule(UnAuthorizedException.class, HttpStatus.UNAUTHORIZED)
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
               .orElseGet(() -> Map.of(ErrorAttributesKey.CODE.getKey(), determineHttpStatus(error).value(),  ErrorAttributesKey.MESSAGE.getKey(), error.getMessage(), ErrorAttributesKey.TIME.getKey(), timestamp));
    }


    private HttpStatus determineHttpStatus(Throwable error) {
        return error instanceof ResponseStatusException err ? err.getStatus() : MergedAnnotations.from(error.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class).getValue(ErrorAttributesKey.CODE.getKey(), HttpStatus.class).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}