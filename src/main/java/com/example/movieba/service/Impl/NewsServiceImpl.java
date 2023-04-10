package com.example.movieba.service.Impl;

import com.example.movieba.entities.Company;
import com.example.movieba.entities.News;
import com.example.movieba.exception.BusinessCode;
import com.example.movieba.exception.BusinessException;
import com.example.movieba.mapper.NewsMapper;
import com.example.movieba.model.request.news.NewsRequest;
import com.example.movieba.model.response.news.NewsResponse;
import com.example.movieba.repository.CompanyRepository;
import com.example.movieba.repository.NewsRepository;
import com.example.movieba.repository.UserRepository;
import com.example.movieba.service.NewsService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final UserRepository userRepository;
    private final NewsMapper newsMapper;
    private final NewsRepository newsRepository;

    private final CompanyRepository companyRepository;

    @Override
    public void addNew(long idCom, NewsRequest newsRequest) {
        Optional<Company> findComs = companyRepository.findById(idCom);
        findComs.orElseThrow(()-> new BusinessException(BusinessCode.USER_NOT_FOUND));

        Date date = new Date();

        News news = newsMapper.to(newsRequest);
        news.setStatusNew("wait");
        news.setTimeNews(date);
        newsRepository.save(news);

        Optional<News> findNews = newsRepository.findByTimeNews(date);
        StringBuilder builder = new StringBuilder(findComs.get().getListNews());
        builder.append(findNews.get().getIdNew()).append(",");

        Company company = findComs.get();
        company.setListNews(builder.toString());

        companyRepository.save(company);

    }

    @Override
    public void updateNew(long idNew, NewsRequest newsRequest) {
        Optional<News> findNews = newsRepository.findById(idNew);
        findNews.orElseThrow(()-> new BusinessException(BusinessCode.USER_NOT_FOUND));

        if (!findNews.isEmpty()){
            News news = findNews.get();
            news.setDescribeNew(newsRequest.getDescribeNew());
            news.setTitleNew(news.getTitleNew());
            newsRepository.save(news);
        }
    }

    @Override
    public void acceptNew(long idNew, String status) {
        Optional<News> findNews = newsRepository.findById(idNew);
        findNews.orElseThrow(()-> new BusinessException(BusinessCode.USER_NOT_FOUND));

        if (!findNews.isEmpty()){
            News news = findNews.get();
            news.setStatusNew(status);
            newsRepository.save(news);
        }
    }

    @Override
    public NewsResponse getNews(long idNew) {
        Optional<News> findNews = newsRepository.findById(idNew);
        findNews.orElseThrow(()-> new BusinessException(BusinessCode.USER_NOT_FOUND));
        return newsMapper.from(findNews.get());
    }

    @Override
    public List<NewsResponse> getAllNews() {
        List<News> listNews = newsRepository.findAll();
        return listNews.stream().map(t-> {
            NewsResponse newsResponse = newsMapper.from(t);
            return  newsResponse;
        }).sorted(Comparator.comparingLong(NewsResponse::getIdNew).reversed()).collect(Collectors.toList());
    }

    @Override
    public int total() {
        return newsRepository.findAll().size();
    }


}
