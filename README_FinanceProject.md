# 💰 Personal Budget Accounting System

A multi-user web application for managing personal finances — tracking income, expenses, transfers and accounts in multiple currencies.

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17 + Spring Boot |
| ORM | Spring Data JPA + QueryDSL |
| Database | PostgreSQL / MySQL |
| Security | Spring Security + JWT |
| Mapping | MapStruct |
| Build | Maven |
| Testing | JUnit 5 + Mockito |
| Containerization | Docker |

---

## ✨ Features

- 👤 **Multi-user** — each user has isolated personal financial data
- 💳 **Financial accounts** — Cash, Deposit, Credit Card account types
- 💸 **Transaction management** — income, expenses, transfers between accounts
- 🏷️ **Categories & tags** — categorize and tag transactions (food, transport, work, etc.)
- 🌍 **Multi-currency** — manage accounts in different currencies with real-time or manual exchange rates
- 📊 **Reports & analytics** — income/expense summaries, spending by category, balance over time
- ⚙️ **Automatic rules** — define rules to auto-categorize transactions on creation/update
- 🔌 **REST API** — full RESTful API for external system integration
- 📥 **Data import** — import transactions from CSV / Excel files
- 🔒 **JWT Authentication** — stateless token-based security

---

## 📁 Project Structure

```
src/main/java/com/example/financeproject/
├── controller/
│   ├── AccountController.java       # Account CRUD + transfers
│   ├── CategoryController.java      # Category management
│   ├── TransactionController.java   # Transaction CRUD + filtering
│   └── UserController.java          # User registration + profile
│
├── services/
│   ├── account/                     # Account business logic
│   ├── category/                    # Category business logic
│   ├── transaction/                 # Transaction logic + predicates (QueryDSL)
│   └── user/                        # User logic
│
├── repositories/
│   ├── account/                     # AccountRepository
│   ├── category/                    # CategoryRepository
│   ├── transaction/                 # TransactionRepository + AccountTransactionRepository
│   └── user/                        # UserRepository
│
├── models/
│   ├── User.java                    # User entity
│   ├── Account.java                 # Financial account entity
│   ├── AccountType.java             # Enum: CASH, DEPOSIT, CREDIT_CARD
│   ├── Transaction.java             # Transaction entity
│   ├── AccountTransaction.java      # Transfer between accounts
│   ├── Category.java                # Transaction category
│   ├── CategoryType.java            # Enum: INCOME, EXPENSE
│   ├── Currency.java                # Currency entity
│   └── DefaultCategory.java        # System default categories
│
├── dto/
│   ├── dtoAccount/                  # AccountDto, GetAccountDto, TransferRequestDto, etc.
│   ├── dtoCategory/                 # CategoryDto, GetCategoryDto, UpdateCategoryDto
│   ├── dtoTransaction/              # TransactionDto, TransactionFilterDto, etc.
│   └── dtoUser/                     # UserDto, UserReturnDto, VerifyDto
│
├── mappers/                         # MapStruct mappers (entity ↔ DTO)
│   ├── AccountMapper.java
│   ├── CategoryMapper.java
│   ├── TransactionMapper.java
│   └── UserMapper.java
│
├── security/
│   ├── JWTService.java              # JWT token generation and validation
│   ├── JwtFilter.java               # JWT request filter
│   ├── SecurityConfig.java          # Spring Security configuration
│   ├── MyUserDetailsService.java    # UserDetailsService implementation
│   └── UserPrincipal.java           # Authenticated user wrapper
│
├── exception/
│   ├── GlobalExceptionHandler.java  # Centralized error handling (@ControllerAdvice)
│   └── ResourceNotFoundException.java
│
├── validation/
│   ├── CategoryValidators/          # @ValidCategorySelection — XOR category validation
│   └── UniqueValidators/            # @UniqueEmail, @UniqueUsername constraints
│
├── init/
│   └── DefaultCategoryInitializer.java  # Seeds default categories on startup
│
└── config/
    └── QueryDslConfig.java          # QueryDSL JPAQueryFactory bean
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL or MySQL

### 1. Clone the repository

```bash
git clone https://github.com/yourname/financeproject.git
cd financeproject
```

### 2. Configure the database

Open `src/main/resources/application.properties` and update:

```properties
***=jdbc:postgresql://localhost:5432/finance_project
***=your_username
***=your_password

spring.jpa.hibernate.ddl-auto=update

***=your_jwt_secret_key
jwt.expiration=86400000
```

### 3. Run with Docker

```bash
docker-compose up --build
```

### 4. Run without Docker

```bash
mvn clean install
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

---

## 🔐 Authentication

The API uses **JWT (JSON Web Token)** authentication.

**Register:**
```http
POST /api/users/register
Content-Type: application/json

{
  "username": "john",
  "email": "john@example.com",
  "password": "secret123"
}
```

**Login:**
```http
POST /api/users/login
Content-Type: application/json

{
  "username": "john",
  "password": "secret123"
}
```

Returns a JWT token. Include it in all subsequent requests:
```http
Authorization: Bearer <your_token>
```

---

## 📡 API Endpoints

### Users
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users/register` | Register new user |
| POST | `/api/users/login` | Login and get JWT token |
| GET | `/api/users/me` | Get current user profile |
| PUT | `/api/users/me` | Update profile |

### Accounts
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/accounts` | List all user accounts |
| POST | `/api/accounts` | Create new account |
| PUT | `/api/accounts/{id}` | Update account |
| DELETE | `/api/accounts/{id}` | Delete account |
| POST | `/api/accounts/transfer` | Transfer between accounts |

### Transactions
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/transactions` | List transactions (with filters) |
| POST | `/api/transactions` | Create transaction |
| PUT | `/api/transactions/{id}` | Update transaction |
| DELETE | `/api/transactions/{id}` | Delete transaction |

### Categories
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/categories` | List all categories |
| POST | `/api/categories` | Create category |
| PUT | `/api/categories/{id}` | Update category |
| DELETE | `/api/categories/{id}` | Delete category |

---

## 🗄️ Database Schema

| Table | Description |
|---|---|
| `users` | User accounts (username, email, hashed password) |
| `accounts` | Financial accounts (cash, deposit, credit card) |
| `transactions` | Income and expense transactions |
| `account_transactions` | Transfers between two accounts |
| `categories` | Transaction categories (with type: INCOME/EXPENSE) |
| `default_categories` | System-wide default categories seeded on startup |
| `currencies` | Supported currencies with exchange rates |

---

## 🧪 Testing

Run all tests:
```bash
mvn test
```

Test coverage includes:
- **Unit tests** — `AccountServiceImplTest`, `CategoryServiceImplTest`, `TransactionServiceImplTest`
- **Integration tests** — `AccountControllerTest`, `CategoryControllerTest`
- **Repository tests** — `AccountRepositoryTest`

---

## 🏗️ Architecture

The project follows **Domain-Driven Design (DDD)** and a layered architecture:

```
Controller  →  Service (interface + impl)  →  Repository  →  Database
               ↑                                ↑
              DTO  ←→  MapStruct Mapper  ←→  Entity
```

Key patterns used:
- **DTO pattern** — separates API contracts from internal domain models
- **MapStruct** — compile-time mapper generation (no reflection overhead)
- **QueryDSL** — type-safe dynamic query building for transaction filtering
- **Service interface + impl** — decouples business logic from implementation details
- **Global exception handler** — centralized error responses via `@ControllerAdvice`
- **Custom validators** — `@UniqueEmail`, `@UniqueUsername`, `@ValidCategorySelection`

---

## 🐳 Docker

```bash
# Build and start all services
docker-compose up --build

# Run in background
docker-compose up -d

# Stop
docker-compose down
```

---

## 📝 Development Notes

- All code changes should be submitted via **Pull Requests**
- Each feature should be developed in a separate branch
- PRs require review before merging to `main`
- CI/CD pipeline runs tests automatically on each PR


