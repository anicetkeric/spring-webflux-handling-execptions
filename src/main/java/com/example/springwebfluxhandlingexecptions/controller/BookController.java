package com.example.springwebfluxhandlingexecptions.controller;

import com.example.springwebfluxhandlingexecptions.exception.UnAuthorizedException;
import com.example.springwebfluxhandlingexecptions.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class BookController {

    @GetMapping("/book")
    public Mono<ResponseEntity<Book>> getBookById() {
        return Mono.error(new UnAuthorizedException("Access denied"));
    }

}
