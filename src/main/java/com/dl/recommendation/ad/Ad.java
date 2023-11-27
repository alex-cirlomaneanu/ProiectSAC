package com.dl.recommendation.ad;

import com.dl.recommendation.user.User;
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
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
    private Details details;

    @Override
    public String toString() {
        return "Ad{" +
                "id='" + id + '\'' +
                "user.id='" + user.getId() + '\'' +
                '}';
    }
}
