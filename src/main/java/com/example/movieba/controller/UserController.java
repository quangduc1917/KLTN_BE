package com.example.movieba.controller;

import com.example.movieba.model.request.user.PasswordRequest;
import com.example.movieba.model.request.user.ProfileRequest;
import com.example.movieba.model.response.BaseResponse;
import com.example.movieba.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2")
public class UserController extends BaseController{
    private final UserService userService;

    @PutMapping("/update-pass")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_USER') OR hasAuthority('ROLE_STAFF')")
    ResponseEntity<BaseResponse> changePass(HttpServletRequest request, @RequestBody PasswordRequest passwordRequest){
        return success(userService.updatePassword(request,passwordRequest));
    }

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_USER') OR hasAuthority('ROLE_STAFF')")
    ResponseEntity<BaseResponse> infoUser(HttpServletRequest request){
        return success(userService.infoUser(request));
    }

    @GetMapping("/infoUser/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_USER') OR hasAuthority('ROLE_STAFF')")
    ResponseEntity<BaseResponse> infoUserById(@PathVariable long id){
        return success(userService.infoById(id));
    }


    @GetMapping("/role")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_USER') OR hasAuthority('ROLE_STAFF')")
    ResponseEntity<BaseResponse> getRole(HttpServletRequest request){
        return success(userService.roleUser(request));
    }

    @GetMapping("/getAll-users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_STAFF')")
    ResponseEntity<BaseResponse> getAllUser(@RequestParam int offset, @RequestParam int limit){
        PageRequest pageRequest = PageRequest.of(offset,limit);
        return success(userService.getAll(pageRequest));
    }

    @PutMapping("/update-user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_USER') OR hasAuthority('ROLE_STAFF')")
    ResponseEntity<BaseResponse> updateUser(@RequestBody ProfileRequest profileRequest, HttpServletRequest request){
        return success(userService.updateProfile(request,profileRequest));
    }

    @GetMapping("/total-user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') ")
    ResponseEntity<BaseResponse> getTotalUser(){
        return success(userService.getTotalUser());
    }

    @PutMapping("/add-company/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') ")
    ResponseEntity<BaseResponse> addCompanies(@PathVariable long id, @RequestParam("role") long role){
        userService.addCompany(id,role);
        return success();
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<BaseResponse> changeStatus(){
        return success(userService.getUsers());
    }

    @PutMapping("/changeStatus/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<BaseResponse> changeStatus(@PathVariable long id, @RequestParam("status") long status){
        userService.changeStatusAccount(id,status);
        return success();
    }

}
