package com.example.demo22.Controller;


import com.example.demo22.Model.Images;
import com.example.demo22.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imgsa")
@CrossOrigin(origins = "*")
public class ImgController {
    @Autowired
    private ImageService imageService;

    @GetMapping
    List<Images> getListImg() { return imageService.getListImg(); }

    @GetMapping("/{id}")
    Images getImg(@PathVariable("id") int id) {
        return imageService.getImg(id);
    }
}
