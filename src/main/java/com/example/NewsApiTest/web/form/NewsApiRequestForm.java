package com.example.NewsApiTest.web.form;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsApiRequestForm {
    private String q;

    private int pageSize;
    private int page;
}
