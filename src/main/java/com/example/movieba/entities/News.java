package com.example.movieba.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNew;

    @Column
    private String titleNew;

    @Column(columnDefinition = "text")
    private String describeNew;

    @Column
    private String statusNew;

    @Column
    private Date timeNews;

}
