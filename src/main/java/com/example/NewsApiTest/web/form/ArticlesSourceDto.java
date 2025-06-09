package com.example.NewsApiTest.web.form;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticlesSourceDto {
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String langueage;
    private String country;
}
