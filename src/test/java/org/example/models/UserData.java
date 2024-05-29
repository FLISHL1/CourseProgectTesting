package org.example.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private Integer id;
    private String email;
    @JsonAlias("first_name")
    private String firstName;
    @JsonAlias("last_name")
    private String lastName;
    private String avatar;
}
