package ru.tbank.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tbank.pet.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
