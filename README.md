# MovieApp

A modern Android movie library app built with **Jetpack Compose**, **Clean Architecture**, and **TMDB API** integration.

## Features

### Current Features (v1.0)
- **Popular Movies** - Today & This Week trending
- **Upcoming Movies** - Latest theatrical releases
- **Favorites System** - Save and manage favorite movies
- **Dark/Light Theme** - Persistent theme switching
- **Modern UI** - Material Design 3 with Jetpack Compose
- **Clean Architecture** - MVVM pattern with proper separation
- **Reactive UI** - Real-time updates with Kotlin Flow
- **Offline Favorites** - Local database storage
- **Image Caching** - Optimized poster loading

## Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Presentation  │    │     Domain      │    │      Data       │
│                 │    │                 │    │                 │
│  • UI Screens   │◄──►│  • Use Cases    │◄──►│  • Repositories │
│  • ViewModels   │    │  • Models       │    │  • Data Sources │
│  • Compose UI   │    │  • Repositories │    │  • API/Database │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Tech Stack
- **UI**: Jetpack Compose, Material Design 3
- **Architecture**: Clean Architecture, MVVM
- **DI**: Dagger Hilt
- **Network**: Retrofit, OkHttp, Moshi
- **Database**: Room (SQLite)
- **Image Loading**: Coil
- **Async**: Kotlin Coroutines, Flow
- **Testing**: JUnit, MockK, Coroutines Test

## Quick Setup

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or later
- JDK 11 or higher
- Android SDK 34+
- TMDB API Key (free)

### 1. Clone Repository
```bash
git clone https://github.com/enonder/MovieApp.git
cd MovieApp
```

### 2. Get TMDB API Key
1. Visit [TMDB](https://www.themoviedb.org/)
2. Create account and request API key
3. Copy your API key

### 3. Configure API Key
Create/edit `local.properties` in project root:
```properties
tmdb_api_key="your_actual_api_key_here"
```

### 4. Build & Run
```bash
./gradlew clean build
./gradlew installDebug
```

## Testing

### Current Test Coverage
The project includes **essential unit tests** focused on core functionality:

```bash
# Run all tests
./gradlew test
```

### Test Structure
```
app/src/test/
├── domain/model/
│   └── MovieTest.kt              # Domain model tests
├── viewmodel/
│   └── HomeViewModelTest.kt      # Main screen business logic
└── util/
    └── ResourceTest.kt           # Error handling wrapper
```

### Test Philosophy
- **Focused on business logic** - ViewModels, critical utilities
- **Essential coverage** - Core functionality validation
- **Easy to maintain** - Simple, readable test cases

## Performance & Storage

### Local Data Storage
1. **Favorites Database**
   ```kotlin
   // Room Database - Persistent local storage
   @Entity(tableName = "favorite_movies")
   data class FavoriteMovieEntity(...)
   ```

2. **Theme Preferences**
   ```kotlin
   // DataStore - Persistent user settings
   private val dataStore: DataStore<Preferences>
   ```

3. **Image Loading**
   ```kotlin
   // Coil - Automatic memory + disk caching
   AsyncImage(
       model = movie.getPosterUrl(),
       contentDescription = movie.title
   )
   ```

### Performance Benefits
- **Instant favorites access** - Local database storage
- **Optimized image loading** - Coil handles caching automatically
- **Persistent theme** - Settings saved across app sessions
- **Reactive UI** - Automatic updates with Kotlin Flow

## Usage Guide

### Navigation
- **Home Tab** - Browse trending movies (Today/This Week) and upcoming releases
- **Favorites Tab** - Manage saved movies
- **Settings Tab** - Toggle dark/light theme

### Key Interactions
- **Tap movie poster** - View movie details
- **Heart icon** - Add/remove from favorites
- **Time period toggle** - Switch between Today/This Week
- **Tap on favorites** - Select multiple items for bulk removal

### Offline Capabilities
- **Favorites** - Full offline access
- **Cached images** - Previously viewed posters
- **Theme settings** - Persists across sessions

## Potential Future Enhancements

### Enhanced Features
- **Search Functionality** - Find specific movies
- **Advanced Filtering** - Genre, rating, year filters
- **Movie Trailers** - In-app video playback
- **Grid/List Toggle** - Multiple view options
- **Pull to Refresh** - Manual list updates

### Improved Offline Experience
- **Movie List Caching** - Browse without internet
- **Smart Cache Management** - Configurable storage limits
- **Background Sync** - Auto-update when online

### Extended Testing
- **Repository Tests** - Data layer validation
- **UI Tests** - Compose testing framework
- **Integration Tests** - End-to-end workflows

## Project Structure

```
├── data/
│   ├── local/
│   │   ├── dao/           # Room DAOs
│   │   ├── database/      # Database setup
│   │   └── entity/        # Room entities
│   ├── preferences/       # DataStore preferences
│   ├── remote/
│   │   ├── dto/           # API response models
│   │   └── TmdbApi.kt     # Retrofit interface
│   └── repository/        # Repository implementations
├── di/                    # Dependency injection modules
├── domain/
│   ├── model/             # Domain models
│   ├── repository/        # Repository interfaces
│   └── use_case/          # Business logic
├── navigation/            # Navigation components
├── ui/
│   ├── components/        # Reusable UI components
│   ├── screens/           # Screen composables
│   └── theme/             # Material Design theme
├── util/                  # Utility classes
├── viewmodel/             # ViewModels
├── MainActivity.kt        # Main activity
├── MainScreen.kt          # Main screen composable
└── MovieApplication.kt    # Application class
```

---

<div align="center">
  Made by <a href="https://github.com/enonder">Elifnur Onder</a>
</div>
