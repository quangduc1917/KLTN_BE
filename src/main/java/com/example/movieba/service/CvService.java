package com.example.movieba.service;

import com.example.movieba.entities.Cv;

import java.util.List;

public interface CvService {
    int getTotalCvs();

    List<Cv> getAllCvs();

}
