package com.dl.recommendation.authentication;

import com.dl.recommendation.user.*;
import com.dl.recommendation.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .id(UUID.randomUUID().toString())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.bCryptPasswordEncoder().encode(request.getPassword()))
                .role(UserRole.USER)
                .locked(false)
                .enabled(false)
                .build();

        String error = "";
        String jwtTokeb = "";
        if (!userService.singUpUser(user)) {
            error = "Email taken";
        } else {
            jwtTokeb = jwtService.generateToken(user);
        }

        return AuthenticationResponse.builder()
                .token(jwtTokeb)
                .error(error)
                .activate(true)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userService.getUser(request.getEmail());

        if (user == null) {
            return AuthenticationResponse.builder()
                    .error("Email does not exist")
                    .activate(false)
                    .build();
        }

        if (!user.getEmail().equals(request.getEmail()) ||
                !passwordEncoder.bCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
            return AuthenticationResponse.builder()
                    .error("Email or password does not match")
                    .activate(false)
                    .build();
        }

        return authenticateUser(user, request.getPassword());
    }

    private AuthenticationResponse authenticateUser(User user, String password) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    password
            )
        );

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .activate(true)
                .build();
        }
    }
