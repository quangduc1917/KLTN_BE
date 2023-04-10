package com.example.movieba.service.Impl;

import com.example.movieba.entities.Cv;
import com.example.movieba.repository.CvRepository;
import com.example.movieba.service.CvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CvServiceImpl implements CvService {
    private final CvRepository cvRepository;
    @Override
    public int getTotalCvs() {
        return cvRepository.findAll().size();
    }

    @Override
    public List<Cv> getAllCvs() {
        return cvRepository.findAll();
    }
}
