package com.example.NewsApiTest.domain;

import com.example.NewsApiTest.web.form.NewsApiRequestForm;
import com.example.NewsApiTest.web.form.NewsApiResponseDto;

public interface NewsApiService {
    public NewsApiResponseDto everythingTest(NewsApiRequestForm form);

    public NewsApiResponseDto topHeadlineTest(NewsApiRequestForm form);
}
