package com.dl.recommendation.seller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/api/v1/sellers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SellerController {
    private final SellerService sellerService;

    @PostMapping(path = "/add-seller")
    public ResponseEntity<?> addSeller(@ModelAttribute SellerRequest seller,
                                       @RequestParam("image") MultipartFile file,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        return ResponseEntity.ok(sellerService.addSeller(file, seller, jwtToken));
    }

    @GetMapping(path = "get-seller")
    public ResponseEntity<?> getSeller(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        return ResponseEntity.ok(sellerService.getSeller(jwtToken));
    }


    @GetMapping(path = "get-seller-by-id/{id}")
    public ResponseEntity<?> getSellerById(@PathVariable String id) {
        return ResponseEntity.ok(sellerService.getSellerByDetailsId(id));
    }

}
