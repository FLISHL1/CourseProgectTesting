package org.example.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ColorData {
    private Integer id;
    private String name;
    private Integer year;
    private String color;
    @JsonAlias("pantone_value")
    private String pantoneValue;
}
