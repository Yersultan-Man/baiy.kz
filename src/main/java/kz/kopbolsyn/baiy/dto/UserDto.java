package kz.kopbolsyn.baiy.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String telegram;
    private BigDecimal salary;
    private LocalDateTime createdAt;
}