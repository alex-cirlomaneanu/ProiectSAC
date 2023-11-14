package com.dl.recommendation.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/get-user-role")
    public ResponseEntity<?> getUserRole(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        return ResponseEntity.ok(userService.getUserRole(jwtToken));
    }

    @PostMapping(path = "/change-user-role")
    public ResponseEntity<?> changeUserRole(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        return ResponseEntity.ok(userService.changeUserRole(jwtToken));
    }
}
