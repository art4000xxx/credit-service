package ru.netology.creditapplicationservice.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditApplicationEvent {
    private Long applicationId;
    private BigDecimal loanAmount;
    private Integer loanTerm;
    private BigDecimal income;
    private BigDecimal currentDebt;
    private Integer creditScore;
}