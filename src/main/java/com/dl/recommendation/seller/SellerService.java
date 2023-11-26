package com.dl.recommendation.seller;


import com.dl.recommendation.image.FileDataService;
import com.dl.recommendation.seller.ad.Ad;
import com.dl.recommendation.seller.ad.AdService;
import com.dl.recommendation.user.User;
import com.dl.recommendation.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final static Logger LOG = LoggerFactory.getLogger(SellerService.class);

    private static final String PATH = "/home/alex/Desktop/frontend/auto-trade/public/";

    private final SellerRepository sellerRepository;
    private final UserService userService;
    private final FileDataService fileDataService;
    private final AdService adService;

    private void updateSellerById(Seller seller, Seller newSeller) {
        seller.setName(newSeller.getName());
        seller.setEmail(newSeller.getEmail());
        seller.setPhoneNumber(newSeller.getPhoneNumber());
        seller.setCity(newSeller.getCity());
        seller.setStreet(newSeller.getStreet());
        seller.setNumber(newSeller.getNumber());
        seller.setLinkMap(newSeller.getLinkMap());
        seller.setImage(newSeller.getImage());
        sellerRepository.save(seller);
    }


    public SellerResponse addSeller(MultipartFile file, SellerRequest seller, String jwtToken) {
        User user = userService.getUserByToken(jwtToken);

        if (user == null) {
            LOG.info("User not found");
            return null;
        }


        String pathImage = null;
        try {
            pathImage = fileDataService.uploadSellerImage(file, user.getId());
        } catch (IOException ignored) {}

        Seller newSeller = Seller.builder()
                .id(user.getId())
                    .name(user.getFirstname() + " " + user.getLastname())
                .email(user.getEmail())
                .phoneNumber(seller.getPhoneNumber())
                .city(seller.getCity())
                .street(seller.getStreet())
                .number(seller.getNumber())
                .linkMap(seller.getLinkMap())
                .image(pathImage)
                .build();

        Optional<Seller> sellerOptional = sellerRepository.findById(user.getId());

        if (sellerOptional.isPresent()) {
            LOG.info("Update seller");
            updateSellerById(sellerOptional.get(), newSeller);
        } else {
            sellerRepository.save(newSeller);
            userService.changeUserRole(jwtToken);
            LOG.info("Seller added successfully");
        }

        return SellerResponse.builder()
                .name(newSeller.getName())
                .email(newSeller.getEmail())
                .phoneNumber(newSeller.getPhoneNumber())
                .city(newSeller.getCity())
                .street(newSeller.getStreet())
                .number(newSeller.getNumber())
                .linkMap(newSeller.getLinkMap())
                .image(newSeller.getImage().substring(PATH.length()))
                .build();
    }


    public Seller getSellerById(String jwtToken) {
        User user = userService.getUserByToken(jwtToken);

        if (user == null) {
            return null;
        }

        return sellerRepository.findById(user.getId()).orElse(null);
    }


    public SellerResponse getSellerByDetailsId(String id) {
        Ad ad = adService.getAdById(id);

        Seller seller = sellerRepository.findById(ad.getSeller().getId()).orElse(null);

        return SellerResponse.builder()
                .name(seller.getName())
                .email(seller.getEmail())
                .phoneNumber(seller.getPhoneNumber())
                .city(seller.getCity())
                .street(seller.getStreet())
                .number(seller.getNumber())
                .linkMap(seller.getLinkMap())
                .image(seller.getImage().substring(PATH.length()))
                .build();
    }

    public SellerResponse getSeller(String jwtToken) {
        Seller seller = getSellerById(jwtToken);
        if (seller == null) {
            return null;
        }

        return SellerResponse.builder()
                .name(seller.getName())
                .email(seller.getEmail())
                .phoneNumber(seller.getPhoneNumber())
                .city(seller.getCity())
                .street(seller.getStreet())
                .number(seller.getNumber())
                .linkMap(seller.getLinkMap())
                .image(seller.getImage().substring(PATH.length()))
                .build();
    }

}
