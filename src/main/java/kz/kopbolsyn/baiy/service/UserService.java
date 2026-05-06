package kz.kopbolsyn.baiy.service;

import kz.kopbolsyn.baiy.dto.*;
import kz.kopbolsyn.baiy.model.User;
import kz.kopbolsyn.baiy.repository.UserRepository;
import kz.kopbolsyn.baiy.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email уже используется");

        String username = (req.getUsername() != null && !req.getUsername().isBlank())
                ? req.getUsername()
                : req.getEmail().split("@")[0];

        User user = User.builder()
                .username(username)
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .middleName(req.getMiddleName())
                .salary(req.getSalary())
                .telegram(req.getTelegram())
                .role(User.Role.USER)
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, toDto(user));
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Неверный пароль");
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, toDto(user));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateProfile(String email, ProfileUpdateRequest req) {
        User user = getCurrentUser(email);
        if (req.getFirstName()  != null) user.setFirstName(req.getFirstName());
        if (req.getLastName()   != null) user.setLastName(req.getLastName());
        if (req.getMiddleName() != null) user.setMiddleName(req.getMiddleName());
        if (req.getSalary()     != null) user.setSalary(req.getSalary());
        user.setTelegram(req.getTelegram());
        return userRepository.save(user);
    }

    private UserDto toDto(User u) {
        return UserDto.builder()
                .id(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .middleName(u.getMiddleName())
                .salary(u.getSalary())
                .telegram(u.getTelegram())
                .createdAt(u.getCreatedAt())
                .build();
    }
}