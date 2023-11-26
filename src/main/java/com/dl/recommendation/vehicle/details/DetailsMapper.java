package com.dl.recommendation.vehicle.details;


import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Mapper
public class DetailsMapper {
    private static final String PATH = "/home/alex/Desktop/frontend/auto-trade/public/";

    public static Details map(DetailsRequest detailsRequest, String pathImages, String images) {
        return Details.builder()
                .id(UUID.randomUUID().toString())
                .VIN(detailsRequest.getVIN())
                .Km(detailsRequest.getKm())
                .owned(detailsRequest.getOwned())
                .imported(detailsRequest.getImported())
                .rightHandDrive(detailsRequest.getRightHandDrive())
                .year(detailsRequest.getYear())
                .brand(detailsRequest.getBrand())
                .model(detailsRequest.getModel())
                .fuelType(detailsRequest.getFuelType())
                .power(detailsRequest.getPower())
                .cylinderCapacity(detailsRequest.getCylinderCapacity())
                .numberOfDoors(detailsRequest.getNumberOfDoors())
                .gearbox(detailsRequest.getGearbox())
                .transmission(detailsRequest.getTransmission())
                .pollutionNorm(detailsRequest.getPollutionNorm())
                .co2Emission(detailsRequest.getCo2Emission())
                .bodyType(detailsRequest.getBodyType())
                .color(detailsRequest.getColor())
                .colorOption(detailsRequest.getColorOption())
                .numberOfSeats(detailsRequest.getNumberOfSeats())
                .pathImages(pathImages)
                .images(images)
                .description(detailsRequest.getDescription())
                .price(detailsRequest.getPrice())
                .currency(detailsRequest.getCurrency())
                .priceDetails(detailsRequest.getPriceDetails())
                .vehicleCondition(detailsRequest.getVehicle_condition())
                .countryOfOrigin(detailsRequest.getCountryOfOrigin())
                .dateAdded(new Timestamp(System.currentTimeMillis()))
                .view360(detailsRequest.getView360())
                .build();
    }


    public static DetailsResponse mapDetailsResponse(Details vehicle, List<String> imagesList) {
        return DetailsResponse.builder()
                .title(vehicle.getBrand() + " " +
                        vehicle.getModel() + " " +
                        vehicle.getYear() + " " +
                        vehicle.getCylinderCapacity() + " " +
                        vehicle.getFuelType())
                .price(vehicle.getPrice())
                .currency(vehicle.getCurrency())
                .path(vehicle.getPathImages().substring(PATH.length()))
                .id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .fuelType(vehicle.getFuelType())
                .power(vehicle.getPower())
                .cylinderCapacity(vehicle.getCylinderCapacity())
                .numberOfDoors(vehicle.getNumberOfDoors())
                .gearbox(vehicle.getGearbox())
                .transmission(vehicle.getTransmission())
                .pollutionNorm(vehicle.getPollutionNorm())
                .co2Emission(vehicle.getCo2Emission())
                .bodyType(vehicle.getBodyType())
                .color(vehicle.getColor())
                .colorOption(vehicle.getColorOption())
                .numberOfSeats(vehicle.getNumberOfSeats())
                .description(vehicle.getDescription())
                .priceDetails(vehicle.getPriceDetails())
                .vehicle_condition(vehicle.getVehicleCondition())
                .countryOfOrigin(vehicle.getCountryOfOrigin())
                .Km(vehicle.getKm())
                .owned(vehicle.getOwned())
                .imported(vehicle.getImported())
                .rightHandDrive(vehicle.getRightHandDrive())
                .VIN(vehicle.getVIN())
                .images(imagesList)
                .dateAdded(vehicle.getDateAdded())
                .view360(vehicle.getView360())
                .build();
    }
}
