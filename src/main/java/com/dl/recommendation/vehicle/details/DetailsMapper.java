package com.dl.recommendation.vehicle.details;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.util.UUID;

@Mapper
public class DetailsMapper {
    public static Details map(DetailsRequest detailsRequest) {
        return Details.builder()
                .id(UUID.randomUUID().toString())
                .Km(detailsRequest.getKm())
                .owned(detailsRequest.getOwned())
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
                .pathImages(detailsRequest.getPathImages())
                .description(detailsRequest.getDescription())
                .price(detailsRequest.getPrice())
                .vehicleCondition(detailsRequest.getVehicleCondition())
                .countryOfOrigin(detailsRequest.getCountryOfOrigin())
                .dateAdded(new Timestamp(System.currentTimeMillis()))
                .build();
    }


    public static DetailsResponse mapDetailsResponse(Details vehicle) {
        return DetailsResponse.builder()
                .title(vehicle.getBrand() + " " +
                        vehicle.getModel() + " " +
                        vehicle.getYear() + " " +
                        vehicle.getCylinderCapacity() + " " +
                        vehicle.getFuelType())
                .price(vehicle.getPrice())
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
                .vehicle_condition(vehicle.getVehicleCondition())
                .countryOfOrigin(vehicle.getCountryOfOrigin())
                .Km(vehicle.getKm())
                .owned(vehicle.getOwned())
                .dateAdded(vehicle.getDateAdded())
                .pathImages(vehicle.getPathImages())
                .build();
    }
}
