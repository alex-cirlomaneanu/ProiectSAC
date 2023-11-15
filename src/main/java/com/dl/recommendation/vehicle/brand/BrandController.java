package com.dl.recommendation.vehicle.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/v1/vehicles/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BrandController {

    private final BrandService brandService;


    @PostMapping(path = "/addBrand")
    public ResponseEntity<?> addBrand(@RequestParam("brand") String name) {
        return ResponseEntity.ok(brandService.addBrand(name));
    }

    @GetMapping(path = "/getAllBrands")
    public ResponseEntity<?> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }
}
