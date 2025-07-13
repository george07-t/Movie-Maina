# 🎬 Movie Mania

A modern Android application for movie enthusiasts that combines movie discovery with intelligent chat functionality. Built with Java and powered by The Movie Database (TMDB) API and FastAPI with LangGraph AI agent integration.

![Movie Mania Banner](https://img.shields.io/badge/Platform-Android-green) ![API Level](https://img.shields.io/badge/API-26+-blue) ![Language](https://img.shields.io/badge/Language-Java-orange)

## ✨ Features

### 🎯 Core Functionality
- **Upcoming Movies**: Browse the latest upcoming movies with rich details
- **Movie Details**: Comprehensive information including ratings, genres, overview, and taglines
- **Movie Recommendations**: Discover similar movies based on your selection
- **Favorites System**: Save and manage your favorite movies locally
- **Smart Chat Bot**: AI-powered movie assistant for recommendations and queries

### 🎨 User Interface
- **Material Design**: Modern, clean interface following Material Design principles
- **Responsive Layouts**: Optimized for different screen sizes
- **Smooth Animations**: Enhanced user experience with loading animations
- **Image Caching**: Efficient image loading with Glide library

### 🤖 Chat Features
- **Intelligent Bot**: Movie-focused AI assistant
- **Typing Animation**: Realistic typing effects for bot responses
- **Markdown Support**: Rich text formatting in chat messages
- **Session Management**: Persistent chat sessions with delete functionality
- **Real-time Communication**: Instant responses with loading indicators

## 📱 Screenshots

| Main Screen | Movie Details | Favorites | Chat Interface |
|-------------|---------------|-----------|----------------|
| <img src="https://github.com/user-attachments/assets/f6f6d40f-a9e7-4f91-ac66-066c25196d62" height="500" width="200" alt="Main Screen"/> | <img src="https://github.com/user-attachments/assets/9958ca90-72de-4ab0-9926-049bb8bcbb02" height="500" width="200" alt="Movie Details"/> | <img src="https://github.com/user-attachments/assets/0cba7d17-6134-42bc-bf42-84df6cb2599c" height="500" width="200" alt="Favorites"/> | <img src="https://github.com/user-attachments/assets/b56e8043-1a7c-4b4e-9a73-36c5b280ba9b" height="500" width="200" alt="Chat Interface"/> |

## 🛠️ Technology Stack

### Core Technologies
- **Language**: Java
- **Platform**: Android (API 26+)
- **Architecture**: MVP Pattern
- **Build System**: Gradle with Kotlin DSL

### Libraries & Dependencies
- **Networking**: Retrofit 2.9.0 + OkHttp
- **JSON Parsing**: Gson
- **Image Loading**: Glide 4.15.1
- **UI Components**: Material Components
- **Data Binding**: View Binding
- **Markdown Rendering**: Markwon
- **Async Operations**: Java 8+ desugaring

### APIs
- **TMDB API**: Movie data and images
- **Custom Chat API**: AI-powered movie recommendations

## 🏗️ Project Structure

```
app/
├── src/main/java/com/example/moviemania/
│   ├── adapter/           # RecyclerView adapters
│   │   ├── MovieAdapter.java
│   │   └── ChatAdapter.java
│   ├── api/              # Network layer
│   │   ├── ApiInterface.java
│   │   ├── RetroInstance.java
│   │   └── TokenInterceptor.java
│   ├── classes/          # Data models
│   │   ├── Movie.java
│   │   ├── MovieDetails.java
│   │   ├── ChatMessage.java
│   │   └── ChatResponse.java
│   ├── utility/          # Helper classes
│   │   ├── MySharedPref.java
│   │   ├── Utility.java
│   │   └── Splash.java
│   └── activities/       # Main activities
│       ├── MainActivity.java
│       ├── MovieDetailsActivity.java
│       ├── FavoriteActivity.java
│       └── ChatActivity.java
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK (API 26+)
- Java 8 or higher
- TMDB API Key

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/george07-t/Movie-Maina.git
   cd Movie-Mania-main
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Click "Open an existing Android Studio project"
   - Navigate to the cloned directory

3. **Configure API Keys**
   - Get your TMDB API key from [TMDB](https://www.themoviedb.org/settings/api)
   - Add your API key to the `TokenInterceptor.java` file

4. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```

## 📦 Key Components

### Activities
- **SplashActivity**: App launch screen with 3-second delay
- **MainActivity**: Home screen with upcoming movies grid
- **MovieDetailsActivity**: Detailed movie information and recommendations
- **FavoriteActivity**: User's saved favorite movies
- **ChatActivity**: AI-powered movie chat interface

### Adapters
- **MovieAdapter**: Grid layout for movie posters with ratings
- **ChatAdapter**: Chat interface with typing animations and markdown support

### Data Management
- **SharedPreferences**: Local storage for favorite movies
- **Retrofit**: RESTful API communication
- **Gson**: JSON serialization/deserialization

## 🎯 Features Breakdown

### Movie Discovery
- **Grid Layout**: Visual movie browsing experience
- **Progressive Loading**: Smooth data loading with progress indicators
- **Error Handling**: Robust error management with user feedback
- **Image Optimization**: Efficient poster loading and caching

### Favorites System
- **Local Storage**: Persistent favorite movies using SharedPreferences
- **Toggle Functionality**: Easy add/remove from favorites
- **Visual Feedback**: Heart icons indicating favorite status
- **Dedicated Screen**: Separate view for managing favorites

### Chat Intelligence
- **Session Management**: Unique chat sessions with persistence
- **Typing Effects**: Word-by-word typing animation
- **Markdown Support**: Rich text formatting for bot responses
- **Loading States**: Visual feedback during API calls

## 🔧 Configuration

### Network Configuration
```java
// Base URLs
TMDB API: https://api.themoviedb.org/3/movie/
Chat API: https://movie-mania-agent.onrender.com/
```

### Build Configuration
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34
- **Java Version**: 11

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**George** - [@george07-t](https://github.com/george07-t)

## 🙏 Acknowledgments

- [The Movie Database (TMDB)](https://www.themoviedb.org/) for providing movie data
- [Material Design](https://material.io/) for design guidelines
- [Glide](https://github.com/bumptech/glide) for image loading
- [Retrofit](https://square.github.io/retrofit/) for networking
- [Markwon](https://github.com/noties/Markwon) for markdown rendering

## 📞 Support

If you have any questions or need help, please:
- Open an issue on GitHub
- Contact via email (if provided)
- Check the documentation

---

⭐ **Star this repository if you found it helpful!** ⭐
