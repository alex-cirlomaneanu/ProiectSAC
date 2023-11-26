package com.dl.recommendation.seller;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerRequest {
    private String phoneNumber;
    private String city;
    private String street;
    private String number;
    private String linkMap;
}
