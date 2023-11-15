package com.dl.recommendation.vehicle.brand.model;


import com.dl.recommendation.vehicle.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findModelByBrand(Brand brand);
}