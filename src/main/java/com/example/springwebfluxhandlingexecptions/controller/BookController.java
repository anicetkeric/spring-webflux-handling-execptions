package com.example.springwebfluxhandlingexecptions.controller;

import com.example.springwebfluxhandlingexecptions.exception.UnAuthorizedException;
import com.example.springwebfluxhandlingexecptions.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Slf4j
public class BookController {

    @GetMapping("/book")
    public Mono<ResponseEntity<Book>> getBookById() {
        return Mono.error(new UnAuthorizedException("Access denied"));
    }

}
