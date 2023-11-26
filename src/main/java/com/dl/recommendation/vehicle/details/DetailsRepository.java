package com.dl.recommendation.vehicle.details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailsRepository extends JpaRepository<Details, String> {
    @Override
    Optional<Details> findById(String s);
}
