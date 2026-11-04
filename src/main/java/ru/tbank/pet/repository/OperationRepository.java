package ru.tbank.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tbank.pet.model.Operation;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findAllByFromAccountOrToAccount(String fromAccount, String toAccount);
}
