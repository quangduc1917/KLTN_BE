package com.example.movieba.service.Impl;

import com.example.movieba.entities.Company;
import com.example.movieba.entities.News;
import com.example.movieba.entities.User;
import com.example.movieba.exception.BusinessCode;
import com.example.movieba.exception.BusinessException;
import com.example.movieba.mapper.CompanyMapper;
import com.example.movieba.mapper.NewsMapper;
import com.example.movieba.model.request.company.CompanyRequest;
import com.example.movieba.model.response.company.CompanyResponse;
import com.example.movieba.model.response.news.NewsResponse;
import com.example.movieba.repository.CompanyRepository;
import com.example.movieba.repository.NewsRepository;
import com.example.movieba.repository.UserRepository;
import com.example.movieba.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserRepository userRepository;

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    @Override
    public void addInfoCompany(long idUser, CompanyRequest companyRequest) {
        Optional<User> users = userRepository.findById(idUser);
        users.orElseThrow(()-> new BusinessException(BusinessCode.USER_NOT_FOUND));

        if (!users.isEmpty()){
            Company company = companyMapper.to(companyRequest);
            company.setUser(users.get());
            companyRepository.save(company);
        }

    }

    @Override
    public void updateCompany(long idUser, long idCompany, CompanyRequest companyRequest) {
        Optional<User> findUsers = userRepository.findById(idUser);
        findUsers.orElseThrow(()-> new BusinessException(BusinessCode.USER_NOT_FOUND));

        Optional<Company> findCompany = companyRepository.findByIdCompanyAndUser(idCompany,findUsers.get());
        findCompany.orElseThrow(()-> new BusinessException(BusinessCode.USER_NOT_FOUND));




        if (!findCompany.isEmpty()){
            Company company = findCompany.get();
            company.setAddressCompany(companyRequest.getAddressCompany());
            company.setNameCompany(companyRequest.getNameCompany());
            companyRepository.save(company);
        }
    }

    @Override
    public CompanyResponse getCompanyById(long idCompany) {
        Optional<Company> company = companyRepository.findById(idCompany);
        if (!company.isEmpty()){
            CompanyResponse companyResponse = companyMapper.from(company.get());
            companyResponse.setUserName(company.get().getUser().getUserName());
            return  companyResponse;
        }
        return null;
    }

    @Override
    public List<CompanyResponse> getAllCompany() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(t->{
            CompanyResponse companyResponse = companyMapper.from(t);
            companyResponse.setUserName(t.getUser().getUserName());
            return companyResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public List<NewsResponse> getAllNews(long idCom) {
        Optional<Company> findCom = companyRepository.findById(idCom);
        List<Long> ids = Arrays.stream(findCom.get().getListNews().split(",")).map(t-> Long.parseLong(t)).collect(Collectors.toList());
        List<News> listNews = newsRepository.findAllByIdNewIsIn(ids);
        return listNews.stream().map(t->{
            NewsResponse newsResponse = newsMapper.from(t);
            return newsResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public int totalCom() {
        return companyRepository.findAll().size();
    }
}
