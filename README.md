# 🚗 Instant Mechanic - Mini Mechanic Service App

An end-to-end, production-grade automotive roadside assistance and garage booking Android application built with **Jetpack Compose**, **Kotlin**, **MVVM Architecture**, and a dedicated **Spring Boot REST API** backend.

---

## 📋 Assignment Requirements Mapping

| Assignment Requirement | Status | Implementation Details |
| :--- | :---: | :--- |
| **1. Home Screen** |  **100% Complete** | Displays verified garages with: Garage Name, Star Rating, Distance (`1.2 km away`), Location (`Indiranagar`), Available Services chips, Open/Closed status tag, 24x7 Roadside SOS emergency dispatch card, and quick category filters. |
| **2. Mechanic Details Screen** |  **100% Complete** | Selecting any garage displays: Garage Name, Rating, Full Street Address, Services Offered, Working Hours, Direct Phone Contact (with 1-tap call intent), Directions shortcut, and prominent "Book / Request Service" action button. |
| **3. Request Service Screen** |  **100% Complete** | Complete booking form with: Customer Name (auto pre-filled from active session), Contact Phone, Vehicle Registration Number, Service selection dropdown, Problem description text field, Emergency vs Standard urgency toggle, and confirmation modal with live arrival ETA. |
| **4. API / Data Handling** |  **100% Complete** | Retrofit 2 + Gson REST client communicating with Spring Boot backend, full loading states (`CircularProgressIndicator`), comprehensive error handling with validation banners, and live API data rendering. |

---

## 🌟 Bonus & Advanced Features Implemented

- **🔐 Full Authentication Suite**:
  - Sign In (`LoginScreen.kt`) with email/phone & password show/hide eye toggle.
  - Sign Up (`SignUpScreen.kt`) with vehicle type selector (Car 🚗, Two-Wheeler 🏍️, Commercial 🚐) and password validation.
  - Forgot Password (`ForgotPasswordScreen.kt`) with 4-digit OTP simulation (`4 8 2 9`) and password reset.
  - **⚡ 1-Tap Quick Demo Credentials Button**: One-tap instant demo login (`rahul.sharma@example.com` / `Password@123`), perfect for internship evaluation.
  - Guest Mode bypass.
- **📷 Custom Profile Photo Upload**:
  - Real Android Gallery Photo Picker integration (`ActivityResultContracts.GetContent()`).
  - Users can tap their avatar or camera badge in the Sidebar Drawer or Profile Screen to select and set their own custom profile picture.
- **🗂️ Interactive Sidebar Navigation Drawer**:
  - Cyber-dark automotive gradient header with large user avatar, verified shield badge, and vehicle details.
  - **My Registered Vehicles Dialog**: View and switch between Car, Two-Wheeler, Commercial, and edit plate numbers in real-time.
  - **Roadside Insurance Policy Modal**: Details of coverage (`IM-RSA-BLR-2026-9901`), 50km free towing, cashless garage settlement.
  - **Settings & Preferences Dialog**: Toggles for Dispatch Alerts, Auto-Share Breakdown GPS, and Automotive Dark Mode.
  - **24x7 Customer Support & FAQs**: Direct 1-tap telephone dialer to Toll-Free Helpline `1800-102-1234` and breakdown FAQs.
- **🔍 Smart Search & Categorized Filtering**:
  - Live query search matching garage names, locations, and repair services.
  - Quick filter chips ("Towing 24/7", "Engine Diagnostics", "Brake Overhaul", "Tyre Replacement", "Battery Jumpstart").
- **🛡️ Resilient Offline & Cache Fallback**:
  - The Android app connects to the live backend REST API on `http://10.0.2.2:8080/`.
  - If the backend is stopped or running offline, the repository gracefully falls back to pre-cached data without crashing.
- **🧪 Unit & Integration Test Suites**:
  - **Android**: `MechanicRepositoryTest.kt` verifying garage fetching, query search, service filtering, and booking dispatch (`BUILD SUCCESSFUL`).
  - **Backend**: `MechanicApiIntegrationTests.java` verifying 6/6 tests passing (garages, auth demo login, signup, service requests).

---

## 🏗️ Architecture Explanation

The application adheres to **Clean Architecture** principles using the **MVVM (Model-View-ViewModel)** architectural pattern with Unidirectional Data Flow (UDF):

```
┌─────────────────────────────────────────────────────────────┐
│                       Presentation Layer                    │
│   Jetpack Compose Screens & UI Components (Material 3)     │
│  (HomeScreen, ExploreScreen, BookingsScreen, ProfileScreen, │
│   MechanicDetailsScreen, RequestServiceScreen, AuthScreens) │
└──────────────────────────────▲──────────────────────────────┘
                               │ Observes StateFlow & Sends Events
┌──────────────────────────────┴──────────────────────────────┐
│                        ViewModel Layer                      │
│        (AuthViewModel, MechanicViewModel,                   │
│               ServiceRequestViewModel)                      │
└──────────────────────────────▲──────────────────────────────┘
                               │ Coroutines & Suspend functions
┌──────────────────────────────┴──────────────────────────────┐
│                        Data Layer                           │
│        (MechanicRepository, RetrofitInstance,               │
│          Offline Local Storage & Cache Fallback)            │
└──────────────────────────────▲──────────────────────────────┘
                               │ HTTP / REST API (Retrofit 2 + Gson)
┌──────────────────────────────┴──────────────────────────────┐
│                   Backend REST API Service                  │
│     Spring Boot 4.x / Java 17 Service (mechanic-api)        │
│    Controllers: /api/mechanics, /api/auth, /api/service-req  │
└─────────────────────────────────────────────────────────────┘
```

- **StateFlow & Coroutines**: Reactive state management ensures UI components automatically recompose when network data arrives.
- **Repository Pattern**: Abstract data sources so UI layers remain decoupled from network implementations.

---

## 🔌 API & Data Specifications

### Base URL: `http://10.0.2.2:8080/` (Android Emulator to Host)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/mechanics` | Returns list of verified garages with services, rating, and status |
| `GET` | `/api/mechanics/{id}` | Returns single garage profile by ID |
| `GET` | `/api/mechanics/search?name={query}` | Searches garages across name, location, and services |
| `GET` | `/api/mechanics/filter?service={svc}` | Filters garages by specific repair capability |
| `GET` | `/api/service-requests` | Retrieves all submitted roadside service requests |
| `POST` | `/api/service-requests` | Submits a new roadside breakdown request |
| `POST` | `/api/auth/login` | Authenticates user (email/phone + password) |
| `POST` | `/api/auth/signup` | Registers new customer account |
| `POST` | `/api/auth/forgot-password` | Sends password reset verification code |
| `POST` | `/api/auth/reset-password` | Updates customer password |

---

## 🚀 Setup & Execution Instructions

### Prerequisites:
- Android Studio Ladybug / Iguana or newer
- JDK 17 or newer
- Android SDK 24+ (Android 7.0 to 14/15)

### 1. Run the Spring Boot Backend (`mechanic-api`):
```powershell
cd c:\Users\acer\Downloads\mechanic-api\mechanic-api
.\mvnw.cmd spring-boot:run
```
*Backend starts on `http://localhost:8080` (accessible from emulator at `http://10.0.2.2:8080`).*

### 2. Run the Android App (`InstantMechanic`):
1. Open `c:\Users\acer\AndroidStudioProjects\InstantMechanic` in Android Studio.
2. Let Gradle sync.
3. Select an Android Emulator (API 24 to 34) and click **Run (Shift + F10)**.
4. Alternatively, build the debug APK via terminal:
   ```powershell
   .\gradlew.bat assembleDebug
   ```
   Output APK: `app/build/outputs/apk/debug/app-debug.apk`.

### 3. Run Unit Tests:
```powershell
# Android Tests
.\gradlew.bat test

# Backend Tests
cd c:\Users\acer\Downloads\mechanic-api\mechanic-api
.\mvnw.cmd test
```

---

## 💡 Quick Demo Tips for Evaluators

1. **⚡ 1-Tap Login**: On the Sign In screen, tap the **"⚡ Quick Demo Login"** pill to instantly log in without typing credentials.
2. **📷 Custom Profile Picture**: Tap on the avatar in the Profile Screen or Sidebar Drawer to pick an image from the device.
3. **🚨 24/7 Roadside SOS**: Tap the red emergency SOS banner on the Home screen to trigger instant roadside dispatch.
4. **🚗 Vehicle Management**: Open the sidebar or Profile screen and tap "My Registered Vehicles" to switch between Car, Two-Wheeler, and Commercial plates.
5. **📴 Offline Demonstration**: Stop the backend server and notice how the app seamlessly falls back to cached data without any crashes!
