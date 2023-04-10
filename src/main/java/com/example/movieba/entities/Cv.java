package com.example.movieba.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter

@Entity
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCv;

    @Column
    private String nameFile;

    @Column(columnDefinition = "text")
    private String pathFile;

    @Column
    private Date createTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cv_companies", joinColumns = @JoinColumn(name = "id_cv", referencedColumnName = "idCv"),
            inverseJoinColumns = @JoinColumn(name = "id_company", referencedColumnName = "idCompany"))
    List<Company> companies = new ArrayList<>();

}
