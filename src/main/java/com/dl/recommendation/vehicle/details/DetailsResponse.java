package com.dl.recommendation.vehicle.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailsResponse {
   private String title;
   private String price;
   private String currency;
   private String path;
   private String id;
   private String VIN;
   private String Km;
   private Boolean owned;
   private Boolean imported;
   private Boolean rightHandDrive;
   private Integer year;
   private String brand;
   private String model;
   private String fuelType;
   private String power;
   private String cylinderCapacity;
   private Integer numberOfDoors;
   private String gearbox;
   private String transmission;
   private String pollutionNorm;
   private String co2Emission;
   private String bodyType;
   private String color;
   private String colorOption;
   private Integer numberOfSeats;
   private String description;
   private String priceDetails;
   private String vehicle_condition;
   private String countryOfOrigin;
   private List<String> images;
   private Timestamp dateAdded;
   private String view360;

}
