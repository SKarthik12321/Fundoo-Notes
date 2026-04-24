#  Fundoo-Notes Backend

A production-style **Spring Boot 3** REST API backend built using a strict **Use-Case (UC) driven Git workflow**.

> Each feature lives in its own isolated feature branch and is merged into `develop` only after completion. The `main` branch is reserved purely for documentation.

---

##  Architecture

Every API request flows through a clean layered pipeline:
Client ──► Controller ──► DTO ──► Service ──► Repository ──► MySQL
↕
Security / JWT Filter
↕
Global Exception Handler

---

##  Package Structure
com.fundoonotes
├── config          → Security & AOP configuration
├── controller      → REST endpoints
├── dto             → Request and Response objects
├── entity          → JPA mapped database models
├── exception       → Custom exceptions + global handler
├── repository      → Spring Data JPA interfaces
├── security        → JWT filter pipeline
├── service/impl    → Business logic layer
└── util            → Token utility

---

## ✅ Phase 1 — Completed Use Cases (UC1–UC10)

| UC | Title | What Was Built |
|----|-------|---------------|
| UC1 | Project Setup | Spring Boot 3 scaffold with strict package separation |
| UC2 | Database Config | MySQL + Hibernate DDL via application.yml |
| UC3 | User Persistence | JPA User entity + UserRepository with email lookup |
| UC4 | User Registration | Validated DTOs + GlobalExceptionHandler with structured JSON errors |
| UC5 | Login & JWT | BCryptPasswordEncoder + JWT token generation on successful login |
| UC6 | JWT Validation | JwtAuthenticationFilter bouncer intercepting every secured request |
| UC7 | Note Entity | Note JPA model with decoupled user_id FK and state flags |
| UC8 | Create Note | Authenticated POST /api/notes — user identity extracted from JWT Principal |
| UC9 | Get Notes | IDOR-safe GET /api/notes returning only the authenticated user's notes |
| UC10 | Toggle APIs | Stateless PUT endpoints for pin, archive, and trash flag flipping |

---

##  Security Design

- Passwords are never stored in plain text — BCryptPasswordEncoder handles hashing
- JWT tokens are issued on login and validated on every secured request via the filter chain
- Spring Security context is populated from the token — no session, fully stateless
- IDOR attacks are prevented by always scoping data queries to the authenticated user's ID

---

##  Branch Strategy
main        → documentation only
develop     → integration branch (all UCs merged here)
feature/UC* → one branch per use case, merged after completion

---

##  Phase 2 — Upcoming

- Label / tagging system for note organisation
- Reminder scheduling with notification support

---

##  Tech Stack

Java 17 · Spring Boot 3 · Spring Security · JWT (jjwt 0.11.5) · Spring Data JPA · MySQL · Lombok · Maven

