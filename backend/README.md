# 🛠️ Instant Mechanic - Backend REST API Service (`mechanic-api`)

Spring Boot REST API backend supporting the **Instant Mechanic** Android application.

---

## 📌 Features & Architecture

- **Spring Boot 4.x / Java 17** with Spring MVC.
- **Garages & Mechanics API**: Preloaded verified garages with search and substring service filtering.
- **Service Requests API**: Live booking management with status tracking (`Mechanic En Route`, `Completed`).
- **Authentication Suite**: User registration, login validation (email or phone), and password reset.
- **CORS Configured**: Fully enabled for Android Emulator (`10.0.2.2`), web clients, and local network devices (`0.0.0.0:8080`).
- **Integration Test Suite**: 6/6 tests passing covering all controllers, services, and repositories.

---

## 🚀 How to Run

```powershell
.\mvnw.cmd spring-boot:run
```
*Listens on `http://0.0.0.0:8080` (accessible from Android Emulator at `http://10.0.2.2:8080`).*

### Run Tests:
```powershell
.\mvnw.cmd test
```
*Result: 6 tests executed, 0 failures, 0 errors.*
