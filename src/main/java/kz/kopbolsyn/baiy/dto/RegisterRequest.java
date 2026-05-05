package kz.kopbolsyn.baiy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class RegisterRequest {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    private String middleName;

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 6)
    private String password;

    private BigDecimal salary;
    private String telegram;
}