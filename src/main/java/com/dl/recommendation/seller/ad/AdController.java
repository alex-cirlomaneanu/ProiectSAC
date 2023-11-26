package com.dl.recommendation.seller.ad;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api/v1/ads")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdController {
    private final AdService service;

    @GetMapping(path = "/get-count-ads-by-id")
    public ResponseEntity<?> getAdCountById(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        return ResponseEntity.ok(service.getAdCountById(jwtToken));
    }
}
