# 🏦 Bank Account API (Pet-проект)

Простой и понятный REST API сервис для управления банковскими счетами и переводами на **Java + Spring Boot + PostgreSQL**.

---

## 🎯 Что делает проект (Бизнес-логика)

1. **Создание счета:** сохраняет номер счета, имя владельца и начальный баланс в PostgreSQL.
2. **Просмотр счета:** возвращает текущий баланс по номеру счета.
3. **Пополнение счета:** увеличивает баланс счета и записывает операцию в историю.
4. **Перевод денег:** проверяет, хватает ли средств на счете отправителя, списывает нужную сумму, зачисляет получателю и сохраняет историю в таблицу операций. Все выполняется в одной транзакции (`@Transactional`).
5. **История операций:** возвращает все переводы и пополнения по номеру счета.

---

## 🛠 Технологии проекта

* **Java 17** (ООП, коллекции, базовая логика)
* **Spring Boot 3**
  * `Spring Web`: аннотации `@RestController`, `@GetMapping`, `@PostMapping`, `@RequestBody`, `@PathVariable`
  * `Spring Data JPA`: связь с базой данных через интерфейс `JpaRepository` без ручного написания сложных SQL
* **PostgreSQL**: реляционная база данных для хранения таблиц `accounts` и `operations`
* **Maven**: сборщик проекта и управление зависимостями
* **Docker Compose**: быстрый запуск базы данных одной командой

---

## 📂 Структура проекта (Всего 6 основных файлов!)

```text
src/main/java/ru/tbank/pet/
├── BankAccountServiceApplication.java   # Главный класс запуска приложения
├── model/
│   ├── Account.java                    # Сущность банковского счета (@Entity)
│   └── Operation.java                  # Сущность истории операции (@Entity)
├── repository/
│   ├── AccountRepository.java          # Интерфейс для поиска счетов в базе
│   └── OperationRepository.java        # Интерфейс для поиска операций в базе
├── service/
│   └── BankService.java                # Логика: проверки баланса, пополнение, перевод
├── controller/
│   └── BankController.java             # REST эндпоинты для вызова через Postman/браузер
└── dto/
    ├── CreateAccountRequest.java       # Данные для создания счета
    └── TransferRequest.java            # Данные для перевода денег
```

---

## 🚀 Как запустить проект

### 1. Запустить базу данных PostgreSQL в Docker

В папке проекта выполните:
```bash
docker compose up -d
```
*(База запустится на порту 5432).*

### 2. Запустить Spring Boot

Откройте проект в **IntelliJ IDEA** и запустите зеленый треугольник у класса `BankAccountServiceApplication`, либо через терминал:
```bash
mvn spring-boot:run
```

Таблицы в PostgreSQL создадутся автоматически (`ddl-auto: update`).

---

## 📡 Примеры запросов для Postman / cURL

### 1. Создать счет
* **URL:** `POST http://localhost:8080/api/accounts`
* **JSON в Body:**
```json
{
  "accountNumber": "ACC-101",
  "ownerName": "Тимофей Сеничев",
  "initialBalance": 5000.00
}
```

### 2. Получить счет
* **URL:** `GET http://localhost:8080/api/accounts/ACC-101`

### 3. Пополнить счет
* **URL:** `POST http://localhost:8080/api/accounts/ACC-101/deposit?amount=1000`

### 4. Перевести деньги
* **URL:** `POST http://localhost:8080/api/transfers`
* **JSON в Body:**
```json
{
  "fromAccount": "ACC-101",
  "toAccount": "ACC-102",
  "amount": 1500.00
}
```

### 5. История операций
* **URL:** `GET http://localhost:8080/api/accounts/ACC-101/operations`
