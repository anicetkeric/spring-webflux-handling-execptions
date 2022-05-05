package com.example.springwebfluxhandlingexecptions.handler;


import com.example.springwebfluxhandlingexecptions.exception.UnAuthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthorHandler {

    public Mono<ServerResponse> getAuthor(ServerRequest serverRequest) {
        return Mono.error(new UnAuthorizedException("Access denied"));
    }
}