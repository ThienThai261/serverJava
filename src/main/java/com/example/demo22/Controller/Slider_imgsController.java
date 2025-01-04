package com.example.demo22.Controller;

import com.example.demo22.Model.Slider_imgs;
import com.example.demo22.service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slider")
@CrossOrigin(origins = "*")
public class Slider_imgsController {

    private final SliderService sliderService;

    @Autowired
    public Slider_imgsController(SliderService sliderService) {
        this.sliderService = sliderService;
    }

    @GetMapping(path = "/all")
    public @ResponseBody List<Slider_imgs> getAllSliderImgs() {
        return sliderService.getAllSliderImgs();
    }
}
