package com.dl.recommendation.vehicle.details;

import com.dl.recommendation.seller.ad.Ad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Details")
@Table(name = "details")
public class Details {
    @Id
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Ad ad;

    @Column(name = "vin")
    private String VIN;

    @Column(name = "km", nullable = false)
    private String Km;

    @Column(name = "owned")
    private Boolean owned;

    @Column(name = "imported")
    private Boolean imported;

    @Column(name = "right_hand_drive")
    private Boolean rightHandDrive;

    @Column(name = "year")
    private Integer year;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "power")
    private String power;

    @Column(name = "cylinder_capacity")
    private String cylinderCapacity;

    @Column(name = "number_of_doors")
    private Integer numberOfDoors;

    @Column(name = "gearbox")
    private String gearbox;

    @Column(name = "transmission")
    private String transmission;

    @Column(name = "pollution_norm")
    private String pollutionNorm;

    @Column(name = "co2_emission")
    private String co2Emission;

    @Column(name = "body_type")
    private String bodyType;

    @Column(name = "color")
    private String color;

    @Column(name = "color_option")
    private String colorOption;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Column(name = "path_images", nullable = false)
    private String pathImages;

    @Column(name = "images", nullable = false, columnDefinition = "TEXT")
    private String images;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "currency")
    private String currency;

    @Column(name = "price_details")
    private String priceDetails;

    @Column(name = "vehicle_condition")
    private String vehicleCondition;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;
    
    @Column(name = "date_added")
    private Timestamp dateAdded;
    
    @Column(name = "view_360")
    private String view360;
}
