package com.dl.recommendation.vehicle.details;

import com.dl.recommendation.ad.Ad;
import com.dl.recommendation.ad.AdRepository;
import com.dl.recommendation.cache.SqlCaching;
import com.dl.recommendation.image.FileDataService;
import com.dl.recommendation.user.User;
import com.dl.recommendation.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DetailsService {
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(DetailsService.class);

    private final DetailsRepository detailsRepository;
    private final UserService userService;
    private final AdRepository adRepository;
    private final SqlCaching cache;

    public DetailsResponse addVehicle(DetailsRequest detailsRequest, String jwtToken)  {
        User user = userService.getUserByToken(jwtToken);

        if (user == null) {
            LOG.info("User not found");
            return null;
        }

        Details details = DetailsMapper.map(detailsRequest);

        Ad ad = new Ad();
        ad.setId(detailsRequest.getAdId());
        ad.setUser(user);
        adRepository.save(ad);

        details.setAd(ad);
        detailsRepository.save(details);
        cache.addDetails(details);

        LOG.info("Vehicle added successfully");
        return getVehicleById(details.getId());
    }

    public List<DetailsResponse> getAllVehicles() {
        Map<String, Details> vehicles = cache.getResponse();

        List<DetailsResponse> response = new ArrayList<>();

        for (Map.Entry<String, Details> entry : vehicles.entrySet()) {
            response.add(DetailsMapper.mapDetailsResponse(entry.getValue()));
        }
        return response;
    }

    public DetailsResponse getVehicleById(String id) {
        Details vehicle = cache.getDetailsById(id);
        return DetailsMapper.mapDetailsResponse(vehicle);
    }

    public List<DetailsResponse> getVehiclesById(String jwtToken) {
        User user = userService.getUserByToken(jwtToken);

        if (user == null) {
            LOG.info("User not found");
            return null;
        }


        List<Ad> ads = adRepository.getAdsByUserId(user.getId());
        List<DetailsResponse> vehicles = new ArrayList<>();

        for (Ad ad : ads) {
            vehicles.add(getVehicleById(ad.getId()));
        }
        vehicles.sort(Comparator.comparing(DetailsResponse::getDateAdded).reversed());

        return vehicles;
    }

    public List<DetailsResponse> getVehicleByIds(List<String> ids) {
        List<DetailsResponse> vehicles = new ArrayList<>();

        for (String id : ids) {
            vehicles.add(getVehicleById(id));
        }

        return vehicles;
    }
}
