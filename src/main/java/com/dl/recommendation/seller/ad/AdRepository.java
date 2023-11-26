package com.dl.recommendation.seller.ad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, String> {
    List<Ad> getAdsBySellerId(String userId);

    Optional<Ad> getAdById(String id);
}
