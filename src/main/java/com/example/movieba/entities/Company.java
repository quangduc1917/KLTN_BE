package com.example.movieba.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCompany;

    @Column
    private String nameCompany;

    @Column
    private String addressCompany;

    @Column
    private String listNews;

    @Column(columnDefinition = "BIGINT default 0")
    private long moneyInAccount;

    @OneToOne
    private User user;

    @ManyToMany(mappedBy = "companies")
    private List<Cv> cvs = new ArrayList<>();


}
