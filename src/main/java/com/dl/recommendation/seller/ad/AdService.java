package com.dl.recommendation.seller.ad;

import com.dl.recommendation.user.User;
import com.dl.recommendation.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService {
    private final AdRepository adRepository;
    private final UserService userService;

    public int getAdCountById(String jwtToken) {
        User user = userService.getUserByToken(jwtToken);

        if (user == null) {
            return -1;
        }

        return adRepository.getAdsBySellerId(user.getId()).size();
    }

    public List<Ad> getAdsBySellerId(String userId) {
        return adRepository.getAdsBySellerId(userId);
    }


    public Ad getAdById(String id) {
        return adRepository.findById(id).orElse(null);
    }
}
