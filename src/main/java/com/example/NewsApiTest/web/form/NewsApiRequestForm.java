package com.example.NewsApiTest.web.form;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsApiRequestForm {
    private String q;

    private String country;
    private String category;

    private List<String> sources;
    private List<String> searchIn;
    private List<String> domains;
    private List<String> excludeDomains;

    private LocalDateTime from;
    private LocalDateTime to;

    private String searchLanguage;
    private String searchSortBy;

    private Language language;
    private SortBy sortBy;

    private int pageSize;
    private int page;

    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
