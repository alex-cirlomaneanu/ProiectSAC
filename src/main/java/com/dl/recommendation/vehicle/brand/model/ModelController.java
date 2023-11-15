package com.dl.recommendation.vehicle.brand.model;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/v1/vehicles/brands/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ModelController {

    private final ModelService modelService;

    @PostMapping(path = "/addModel")
    public ResponseEntity<?> addModel(@RequestParam("brand") String brandName,
                                      @RequestParam("model") String name) {
        return ResponseEntity.ok(modelService.addModel(name, brandName));
    }

    @GetMapping(path = "/getModelsByBrand")
    public ResponseEntity<?> getModelsByBrand(@RequestParam("brand") String brandName) {
        return ResponseEntity.ok(modelService.getModelsByBrand(brandName));
    }
}
