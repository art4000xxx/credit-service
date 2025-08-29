# Credit Service

Двухмикросервисное приложение для обработки кредитных заявок с использованием Kafka и RabbitMQ.

---

## Структура проекта

- **credit-application-service** – сервис для создания заявок на кредит и получения их статуса.
- **credit-processing-service** – сервис для обработки заявок и принятия решения (одобрено/отказано).

---

## Стек технологий

- Java 17, Spring Boot 3  
- Kafka (Wurstmeister)  
- RabbitMQ (3-management)  
- H2 Database (для хранения заявок)  
- Docker & Docker Compose  

---

## Как запустить

1. Перейти в корень проекта, где лежит `docker-compose.yml`:
  2 Сборка и запуск всех контейнеров:
docker-compose up --build
API
Создание заявки на кредит
POST /credit-applications
Content-Type: application/json

{
  "loanAmount": 100000,
  "loanTerm": 12,
  "income": 50000,
  "currentDebt": 10000,
  "creditScore": 700
}
Ответ:
{
  "id": 1
}
Получение статуса заявки
GET /credit-applications/{id}/status
Ответ:
{
  "status": "одобрено"
}
Логика обработки

Сервис обработки принимает заявку из Kafka.

Проверка: платеж по кредиту не превышает 50% дохода → одобрено, иначе отказано.

Решение отправляется обратно в сервис заявок через RabbitMQ.

Сервис заявок обновляет статус в базе.

```bash
cd credit-service
