package ru.tbank.pet.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String type; // DEPOSIT, TRANSFER
    private LocalDateTime dateTime;

    public Operation() {
    }

    public Operation(String fromAccount, String toAccount, BigDecimal amount, String type) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
        this.dateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
