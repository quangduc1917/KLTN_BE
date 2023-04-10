package com.example.movieba.controller;

import com.example.movieba.model.response.BaseResponse;
import com.example.movieba.service.CvService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2")
public class CvController extends BaseController{

    private final CvService cvService;

    @GetMapping("/total-cvs")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<BaseResponse> totalCvs(){
        return success(cvService.getTotalCvs());
    }

    @GetMapping("/getAll-cvs")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<BaseResponse> getAllCvs(){
        return success(cvService.getAllCvs());
    }
}
