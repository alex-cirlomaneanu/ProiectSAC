package com.dl.recommendation.vehicle.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    @Autowired
    private final BrandRepository brandRepository;


    public String addBrand(String name) {
        if (brandRepository.findBrandByName(name).isPresent()) {
            return "Brand already exists";
        }

        brandRepository.save(Brand.builder()
                .name(name)
                .build());

        return "Brand added successfully";
    }


    public BrandResponse getAllBrands() {
        List<Brand> brands = brandRepository.findAll();

        return BrandResponse.builder()
                .allBrands(brands.stream().map(Brand::getName).toList())
                .build();
    }

}
