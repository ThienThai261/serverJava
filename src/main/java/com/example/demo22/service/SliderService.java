package com.example.demo22.service;

import com.example.demo22.Model.Slider_imgs;
import com.example.demo22.Rep.SliderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SliderService {

    private final SliderRepository sliderRepository;

    @Autowired
    public SliderService(SliderRepository sliderRepository) {
        this.sliderRepository = sliderRepository;
    }

    public List<Slider_imgs> getAllSliderImgs() {
        return sliderRepository.findAll();
    }
}
