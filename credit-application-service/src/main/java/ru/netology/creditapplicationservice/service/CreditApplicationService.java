package ru.netology.creditapplicationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.netology.creditapplicationservice.dto.CreditApplicationRequestDto;
import ru.netology.creditapplicationservice.entity.CreditApplication;
import ru.netology.creditapplicationservice.event.CreditApplicationEvent;
import ru.netology.creditapplicationservice.event.CreditDecisionEvent;
import ru.netology.creditapplicationservice.repository.CreditApplicationRepository;

@Service
@RequiredArgsConstructor
public class CreditApplicationService {

    private final CreditApplicationRepository repository;
    private final KafkaTemplate<String, CreditApplicationEvent> kafkaTemplate;

    public Long submitApplication(CreditApplicationRequestDto dto) {
        CreditApplication application = new CreditApplication();
        application.setLoanAmount(dto.getLoanAmount());
        application.setLoanTerm(dto.getLoanTerm());
        application.setIncome(dto.getIncome());
        application.setCurrentDebt(dto.getCurrentDebt());
        application.setCreditScore(dto.getCreditScore());
        // Статус по умолчанию "в обработке"

        CreditApplication saved = repository.save(application);

        // Создаём event и отправляем в Kafka
        CreditApplicationEvent event = new CreditApplicationEvent();
        event.setApplicationId(saved.getId());
        event.setLoanAmount(dto.getLoanAmount());
        event.setLoanTerm(dto.getLoanTerm());
        event.setIncome(dto.getIncome());
        event.setCurrentDebt(dto.getCurrentDebt());
        event.setCreditScore(dto.getCreditScore());

        kafkaTemplate.send("credit-applications", event);

        return saved.getId();
    }

    public String getStatus(Long id) {
        return repository.findById(id)
                .map(CreditApplication::getStatus)
                .orElse("Не найдено");
    }


    public void updateStatus(CreditDecisionEvent event) {
        repository.findById(event.getApplicationId()).ifPresent(app -> {
            app.setStatus(event.getDecision());
            repository.save(app);
        });
    }
}
