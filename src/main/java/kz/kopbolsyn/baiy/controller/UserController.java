package kz.kopbolsyn.baiy.controller;

import kz.kopbolsyn.baiy.dto.ProfileUpdateRequest;
import kz.kopbolsyn.baiy.model.User;
import kz.kopbolsyn.baiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getCurrentUser(userDetails.getUsername()));
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody ProfileUpdateRequest req,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        User updated = userService.updateProfile(userDetails.getUsername(), req);
        return ResponseEntity.ok(updated);
    }
}