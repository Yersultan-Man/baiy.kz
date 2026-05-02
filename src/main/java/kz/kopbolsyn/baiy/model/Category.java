package kz.kopbolsyn.baiy.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String icon;
    private String color;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "category")
    private List<Budget> budgets;

    public enum TransactionType { INCOME, EXPENSE }
}