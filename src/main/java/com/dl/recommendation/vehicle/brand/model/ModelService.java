package com.dl.recommendation.vehicle.brand.model;


import com.dl.recommendation.vehicle.brand.Brand;
import com.dl.recommendation.vehicle.brand.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ModelService {
    private ModelRepository repository;
    private BrandRepository brandRepository;

    public String addModel(String name, String brandName) {
        Optional<Brand> brand = brandRepository.findBrandByName(brandName);

        if (brand.isEmpty()) {
            return "Brand not found";
        }

        Model model = Model.builder().model(name)
                .brand(brand.get())
                .build();

        repository.save(model);

        return "Model added";
    }

    public ModelsResponse getModelsByBrand(String brandName) {
        Optional<Brand> brand = brandRepository.findBrandByName(brandName);

        if (brand.isEmpty()) {
            return ModelsResponse.builder().build();
        }
        List<Model> models = repository.findModelByBrand(brand.get());

        return ModelsResponse.builder()
                .models(models.stream().map(Model::getModel).toList())
                .build();
    }
}
