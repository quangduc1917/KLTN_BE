package com.example.movieba.service;


import com.example.movieba.model.request.company.CompanyRequest;
import com.example.movieba.model.response.company.CompanyResponse;
import com.example.movieba.model.response.news.NewsResponse;

import java.util.List;

public interface CompanyService {

    void addInfoCompany(long idUser, CompanyRequest companyRequest);

    void updateCompany(long idUser, long idCompany, CompanyRequest companyRequest);

    CompanyResponse getCompanyById(long idCompany);

    List<CompanyResponse> getAllCompany();

    List<NewsResponse> getAllNews( long idCom);

    int totalCom();
}
