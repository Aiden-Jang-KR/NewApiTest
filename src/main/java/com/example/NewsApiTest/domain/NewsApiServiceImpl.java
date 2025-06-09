package com.example.NewsApiTest.domain;

import com.example.NewsApiTest.web.form.NewsApiArticlesDto;
import com.example.NewsApiTest.web.form.NewsApiRequestForm;
import com.example.NewsApiTest.web.form.NewsApiResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class NewsApiServiceImpl implements NewsApiService {

    private final NewsApiClient newsApiClient;
    private final ObjectMapper mapper;

    @Override
    public NewsApiResponseDto everythingTest(NewsApiRequestForm form) {
        CompletableFuture<NewsApiResponseDto> future = new CompletableFuture<>();

        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(form.getQ())
                        .pageSize(form.getPageSize() > 0 ?   form.getPage() : 20)
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
            return new NewsApiResponseDto("error", "timeout", "요청 타임아웃");
        } catch (Exception e){
            return new NewsApiResponseDto("error", "exception", e.getMessage());
        }
    }
}
