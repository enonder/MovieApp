# MovieApp

A modern Android movie discovery app built with **Jetpack Compose**, **Clean Architecture**, and **TMDB API** integration.

<div align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" />
  <img src="https://img.shields.io/badge/Material%20Design%203-757575?style=for-the-badge&logo=materialdesign&logoColor=white" />
</div>

## Features

### Current Features (v1.0)
- **Browse Popular Movies** - Today & This Week trending
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

# Run with coverage
./gradlew testDebugUnitTestCoverage
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
- **Realistic for rapid development** - 6 tests covering core functionality
- **Easy to maintain** - Simple, readable test cases

## Performance & Caching

### Current Caching Mechanisms

#### Implemented
1. **Favorites Caching**
   ```kotlin
   // Room Database - Persistent local storage
   @Entity(tableName = "favorite_movies")
   data class FavoriteMovieEntity(...)
   ```

2. **Image Caching**
   ```kotlin
   // Coil - Automatic memory + disk caching
   AsyncImage(
       model = movie.getPosterUrl(),
       contentDescription = movie.title
   )
   ```

3. **Theme Preferences**
   ```kotlin
   // DataStore - Persistent user settings
   private val dataStore: DataStore<Preferences>
   ```

#### Performance Benefits
- **Faster app launches** - Favorites load instantly
- **Reduced bandwidth** - Images cached automatically  
- **Better UX** - Smooth scrolling with cached images
- **Offline favorites** - Works without internet

### Memory Management
- **Singleton repositories** - Efficient resource usage
- **Flow-based reactive UI** - Automatic lifecycle management
- **Lazy loading** - Components created when needed

## Usage Guide

### Navigation
- **Home Tab** - Browse trending movies (Today/This Week)
- **Upcoming Tab** - See future releases
- **Favorites Tab** - Manage saved movies
- **Settings Tab** - Toggle dark/light theme

### Key Interactions
- **Tap movie poster** - View movie details
- **Heart icon** - Add/remove from favorites
- **Time period toggle** - Switch between Today/This Week
- **Pull to refresh** - Update movie lists
- **Long press** - Bulk favorite management

### Offline Capabilities
- **Favorites** - Full offline access
- **Cached images** - Previously viewed posters
- **Theme settings** - Persists across sessions
- **Movie lists** - Requires internet connection

## Future Enhancements (v2.0)

### Internationalization
- **Region Selection** - Movies by country (US, UK, IN, etc.)
- **Language Support** - Multi-language movie titles
- **Localized UI** - App interface in user's language
- **Cultural Preferences** - Region-specific trending content

### Enhanced UI/UX
- **Animated Transitions** - Smooth screen changes
- **Advanced Filtering** - Genre, rating, year filters
- **Grid/List Toggle** - Multiple view options
- **Movie Trailers** - In-app video playback
- **Search Functionality** - Find specific movies
- **Recommendations** - AI-powered suggestions

### Offline Experience
- **Movie List Caching** - Browse without internet
- **Smart Cache Management** - Configurable storage limits
- **Sync Strategy** - Background updates when online
- **Cache Expiration** - Auto-refresh stale data

### Advanced Testing
- **Repository Tests** - Data layer validation
- **Integration Tests** - End-to-end workflows  
- **UI Tests** - Compose testing framework
- **Performance Tests** - Memory and speed benchmarks
- **API Tests** - Network layer validation

## Implementation Roadmap

### Phase 1: Core Enhancements (1-2 weeks)
```kotlin
// Movie List Caching
@Entity(tableName = "cached_movies")
data class CachedMovieEntity(
    @PrimaryKey val id: Int,
    val category: String, // "popular", "trending_today", etc.
    val movieData: String, // JSON serialized movie
    val cachedAt: Long,
    val expiresAt: Long
)

// Region & Language Support
data class UserPreferences(
    val region: String = "US",
    val language: String = "en",
    val isDarkTheme: Boolean = false
)
```

### Phase 2: Advanced Features (2-3 weeks)
- Search with autocomplete
- Movie detail screens with cast/crew
- Recommendation engine
- Social features (ratings, reviews)

### Phase 3: Polish & Performance (1 week)
- Advanced animations
- Performance optimizations
- Accessibility improvements
- Comprehensive testing

## Project Structure

```
app/src/main/java/com/elifnuronder/movieapp/
├── data/
│   ├── local/
│   │   ├── dao/           # Room DAOs
│   │   ├── database/      # Database setup
│   │   └── entity/        # Room entities
│   ├── remote/
│   │   ├── dto/           # API response models
│   │   └── TmdbApi.kt        # Retrofit interface
│   └── repository/        # Repository implementations
├── di/                    # Dependency injection
├── domain/
│   ├── model/             # Domain models
│   ├── repository/        # Repository interfaces
│   └── use_case/          # Business logic
├── ui/
│   ├── components/        # Reusable UI components
│   ├── screens/           # Screen composables
│   └── theme/             # Material Design theme
├── util/                  # Utility classes
└── viewmodel/             # ViewModels
```

## Contributing

### Development Setup
1. Fork the repository
2. Create feature branch: `git checkout -b feature/amazing-feature`
3. Follow existing code style and architecture
4. Add tests for new functionality
5. Commit changes: `git commit -m 'Add amazing feature'`
6. Push to branch: `git push origin feature/amazing-feature`
7. Open Pull Request

### Code Style
- Follow [Android Kotlin Style Guide](https://developer.android.com/kotlin/style-guide)
- Use meaningful variable/function names
- Add KDoc comments for public APIs
- Keep functions small and focused

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [TMDB API](https://www.themoviedb.org/documentation/api) - Movie data provider
- [Material Design 3](https://m3.material.io/) - Design system
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern UI toolkit
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) - Architecture principles

---

<div align="center">
  Made by <a href="https://github.com/enoder">Elifnur Onder</a>
</div>
