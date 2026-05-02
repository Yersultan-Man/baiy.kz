package kz.kopbolsyn.baiy.repository;

import kz.kopbolsyn.baiy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
    List<Transaction> findByAccount_User_Id(Long userId);

    @Query("SELECT t.category.name, SUM(t.amount) FROM Transaction t " +
            "WHERE t.account.user.id = :userId AND t.type = 'EXPENSE' " +
            "GROUP BY t.category.name")
    List<Object[]> sumByCategory(@Param("userId") Long userId);
}