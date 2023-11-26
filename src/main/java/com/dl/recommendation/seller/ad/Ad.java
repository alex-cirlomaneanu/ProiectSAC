package com.dl.recommendation.seller.ad;

import com.dl.recommendation.seller.Seller;
import com.dl.recommendation.vehicle.details.Details;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Ad")
@Table(name = "ad")
public class Ad {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToOne(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
    private Details details;

    @Override
    public String toString() {
        return "Ad{" +
                "id='" + id + '\'' +
                "seller.id='" + seller.getId() + '\'' +
                '}';
    }
}
