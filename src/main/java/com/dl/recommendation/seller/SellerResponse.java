package com.dl.recommendation.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerResponse {
    private String name;
    private String phoneNumber;
    private String email;
    private String city;
    private String street;
    private String number;
    private String linkMap;
    private String image;
}
