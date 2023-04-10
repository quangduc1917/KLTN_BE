package com.example.movieba.repository;

import com.example.movieba.entities.CompanyServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyServicesRepository extends JpaRepository<CompanyServices,Long> {
}
