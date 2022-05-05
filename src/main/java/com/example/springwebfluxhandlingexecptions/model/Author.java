package com.example.springwebfluxhandlingexecptions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private String id;

    private String lastname;

    private String middleName;

    private String firstname;
}
