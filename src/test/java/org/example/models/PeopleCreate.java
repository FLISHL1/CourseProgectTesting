package org.example.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PeopleCreate extends People{
    private Integer id;
    private String createdAt;
}
