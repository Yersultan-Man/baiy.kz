package kz.kopbolsyn.baiy.service;

import kz.kopbolsyn.baiy.dto.BudgetRequest;
import kz.kopbolsyn.baiy.model.*;
import kz.kopbolsyn.baiy.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public Budget create(BudgetRequest req, User user) {
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Budget budget = Budget.builder()
                .user(user)
                .category(category)
                .limitAmount(req.getLimitAmount())
                .spentAmount(BigDecimal.ZERO)
                .period(req.getPeriod())
                .build();

        return budgetRepository.save(budget);
    }

    public List<Budget> getByUser(Long userId) {
        return budgetRepository.findByUserId(userId);
    }

    public void delete(Long id) {
        budgetRepository.deleteById(id);
    }
}