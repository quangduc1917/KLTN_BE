package com.example.movieba.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;


@Setter
@Getter

@Entity
public class CompanyServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idComService;

    @Column
    private Date timeStart;

    @Column
    private Date timeEnd;

    @OneToOne
    private Company company;

    @OneToOne
    private Services services;
}
