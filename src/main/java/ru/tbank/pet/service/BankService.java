package ru.tbank.pet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tbank.pet.dto.CreateAccountRequest;
import ru.tbank.pet.dto.TransferRequest;
import ru.tbank.pet.model.Account;
import ru.tbank.pet.model.Operation;
import ru.tbank.pet.repository.AccountRepository;
import ru.tbank.pet.repository.OperationRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    public BankService(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    // 1. Создать новый счет
    public Account createAccount(CreateAccountRequest request) {
        BigDecimal balance = request.getInitialBalance() != null ? request.getInitialBalance() : BigDecimal.ZERO;
        Account account = new Account(request.getAccountNumber(), request.getOwnerName(), balance);
        return accountRepository.save(account);
    }

    // 2. Получить информацию о счете
    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Счет не найден: " + accountNumber));
    }

    // 3. Пополнить счет
    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Сумма пополнения должна быть больше 0");
        }

        Account account = getAccount(accountNumber);
        account.setBalance(account.getBalance().add(amount));

        operationRepository.save(new Operation(null, accountNumber, amount, "DEPOSIT"));
        return accountRepository.save(account);
    }

    // 4. Перевести деньги между счетами
    @Transactional
    public void transfer(TransferRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Сумма перевода должна быть больше 0");
        }

        Account from = getAccount(request.getFromAccount());
        Account to = getAccount(request.getToAccount());

        // Проверяем, хватает ли денег
        if (from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Недостаточно средств на счете: " + from.getAccountNumber());
        }

        // Списываем и начисляем
        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        accountRepository.save(from);
        accountRepository.save(to);

        // Сохраняем запись об операции
        operationRepository.save(new Operation(from.getAccountNumber(), to.getAccountNumber(), request.getAmount(), "TRANSFER"));
    }

    // 5. История операций по счету
    public List<Operation> getOperations(String accountNumber) {
        return operationRepository.findAllByFromAccountOrToAccount(accountNumber, accountNumber);
    }
}
