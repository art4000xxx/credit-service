package ru.netology.creditprocessingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.netology.creditprocessingservice.config.RabbitConfig;
import ru.netology.creditprocessingservice.event.CreditApplicationEvent;
import ru.netology.creditprocessingservice.event.CreditDecisionEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditProcessingService {

    private final RabbitTemplate rabbitTemplate;

    @KafkaListener(topics = "credit-applications", groupId = "processing-group")
    public void processApplication(CreditApplicationEvent event) {
        log.info("Получено из Kafka: {}", event);

        // Рассчитываем месячный платеж
        BigDecimal monthlyPayment = event.getLoanAmount()
                .divide(BigDecimal.valueOf(event.getLoanTerm()), 2, RoundingMode.HALF_UP);
        BigDecimal totalDebt = event.getCurrentDebt().add(monthlyPayment);
        BigDecimal halfIncome = event.getIncome().multiply(BigDecimal.valueOf(0.5));

        String decision = totalDebt.compareTo(halfIncome) <= 0 ? "одобрено" : "отказано";

        CreditDecisionEvent decisionEvent = new CreditDecisionEvent();
        decisionEvent.setApplicationId(event.getApplicationId());
        decisionEvent.setDecision(decision);

        log.info("Отправка в RabbitMQ: {}", decisionEvent);
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE, decisionEvent); // default exchange
    }
}
