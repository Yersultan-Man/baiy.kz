package kz.kopbolsyn.baiy.controller;

import kz.kopbolsyn.baiy.dto.BudgetRequest;
import kz.kopbolsyn.baiy.model.*;
import kz.kopbolsyn.baiy.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Budget> create(@Valid @RequestBody BudgetRequest req,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails.getUsername());
        return ResponseEntity.ok(budgetService.create(req, user));
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails.getUsername());
        return ResponseEntity.ok(budgetService.getByUser(user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        budgetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}