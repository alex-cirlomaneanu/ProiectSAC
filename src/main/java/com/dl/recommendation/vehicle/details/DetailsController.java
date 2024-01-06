package com.dl.recommendation.vehicle.details;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/vehicle")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DetailsController {
    private final DetailsService service;

    @PostMapping(path = "/uploadVehicle")
    public ResponseEntity<?> uploadCar(@ModelAttribute DetailsRequest request,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        return ResponseEntity.ok(service.addVehicle(request, jwtToken));
    }

    @GetMapping(path = "/getAllVehicles")
    public ResponseEntity<?> getVehicle() {
        return ResponseEntity.ok(service.getAllVehicles());
    }

    @GetMapping(path = "/getVehicle/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable String id) {
        return ResponseEntity.ok(service.getVehicleById(id));
    }

    @GetMapping("/getUserVehicles")
    public ResponseEntity<?> getVehiclesById(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        return ResponseEntity.ok(service.getVehiclesById(jwtToken));
    }

    @GetMapping("/getVehicles")
    public ResponseEntity<?> getItemsByIds(@RequestParam("ids") List<String> ids) {
        return ResponseEntity.ok(service.getVehicleByIds(ids));
    }
}
