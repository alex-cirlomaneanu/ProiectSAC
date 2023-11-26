package com.dl.recommendation.cache;

import com.dl.recommendation.vehicle.details.Details;
import com.dl.recommendation.vehicle.details.DetailsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SqlCaching {
    private final static Logger LOG = LoggerFactory.getLogger(SqlCaching.class);
    private final DetailsRepository detailsRepository;
    private static final Map<String, Details> response = new HashMap<>();

    @PostConstruct
    private void getDetails() {
        LOG.info("Caching data from Postgres");
        var vehicles = detailsRepository.findAll();

        for (Details vehicle : vehicles) {
            response.put(vehicle.getId(), vehicle);
        }
    }


    public  Details getDetailsById(String id) {
        return response.get(id);
    }

    public void addDetails(Details details) {
        response.put(details.getId(), details);
    }

    public void removeDetails(String id) {
        response.remove(id);
    }

    public void updateDetails(Details details) {
        response.put(details.getId(), details);
    }

    public Map<String, Details> getResponse() {
        return response;
    }
}
