package ru.tbank.pet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tbank.pet.dto.CreateAccountRequest;
import ru.tbank.pet.dto.TransferRequest;
import ru.tbank.pet.model.Account;
import ru.tbank.pet.model.Operation;
import ru.tbank.pet.service.BankService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    // Создать счет
    @PostMapping("/accounts")
    public Account createAccount(@RequestBody CreateAccountRequest request) {
        return bankService.createAccount(request);
    }

    // Получить счет
    @GetMapping("/accounts/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return bankService.getAccount(accountNumber);
    }

    // Пополнить счет
    @PostMapping("/accounts/{accountNumber}/deposit")
    public Account deposit(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        return bankService.deposit(accountNumber, amount);
    }

    // Перевести деньги
    @PostMapping("/transfers")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        bankService.transfer(request);
        return ResponseEntity.ok("Перевод успешно выполнен");
    }

    // История операций счета
    @GetMapping("/accounts/{accountNumber}/operations")
    public List<Operation> getOperations(@PathVariable String accountNumber) {
        return bankService.getOperations(accountNumber);
    }
}
