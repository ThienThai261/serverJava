package com.example.demo22.service;

import com.example.demo22.Model.Category;
import com.example.demo22.Model.Images;
import com.example.demo22.Rep.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getListCate() {
        return categoryRepository.findAll();
    }

    public Category getCate(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}
