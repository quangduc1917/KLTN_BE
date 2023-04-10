package com.example.movieba.model.response.news;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class NewsResponse {
    private Long idNew;
    private String titleNew;
    private String describeNew;
    private String statusNew;
    private Date timeNews;
}
