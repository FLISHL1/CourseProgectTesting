package org.example.models;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFromPage {
    private Integer page;
    private Integer per_page;
    private Integer total;
    @JsonAlias("total_pages")
    private Integer totalPages;
    private List<UserData> data;
    private UserSupport support;



    public UserFromPage(List<UserData> data, UserSupport support) {
        this.data = data;
        this.support = support;
    }
}
