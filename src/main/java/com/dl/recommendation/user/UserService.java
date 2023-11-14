package com.dl.recommendation.user;

import com.dl.recommendation.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final static Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;
    private final JwtService jwtService;

    public Boolean singUpUser(User user) {
        boolean userExists = repository
                .findUserByEmail(user.getEmail())
                .isPresent();
        if (userExists) {
            LOG.error("Email already taken");
            return false;
        }
        repository.save(user);
        return true;
    }

    public User getUserByToken(String jwtToken) {
        String userEmail = jwtService.extractUsername(jwtToken.substring(7));
        Optional<User> user = repository.findUserByEmail(userEmail);

        return user.orElse(null);
    }

    public User getUser(String email) {
        Optional<User> appUser = repository
                .findUserByEmail(email);
        if (appUser.isEmpty()) {
            LOG.error("The email address does not exist");
            return null;
        }
        return appUser.get();
    }


    public String getUserRole(String jwtToken) {
        User user = getUserByToken(jwtToken);

        return user.getRole().toString();
    }

    public String changeUserRole(String jwtToken) {
        User user = getUserByToken(jwtToken);

        if (user.getRole() == UserRole.USER) {
            user.setRole(UserRole.SELLER);
        } else {
            user.setRole(UserRole.USER);
        }

        repository.save(user);
        return "Role changed successfully";
    }
}
