package ru.netology.creditprocessingservice.event;

import lombok.Data;

@Data
public class CreditDecisionEvent {
    private Long applicationId;
    private String decision;  // "одобрено" или "отказано"
}