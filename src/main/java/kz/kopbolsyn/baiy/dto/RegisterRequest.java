package kz.kopbolsyn.baiy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class RegisterRequest {

    // Required for login
    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 6)
    private String password;

    // Profile fields (optional username fallback)
    private String username;

    // Name fields
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String middleName;

    // Optional extras
    private BigDecimal salary;
    private String telegram;
}