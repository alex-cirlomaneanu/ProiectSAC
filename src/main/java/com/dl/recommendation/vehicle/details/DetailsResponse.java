package com.dl.recommendation.vehicle.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailsResponse {
   private String title;
   private String price;
   private String pathImages;
   private String id;
   private String Km;
   private String owned;
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
   private String vehicle_condition;
   private String countryOfOrigin;
   private Timestamp dateAdded;
}
