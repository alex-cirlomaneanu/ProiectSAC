package com.dl.recommendation.vehicle.details;

import com.dl.recommendation.cache.SqlCaching;
import com.dl.recommendation.image.FileDataService;
import com.dl.recommendation.seller.*;
import com.dl.recommendation.seller.ad.*;
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
    private final SellerService sellerService;
    private final FileDataService fileDataService;
    private final AdRepository adRepository;
    private final SqlCaching cache;

    public DetailsResponse addVehicle(MultipartFile[] files, DetailsRequest detailsRequest, String jwtToken) throws IOException {
        Seller seller = sellerService.getSellerById(jwtToken);

        if (seller == null) {
            LOG.info("Seller not found");
            return null;
        }

        String title = detailsRequest.getBrand() + " " +
                       detailsRequest.getModel() + " " +
                       detailsRequest.getYear() + " " +
                       detailsRequest.getCylinderCapacity() + " " +
                       detailsRequest.getFuelType();

        Map<String, String> imagesAndPath = fileDataService.uploadImage(files, seller.getId(), title);
        Details details = DetailsMapper.map(detailsRequest, imagesAndPath.get("path"), imagesAndPath.get("images"));

        Ad ad = new Ad();
        ad.setId(UUID.randomUUID().toString());
        ad.setSeller(seller);
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
            response.add(DetailsMapper.mapDetailsResponse(entry.getValue(), getArrayImages(entry.getValue().getImages())));
        }
        return response;
    }

    private List<String> getArrayImages(String images) {
        String cleanedInput = images.substring(1, images.length() - 1);
        String[] imagesArray = cleanedInput.split(", ");
        List<String> imagesList = new ArrayList<>();

        for (String s : imagesArray) {
            imagesList.add(s.trim());
        }

        Collections.sort(imagesList);
        return imagesList;
    }

    public DetailsResponse getVehicleById(String id) {
        Details vehicle = cache.getDetailsById(id);
        return DetailsMapper.mapDetailsResponse(vehicle, getArrayImages(vehicle.getImages()));
    }

    public List<DetailsResponse> getVehiclesById(String jwtToken) {
        Seller seller = sellerService.getSellerById(jwtToken);

        if (seller == null) {
            LOG.info("Seller not found");
            return null;
        }


        List<Ad> ads = adRepository.getAdsBySellerId(seller.getId());
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
