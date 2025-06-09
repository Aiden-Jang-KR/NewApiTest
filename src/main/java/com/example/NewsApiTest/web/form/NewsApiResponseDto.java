package com.example.NewsApiTest.web.form;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsApiResponseDto {
    private String status;
    private String code;
    private String message;

    private int totalResults;
    private List<NewsApiArticlesDto> articles;

    public NewsApiResponseDto(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public NewsApiResponseDto(String status, int totalResults, List<NewsApiArticlesDto> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }
}
