package com.example.NewsApiTest.web.api;

import com.example.NewsApiTest.domain.NewsApiService;
import com.example.NewsApiTest.web.form.Language;
import com.example.NewsApiTest.web.form.NewsApiRequestForm;
import com.example.NewsApiTest.web.form.NewsApiResponseDto;
import com.example.NewsApiTest.web.form.SortBy;
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
        if(!StringUtils.hasText(form.getQ())){
            log.info("검색어는 필수입니다. 검색어 : [{}]", form.getQ());
            return new NewsApiResponseDto("error", "parametersMissing", "검색어는 필수입니다.");
        }

        if(StringUtils.hasText(form.getSearchSortBy())){
            try {
                SortBy sortBy = SortBy.fromString(form.getSearchSortBy());
                form.setSortBy(sortBy);
            } catch (IllegalArgumentException e) {
                return new NewsApiResponseDto("error", "parametersMissing", "잘못된 정렬기준입니다. 허용된 문자열 : PUB, REL, PUP");
            }
        }

        if(StringUtils.hasText(form.getSearchLanguage())){
            try {
                Language lang = Language.fromString(form.getSearchLanguage());
                form.setLanguage(lang);
            } catch (IllegalArgumentException e) {
                return new NewsApiResponseDto("error", "parametersMissing", "지원하지 않는 언어입니다.");
            }
        }

        if(form.getFrom() != null && form.getTo() != null && form.getTo().isBefore(form.getFrom())){
            return new NewsApiResponseDto("error", "parametersMissing", "검색일자를 확인해 주세요.");
        }

        if(form.getPageSize() > 100){
            return new NewsApiResponseDto("error", "parametersMissing", "1회 최대 검색량은 100건입니다.");
        }

        return ResponseEntity.ok(newsApiService.getEverything(form));
    }

    @PostMapping("/top-headline")
    public Object topHeadlineTest(@RequestBody NewsApiRequestForm form){
        if(!StringUtils.hasText(form.getCountry()) && !StringUtils.hasText(form.getQ())
                && !StringUtils.hasText(form.getSearchLanguage()) && !StringUtils.hasText(form.getCategory()) ){
            log.info("검색조건으로 검색어, 국가, 카테고리 중 하나는 필수입니다. 검색어 : [{}] 국가 : [{}] 카테고리 : [{}]"
                    , form.getQ(), form.getCountry(), form.getCategory());
            return new NewsApiResponseDto("error", "parametersMissing", "검색조건으로 검색어, 언어, 국가, 카테고리 중 하나는 필수입니다.");
        }

        if(StringUtils.hasText(form.getSearchSortBy())){
            try {
                SortBy sortBy = SortBy.fromString(form.getSearchSortBy());
                form.setSortBy(sortBy);
            } catch (IllegalArgumentException e) {
                return new NewsApiResponseDto("error", "parametersMissing", "잘못된 정렬기준입니다. 허용된 문자열 : PUB, REL, PUP");
            }
        }


        return ResponseEntity.ok(newsApiService.getTopHeadline(form));
    }
}
