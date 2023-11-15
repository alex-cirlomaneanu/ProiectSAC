package com.dl.recommendation.collector;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/v1/collector")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
@RestController
public class CollectorController {
    private final CollectorService collectorService;

    @GetMapping(path = "/collect")
    public ResponseEntity<?> getUserRole()  {
        return ResponseEntity.ok(collectorService.collect());
    }
}
