package org.example.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment extends PanacheEntity {
    public Long payeeId;
    public Long accountId;
    public BigDecimal amount;
    public LocalDateTime paymentDate;
}

