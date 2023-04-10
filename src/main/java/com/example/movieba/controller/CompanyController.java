package com.example.movieba.controller;

import com.example.movieba.model.request.company.CompanyRequest;
import com.example.movieba.model.request.services.ServicesRequest;
import com.example.movieba.model.response.BaseResponse;
import com.example.movieba.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2")
public class CompanyController extends BaseController {

    private final CompanyService companyService;

    @PostMapping("/add-company/{id}")
    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    ResponseEntity<BaseResponse> addCompanies(@PathVariable long id, @RequestBody CompanyRequest companyRequest){
        companyService.addInfoCompany(id,companyRequest);
        return success();
    }

    @PutMapping("/update-company/{id}")
    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    ResponseEntity<BaseResponse> updateCompanies(@PathVariable long id, @RequestParam long idCom,@RequestBody CompanyRequest companyRequest){
        companyService.updateCompany(id, idCom, companyRequest);
        return success();
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_STAFF')")
    ResponseEntity<BaseResponse> infoCompanies(@PathVariable long id){
        return success(companyService.getCompanyById(id));
    }


    @GetMapping("/get-all-company")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_USER') OR hasAuthority('ROLE_STAFF')")
    ResponseEntity<BaseResponse> allCompanies(){
        return success(companyService.getAllCompany());
    }

    @GetMapping("/get-all-news/{idCom}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_USER') OR hasAuthority('ROLE_STAFF')")
    ResponseEntity<BaseResponse> allNewCompanies(@PathVariable long idCom){
        return success(companyService.getAllNews(idCom));
    }

    @GetMapping("/total-com")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<BaseResponse> totalCompanies(){
        return success(companyService.totalCom());
    }



}
