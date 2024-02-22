package com.myco.stock.news.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class News {
    @JsonProperty("status")
    private String status;
    @JsonProperty("totalResults")
    private Integer totalResults;
    @JsonProperty("articles")
    private List<Article> articles = new ArrayList<>();

    private String str;
}
