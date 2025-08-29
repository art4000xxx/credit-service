package ru.netology.creditapplicationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.netology.creditapplicationservice.config.RabbitConfig;
import ru.netology.creditapplicationservice.event.CreditDecisionEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreditDecisionListener {

    private final CreditApplicationService service;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receiveDecision(CreditDecisionEvent event) {
        log.info("Получено из RabbitMQ: {}", event);
        service.updateStatus(event);
    }
}
