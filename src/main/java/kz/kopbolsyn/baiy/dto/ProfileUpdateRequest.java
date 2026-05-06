package kz.kopbolsyn.baiy.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProfileUpdateRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private BigDecimal salary;
    private String telegram;
}