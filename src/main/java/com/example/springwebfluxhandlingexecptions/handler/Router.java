package com.example.springwebfluxhandlingexecptions.handler;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@AllArgsConstructor
public class Router {

    @Bean
    public RouterFunction<ServerResponse> routeUserAccount(final AuthorHandler authorHandler) {
        return route()
                .nest(path("/api"), builder -> builder.GET("/author", authorHandler::getAuthor))
                .build();
    }
}