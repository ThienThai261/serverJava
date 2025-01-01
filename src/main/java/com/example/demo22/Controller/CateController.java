package com.example.demo22.Controller;

import com.example.demo22.Model.Category;
import com.example.demo22.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cate")
@CrossOrigin(origins = "*")
public class CateController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(path = "/all")
    List<Category> getListCate() { return categoryService.getListCate(); }

    @GetMapping("/{id}")
    Category getCate(@PathVariable("id") int id) {
        return categoryService.getCate(id);
    }
}
