package com.example.NewsApiTest.domain;

import com.example.NewsApiTest.web.form.NewsApiArticlesDto;
import com.example.NewsApiTest.web.form.NewsApiRequestForm;
import com.example.NewsApiTest.web.form.NewsApiResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsApiServiceImpl implements NewsApiService {

    private final NewsApiClient newsApiClient;
    private final ObjectMapper mapper;

    @Override
    public NewsApiResponseDto getEverything(NewsApiRequestForm form) {
        CompletableFuture<NewsApiResponseDto> future = new CompletableFuture<>();

        String sources = (!CollectionUtils.isEmpty(form.getSources())) ? String.join(",", form.getSources())  : null;
        String domains = (!CollectionUtils.isEmpty(form.getDomains())) ? String.join(",", form.getDomains())  : null;

        String searchIn = (!CollectionUtils.isEmpty(form.getSearchIn())) ? String.join(",", form.getSearchIn())  : null;
        String excludeDomains = (!CollectionUtils.isEmpty(form.getExcludeDomains())) ? String.join(",", form.getExcludeDomains())  : null;

        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(form.getQ())
                        .sources(sources)
                        .domains(domains)
                        .sortBy(form.getSortBy() != null ? form.getSortBy().getLabel(): null)
                        .language(form.getLanguage() != null ? form.getLanguage().getLabel() : null)
                        .from(form.getFrom() != null ? form.getFrom().toString() : null)
                        .to(form.getTo() != null ? form.getTo().toString() : null)
                        .pageSize(form.getPageSize() > 0 ?   form.getPageSize() : 20)
                        .page(form.getPage() > 1 ? form.getPage() : 1)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse articleResponse) {
                        List<NewsApiArticlesDto> articles =
                                mapper.convertValue(articleResponse.getArticles()
                                    , new TypeReference<List<NewsApiArticlesDto>>() {
                        });
                        future.complete(new NewsApiResponseDto(
                                articleResponse.getStatus(),
                                articleResponse.getTotalResults(),
                                articles
                        ));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        String message;

                        if (throwable instanceof UnknownHostException) {
                            message = "인터넷 연결을 확인하세요.";
                        } else if (throwable instanceof SocketTimeoutException) {
                            message = "서버 응답이 지연되고 있습니다.";
                        } else {
                            message = "알 수 없는 오류: " + throwable.getMessage();
                        }

                        future.complete(new NewsApiResponseDto("Error", "network_error", message));
                    }
                }
        );

        try {
            return future.get(20, TimeUnit.SECONDS); // 최대 5초 대기 후 결과 리턴
        } catch (TimeoutException e) {
            log.debug("뉴스 API 응답 타임아웃 [{}]", e);
            return new NewsApiResponseDto("error", "timeout", "요청 타임아웃");
        } catch (Exception e){
            log.debug("뉴스 API Exception [{}]", e);
            return new NewsApiResponseDto("error", "exception", e.getMessage());
        }
    }

    @Override
    public NewsApiResponseDto getTopHeadline(NewsApiRequestForm form) {
        CompletableFuture<NewsApiResponseDto> future = new CompletableFuture<>();

        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .country(StringUtils.hasText(form.getCountry()) ? form.getCountry() : null)
                        .category(StringUtils.hasText(form.getCategory()) ? form.getCategory() : null)
                        .q(StringUtils.hasText(form.getQ()) ? form.getQ() : null)
                        .pageSize(form.getPageSize() > 0 ?   form.getPageSize() : 20)
                        .page(form.getPage() > 1 ? form.getPage() : 1)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse articleResponse) {
                        List<NewsApiArticlesDto> articles =
                                mapper.convertValue(articleResponse.getArticles()
                                        , new TypeReference<List<NewsApiArticlesDto>>() {
                                        });
                        future.complete(new NewsApiResponseDto(
                                articleResponse.getStatus(),
                                articleResponse.getTotalResults(),
                                articles
                        ));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        String message;

                        if (throwable instanceof UnknownHostException) {
                            message = "인터넷 연결을 확인하세요.";
                        } else if (throwable instanceof SocketTimeoutException) {
                            message = "서버 응답이 지연되고 있습니다.";
                        } else {
                            message = "알 수 없는 오류: " + throwable.getMessage();
                        }

                        future.complete(new NewsApiResponseDto("Error", "network_error", message));
                    }
                }
        );

        try {
            return future.get(20, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            log.debug("뉴스 API 응답 타임아웃 [{}]", e);
            return new NewsApiResponseDto("error", "timeout", "요청 타임아웃");
        } catch (Exception e){
            log.debug("뉴스 API Exception [{}]", e);
            return new NewsApiResponseDto("error", "exception", e.getMessage());
        }
    }
}
