package com.example.springwebfluxhandlingexecptions.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppExceptionResponse {

    private String code;
    private String message;
    private Instant timestamp;
}
