package kz.kopbolsyn.baiy.dto;

import kz.kopbolsyn.baiy.model.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    @NotNull @Positive
    private BigDecimal amount;

    @NotNull
    private Category.TransactionType type;

    @NotNull
    private Long accountId;

    private Long categoryId;
    private String description;
    private LocalDate date;
}