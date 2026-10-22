# Jetpack Compose Login Demo

A small Android app demonstrating a login form built with Kotlin, Jetpack Compose, MVVM, and a simple clean architecture layer structure. The form accepts a mobile number, email address, and password. Each tap on **Login** increments a counter shown above the form and validates all three fields.

> This is a UI and architecture demo. It does not authenticate a user or send credentials to a server.

## What happens when the app starts

1. Android launches `MainActivity`, the app's launcher activity (declared in `app/src/main/AndroidManifest.xml`).
2. `MainActivity` enables edge-to-edge display and sets Compose content.
3. `MyApplicationTheme` applies the app's Material theme.
4. The activity creates a `LoginViewModel` with a `ViewModelProvider.Factory`. The factory wires up the domain use cases and the in-memory counter repository.
5. `LoginScreen` observes the ViewModel's `StateFlow<LoginUiState>` and draws the current form and counter.

## How the login form works

1. The user types into the mobile number, email, and password fields in `LoginScreen`.
2. Each field calls its corresponding ViewModel function (`onMobileChanged`, `onEmailChanged`, or `onPasswordChanged`).
3. The ViewModel updates the immutable `LoginUiState` held in a `MutableStateFlow`. Compose observes that state and redraws the affected UI.
4. On **Login**, the ViewModel passes the current `LoginForm` to `ValidateLoginUseCase` and asks `RecordLoginAttemptUseCase` to increment the counter.
5. The validation result and new count are saved in the UI state. Compose displays any field errors and the updated count.
6. If all fields pass validation, the UI also displays “All fields are valid.” Editing a field clears that success message. The errors remain visible and are recalculated on the next login tap.

### Validation rules

- **Mobile number:** required; accepts 10–15 digits, optionally preceded by `+`.
- **Email:** required; must match the app's email format check.
- **Password:** required; must contain at least 8 characters.

All fields are checked on every login tap, so the user can see multiple errors at once. The counter increments on every tap, including when one or more fields are invalid.

## Counter behavior

`LoginAttemptRepository` stores an integer in memory and increments it each time it is called. The ViewModel copies the returned value into `LoginUiState`, and `LoginScreen` displays it at the top.

The counter survives Compose recompositions and Activity recreation while the same ViewModel remains alive. It is not saved to disk, so it resets when the app process is restarted.

## Project structure

```text
app/src/main/java/com/example/myapplication/
├── MainActivity.kt
├── data/login/
│   └── LoginAttemptRepository.kt
├── domain/login/
│   ├── LoginAttemptCounter.kt
│   ├── LoginForm.kt
│   ├── RecordLoginAttemptUseCase.kt
│   └── ValidateLoginUseCase.kt
├── presentation/login/
│   ├── LoginScreen.kt
│   └── LoginViewModel.kt
└── ui/theme/
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

### Layer responsibilities

- **Presentation:** `LoginScreen` renders state and forwards user actions. `LoginViewModel` coordinates actions and exposes a read-only `StateFlow` for the UI.
- **Domain:** `LoginForm` and `LoginValidation` represent form data and validation results. `ValidateLoginUseCase` applies the rules. `LoginAttemptCounter` defines the counter operation, and `RecordLoginAttemptUseCase` invokes it.
- **Data:** `LoginAttemptRepository` implements the domain counter interface and holds the in-memory count.
- **App wiring:** `MainActivity` creates the concrete repository and use cases and supplies them to the ViewModel factory. This keeps the ViewModel dependent on domain behavior rather than the data implementation.
- **Theme:** `ui/theme` contains the Material color scheme, typography, and theme wrapper used by the Compose screen.

## Main files

- `app/src/main/AndroidManifest.xml` declares the application and launcher activity.
- `app/build.gradle.kts` configures the Android app module, SDK levels, Compose, and dependencies.
- `gradle/libs.versions.toml` centralizes plugin and library versions.
- `app/src/main/res/values/strings.xml` contains the application label.

## Build and run

Open the project in Android Studio and run the `app` configuration on an emulator or Android device. From a shell, use the Gradle wrapper:

```bash
./gradlew :app:assembleDebug
```

The project currently targets SDK 37 and supports Android 7.0 (API 24) and newer. Android Studio/Gradle may need to download the configured SDK and dependencies on first build.
