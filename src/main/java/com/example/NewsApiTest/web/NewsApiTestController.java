package com.example.NewsApiTest.web;

import com.example.NewsApiTest.domain.NewsApiService;
import com.example.NewsApiTest.web.form.NewsApiRequestForm;
import com.example.NewsApiTest.web.form.NewsApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsApiTestController {

    private final NewsApiService newsApiService;
    @PostMapping("/everything")
    public ResponseEntity<NewsApiResponseDto> everythingTest(@RequestBody NewsApiRequestForm form){
        return ResponseEntity.ok(newsApiService.everythingTest(form));
    }
}
