package ru.netology.creditapplicationservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.creditapplicationservice.dto.CreditApplicationRequestDto;
import ru.netology.creditapplicationservice.service.CreditApplicationService;

@RestController
@RequestMapping("/api/credit")
@RequiredArgsConstructor
@Slf4j
public class CreditApplicationController {

    private final CreditApplicationService service;

    @PostMapping("/apply")
    public ResponseEntity<Long> apply(@RequestBody CreditApplicationRequestDto dto) {
        log.info("Получен POST-запрос: {}", dto);
        Long id = service.submitApplication(dto);
        log.info("ID заявки: {}", id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<String> getStatus(@PathVariable Long id) {
        log.info("Получен GET-запрос для ID: {}", id);
        return ResponseEntity.ok(service.getStatus(id));
    }
}