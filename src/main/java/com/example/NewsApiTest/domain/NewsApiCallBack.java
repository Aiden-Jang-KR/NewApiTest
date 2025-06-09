package com.example.NewsApiTest.domain;

import com.example.NewsApiTest.web.form.NewsApiResponseDto;

public interface NewsApiCallBack {
    void onResult(NewsApiResponseDto responseDto);
}
