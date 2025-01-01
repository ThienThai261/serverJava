package com.example.demo22.service;

import com.example.demo22.Model.Images;
import com.example.demo22.Rep.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public List<Images> getListImg() {
        return imageRepository.findAll();
    }

    public Images getImg(int id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

}

