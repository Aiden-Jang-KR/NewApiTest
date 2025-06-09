package com.example.NewsApiTest.web.form;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsApiRequestForm {
    private String q;

    private String country;
    private String category;

    private String sources;
    private String searchIn;
    private String domains;
    private String excludeDomains;

    private LocalDateTime from;
    private LocalDateTime to;

    private String language;
    private SortBy sortBy;

    private int pageSize;
    private int page;
}
