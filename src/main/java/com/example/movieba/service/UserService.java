package com.example.movieba.service;

import com.example.movieba.model.request.user.PasswordRequest;
import com.example.movieba.model.request.user.ProfileRequest;
import com.example.movieba.model.request.user.UserInfoRequest;
import com.example.movieba.model.response.UserInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    String createUser(UserInfoRequest infoRequest);

    String createCompany(UserInfoRequest infoRequest);

    UserInfoResponse infoUser(HttpServletRequest request);

    UserInfoResponse infoById(long id);

    String updatePassword(HttpServletRequest request, PasswordRequest passwordRequest);

    UserInfoResponse updateProfile(HttpServletRequest request, ProfileRequest profileRequest);

    List<String> roleUser(HttpServletRequest request);

    Page<UserInfoResponse> getAll(Pageable pageable);

    List<UserInfoResponse> getUsers();

    int getTotalUser();

    void addCompany(long id, long role);

    void changeStatusAccount(long id, long status);


}
