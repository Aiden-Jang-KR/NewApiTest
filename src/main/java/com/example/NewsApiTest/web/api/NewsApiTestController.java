package com.example.NewsApiTest.web.api;

import com.example.NewsApiTest.domain.NewsApiService;
import com.example.NewsApiTest.web.form.NewsApiRequestForm;
import com.example.NewsApiTest.web.form.NewsApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    public Object everythingTest(@RequestBody NewsApiRequestForm form){
        if(!StringUtils.hasText(form.getCountry()) && !StringUtils.hasText(form.getQ())
                && !StringUtils.hasText(form.getLanguage()) && !StringUtils.hasText(form.getCategory()) ){
            log.info("검색어는 필수입니다. 검색어 : [{}]", form.getQ());
            return new NewsApiResponseDto("error", "parametersMissing", "검색어는 필수입니다.");
        }
        return ResponseEntity.ok(newsApiService.everythingTest(form));
    }

    @PostMapping("/top-headline")
    public Object topHeadlineTest(@RequestBody NewsApiRequestForm form){
        if(!StringUtils.hasText(form.getCountry()) && !StringUtils.hasText(form.getQ())
                && !StringUtils.hasText(form.getLanguage()) && !StringUtils.hasText(form.getCategory()) ){
            log.info("검색조건으로 검색어, 언어, 국가, 카테고리 중 하나는 필수입니다. 검색어 : [{}] 언어 : [{}] 국가 : [{}] 카테고리 : [{}]"
                    , form.getQ(), form.getLanguage(), form.getCountry(), form.getCategory());
            return new NewsApiResponseDto("error", "parametersMissing", "검색조건으로 검색어, 언어, 국가, 카테고리 중 하나는 필수입니다.");
        }
        return ResponseEntity.ok(newsApiService.topHeadlineTest(form));
    }
}
