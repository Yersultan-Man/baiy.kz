package kz.kopbolsyn.baiy.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "budgets")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private BigDecimal limitAmount;

    private BigDecimal spentAmount = BigDecimal.ZERO;

    private String period;
}