package kz.kopbolsyn.baiy.controller;

import kz.kopbolsyn.baiy.model.Account;
import kz.kopbolsyn.baiy.model.User;
import kz.kopbolsyn.baiy.repository.AccountRepository;
import kz.kopbolsyn.baiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody Account account,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails.getUsername());
        account.setUser(user);
        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }
        return ResponseEntity.ok(accountRepository.save(account));
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails.getUsername());
        return ResponseEntity.ok(accountRepository.findByUserId(user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails.getUsername());
        accountRepository.findById(id).ifPresent(acc -> {
            if (acc.getUser().getId().equals(user.getId())) {
                accountRepository.deleteById(id);
            }
        });
        return ResponseEntity.noContent().build();
    }
}