package kz.kopbolsyn.baiy.service;

import kz.kopbolsyn.baiy.dto.TransactionRequest;
import kz.kopbolsyn.baiy.exception.ResourceNotFoundException;
import kz.kopbolsyn.baiy.model.*;
import kz.kopbolsyn.baiy.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Transaction create(TransactionRequest req, User currentUser) {
        Account account = accountRepository.findById(req.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("This account does not belong to you");
        }

        Transaction tx = Transaction.builder()
                .account(account)
                .amount(req.getAmount())
                .type(req.getType())
                .description(req.getDescription())
                .date(req.getDate() != null ? req.getDate() : LocalDate.now())
                .build();

        if (req.getCategoryId() != null) {
            Category cat = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            tx.setCategory(cat);
        }

        if (req.getType() == Category.TransactionType.INCOME) {
            account.setBalance(account.getBalance().add(req.getAmount()));
        } else {
            account.setBalance(account.getBalance().subtract(req.getAmount()));
        }
        accountRepository.save(account);

        return transactionRepository.save(tx);
    }

    public List<Transaction> getAllByUser(Long userId) {
        return transactionRepository.findByAccount_User_Id(userId);
    }

    public Transaction getById(Long id, User currentUser) {
        Transaction tx = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (!tx.getAccount().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("This transaction does not belong to you");
        }
        return tx;
    }

    @Transactional
    public void delete(Long id, User currentUser) {
        Transaction tx = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (!tx.getAccount().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("This transaction does not belong to you");
        }

        Account account = tx.getAccount();
        if (tx.getType() == Category.TransactionType.INCOME) {
            account.setBalance(account.getBalance().subtract(tx.getAmount()));
        } else {
            account.setBalance(account.getBalance().add(tx.getAmount()));
        }
        accountRepository.save(account);

        transactionRepository.delete(tx);
    }
}