package com.example.movieba.service;


import com.example.movieba.model.request.news.NewsRequest;
import com.example.movieba.model.response.news.NewsResponse;


import java.util.List;

public interface NewsService {
    void addNew(long idCom, NewsRequest newsRequest);

    void updateNew(long idNew, NewsRequest newsRequest);

    void acceptNew(long idNew, String status);

    NewsResponse getNews(long idNew);

    List<NewsResponse> getAllNews();

    int total();



}
