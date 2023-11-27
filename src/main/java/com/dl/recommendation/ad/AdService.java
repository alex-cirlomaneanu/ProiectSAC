package com.dl.recommendation.ad;

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

        return adRepository.getAdsByUserId(user.getId()).size();
    }

    public List<Ad> getAdsByUserId(String userId) {
        return adRepository.getAdsByUserId(userId);
    }


    public Ad getAdById(String id) {
        return adRepository.findById(id).orElse(null);
    }
}
