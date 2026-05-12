# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Test Commands

```bash
# Build
./gradlew assembleDebug
./gradlew build

# Unit tests (JUnit 5 via JUnit Platform)
./gradlew test
./gradlew testDebugUnitTest

# Run a single test class
./gradlew test --tests "com.example.cryptocoins.CoinsViewModelTest"

# Instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Clean
./gradlew clean
```

## Architecture

MVVM + Clean Architecture with three distinct model layers and a local-first repository strategy.

### Model Layers

Every entity (Coin, Exchange) exists in three forms with explicit mapper interfaces between them:

| Layer | Location | Type suffix | Mapped by |
|-------|----------|-------------|-----------|
| Network | `data/network/response/` | `*Response` | `ResponseMapper` |
| Database | `data/database/entity/` | `*Entity` | `EntityMapper` |
| Domain | `domain/` | *(no suffix)* | `DomainMapper` |

Mapping extension functions (e.g. `toResponseModels()`, `toEntityModels()`, `toDomainModels()`) are defined at the package level near each layer boundary.

### Repository Pattern (local-first)

Repositories (`data/respositories/coin/CoinRepository`, `data/respositories/exchange/ExchangeRepository`) check Room first; if empty, they fetch from the network, persist the result, and return it. All repository calls run on `Dispatchers.IO` via `withContext`.

Each repository is backed by a `*RemoteDataSource` (Retrofit call) and a `*LocalDataSource` (Room DAO call) in the same package.

### ViewModel Pattern

Every ViewModel exposes:
- `viewState: LiveData<ViewState>` — a sealed class with `Loading`, `Error`, `Success(data)` states.
- `viewCommand: SingleLiveEvent<ViewCommand>` — one-shot navigation/action events (e.g. `ShowCoinDetails`).

Coroutines are launched with `viewModelScope.launch { runCatching { ... }.onSuccess { }.onFailure { } }`.

### DI (Hilt)

- `NetworkModule` — provides `Cache`, `HttpLoggingInterceptor`, `OkHttpClient`, `Retrofit`, and `CoinsService`. Base URL: `https://api.coingecko.com/api/v3/`.
- `DatabaseModule` — provides `CryptoCoinsDatabase` and its DAOs.
- Both are `@InstallIn(SingletonComponent::class)`.

### UI Layer

Features are organised by screen under `ui/`:
- `coins/` — list of coins (Fragment + ViewModel + Adapter + ViewHolder + DiffCallback + ItemDecoration)
- `coindetails/` — detail view for a single coin
- `exchanges/` — list of exchanges
- `exchangedetails/` — detail view for a single exchange
- `main/` — `MainActivity` hosts the fragments via bottom navigation

ViewBinding is enabled project-wide.

## Testing Conventions

- Test framework: **JUnit 5** (configured via `useJUnitPlatform()`). JUnit 4 vintage engine is also on the classpath.
- Mocking: **MockK** (`@RelaxedMockK` annotations, initialized in `BaseTest.setUp()`).
- Fake data: **JavaFaker** accessed through `BaseFaker`; per-domain faker objects live in `test/.../fakers/` (e.g. `CoinFaker`, `ImageFaker`).
- All test classes extend `BaseTest`, which registers `InstantExecutorExtension` (runs `LiveData` synchronously) and calls `clearAllMocks()` after each test.
- Coroutine tests call `Dispatchers.setMain(newSingleThreadContext(...))` in `@BeforeEach` and `Dispatchers.resetMain()` in `@AfterEach`.
- Test structure follows **Given / When / Then** comments.
- ViewModel tests verify `ViewState` and `ViewCommand` sequences using MockK's `verifySequence { }` / `verify { }`.

## Notes

- The `data/respositories` package has a typo (missing `o`) — match it exactly when adding new files there.
- Room schema export directory is `app/schemas/`.
- DB migrations live in `data/database/migrations/` and must be registered in `CryptoCoinsDatabase`.
