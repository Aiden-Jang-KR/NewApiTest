package com.example.NewsApiTest.global.config;

import com.example.NewsApiTest.global.constant.NewsApiConst;
import com.kwabenaberko.newsapilib.NewsApiClient;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public NewsApiClient newsApiClient() {
        return new NewsApiClient(NewsApiConst.NEWS_API_KEY);
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
