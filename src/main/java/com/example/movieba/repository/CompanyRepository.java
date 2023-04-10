package com.example.movieba.repository;

import com.example.movieba.entities.Company;
import com.example.movieba.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Optional<Company> findByIdCompanyAndUser(long idCom, User user);

    Optional<Company> findByUser(User user);
}
