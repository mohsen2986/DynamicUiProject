# ReviewTask

## Project Objective

This project is a Kotlin Multiplatform application designed to dynamically render a single mobile input component based on a REST API response. The core goal was to demonstrate proficiency in Kotlin Multiplatform architecture, REST API integration, serialization, dynamic UI rendering, state management, and persistence across configuration changes.

## How to Run the Project

### Prerequisites

*   Android Studio Jellyfish | 2023.3.1 or newer
*   JDK 17 or newer
*   A physical Android device or emulator running API level 21 or higher

### Running on Android

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/yourusername/ReviewTask.git
    cd ReviewTask
    ```
2.  **Open in Android Studio:**
    Open the `ReviewTask` project in Android Studio.
3.  **Sync Gradle:**
    Allow Android Studio to sync the Gradle project. If it doesn't happen automatically, click the "Sync Project with Gradle Files" button in the toolbar.
4.  **Run the `androidApp`:**
    Select the `androidApp` run configuration and choose your desired emulator or connected device. Click the "Run" button (green triangle icon) to deploy and run the application.

## Running on iOS

> ⚠️ Requires macOS with Xcode installed

### 1. Open the iOS project

Navigate to the `iosApp` folder and open the Xcode project:

```bash
cd iosApp
open iosApp.xcodeproj
```

---

## Mock Server

This project uses a simple **Ktor-based mock server** to simulate backend responses during development.  
It allows the app to dynamically render UI components without relying on a production backend.

---

## Purpose

- Provide predictable API responses for UI rendering
- Enable offline/local development
- Simulate dynamic component-based API structure
- Support fast iteration during UI/architecture development

---

## How to Run the Mock Server

### 1. Navigate to server module

```bash
./gradlew :server:run
```




## Architecture Decisions

The project follows a modular and layered architecture, typical for Kotlin Multiplatform projects, to ensure a clear separation of concerns and maintainability.

*   **Kotlin Multiplatform (KMP):** The core logic, including data models, API interfaces, and business logic, is shared across common modules.
*   **Compose Multiplatform:** The UI is built using Jetpack Compose, enabling a declarative and reactive approach to UI development for Android.
*   **Ktor Client:** Used for making network requests to the REST API, providing a robust and flexible HTTP client.
*   **Kotlinx Serialization:** Employed for type-safe serialization and deserialization of JSON responses into Kotlin data models (`ComponentResponseDto`).
*   **Repository Pattern:** A `RemoteDataSource` interface abstracts the data fetching logic, allowing for easy switching between a real API and a fake/mock implementation. This enhances testability and development flexibility.
*   **ViewModel with SavedStateHandle:** State management is handled by `ViewModel`s, utilizing `SavedStateHandle` for persistence across configuration changes (like screen rotations), ensuring the UI state is preserved without needing long-term storage.

Layers:

```
Presentation
    │
    ▼
  Domain
    │
    ▼
   Data
```


## Presentation

- Compose Multiplatform UI
- ViewModel
- StateFlow-based state management
- UI State pattern

## Domain

- Business logic
- Use Cases
- Repository interfaces
- Platform-independent code

## Data

- Ktor Client
- DTOs
- Repository implementations
- Mapping between DTOs and domain models

## Dependency Injection

Dependencies are injected through a shared dependency graph to keep layers decoupled and easily testable.


## Assumptions Made

*   The backend will always return a single UI component definition in the `/screen/home` endpoint response.
*   The `type` field in the component JSON response will always correspond to one of the supported component types (`text`, `number`, `slider`, `empty_box`).
*   The `id`, `label`, and `placeholder` (for text/number) or `min`, `max`, `value` (for slider) fields will always be present and correctly typed for their respective component types.
*   The app's UI is designed for mobile form factors (Android primarily, with potential for iOS).
*   For this task, a fake remote data source (`FakeKtorRemoteDataSource`) is used to simulate API responses with hardcoded JSON data.

## What is Implemented

*   **Dynamic UI Rendering:** The app fetches a JSON response defining a UI component and renders it dynamically on the screen.
*   **Supported Component Types:**
    *   **Text Field:** Renders a text input field with a label and placeholder.
    *   **Number Input:** Renders a number input field with a label and placeholder.
    *   **Slider:** Renders a slider with a label, min, max, and current value.
    *   **Empty Box:** Renders a box with a color specified in the response.
*   **State Management & Persistence:** The state of the rendered component (e.g., text in a text field, value of a slider) is managed using `ViewModel` and persisted across configuration changes (like screen rotations) via `SavedStateHandle`.
*   **Loading, Success, and Error States:** The UI gracefully handles different states of the data fetching process, displaying loading indicators, the rendered component on success, and an error message on failure.
*   **Serialization:** Uses `kotlinx.serialization` to parse JSON responses into type-safe Kotlin data models (`ComponentResponseDto`).
*   **KMP Shared Module:** Core data models, API interfaces, and business logic reside in the `shared` module, accessible by platform-specific modules.
*   **Fake Remote Data Source:** A `FakeKtorRemoteDataSource` is implemented to provide mock API responses, facilitating development without a live backend.
*   **Real Mock Remote Web Server:** A Ktor-based mock backend that exposes HTTP endpoints returning `ComponentResponseDto` JSON, simulating a real API for offline development and fast iteration.
*   **iOS Target Support:** The project is built with Kotlin Multiplatform and includes a shared architecture prepared for iOS integration. The shared module is fully compatible with iOS targets, enabling future expansion without changes to core logic.
*   **Retry Button on Error:** The error state includes a retry action that allows users to re-fetch data and recover from failed requests.
*   **Pull-to-Refresh:** The UI supports pull-to-refresh, allowing users to manually refresh the rendered component and fetch the latest data from the server.
*   **Theming (Material Design 3 advanced features):** The app implements Material Design 3 theming with support for dynamic and structured UI styling, providing a consistent and modern visual experience.
*   **Persistence Beyond Configuration Changes:** Component state is fully preserved across configuration changes such as screen rotations
*   **Unit Tests:** Unit tests for the data layer, domain logic,mappers for some of the logic and data implemented.


## What is Not Implemented
*   **Input Validation:** No explicit validation rules are applied to user inputs (e.g., ensuring number input is indeed a number within a range).

## Screenshots / Screen Recording

## Android ScreenShots
| ![Screenshot](media/android_1.jpg) | ![Screenshot](media/android_2.jpg) | ![Screenshot](media/android_3.jpg) |
|-----------------------------------------------|-----------------------------------------------|-----------------------------------------------|

## IOS ScreenShots
| ![Screenshot](media/ios_1.jpg) | ![Screenshot](media/ios_2.jpg) | ![Screenshot](media/ios_3.jpg) |
|--------------------------------|--------------------------------|--------------------------------|

## Demo video
[Watch Demo](media/video_demo.mp4)

---
