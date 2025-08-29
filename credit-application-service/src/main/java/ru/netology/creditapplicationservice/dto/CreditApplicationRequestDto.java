package ru.netology.creditapplicationservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditApplicationRequestDto {
    private BigDecimal loanAmount;  // Сумма кредита
    private Integer loanTerm;       // Срок кредита (в месяцах?)
    private BigDecimal income;      // Доход пользователя
    private BigDecimal currentDebt; // Текущая кредитная нагрузка
    private Integer creditScore;    // Кредитный рейтинг
}