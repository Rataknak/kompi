# Kompi - Android Book Application

An Android application built with Jetpack Compose for managing books with authentication features.

## Technologies Used

- **Language**: Kotlin 2.0.21
- **Platform**: Android (Min SDK 24, Target SDK 34)
- **UI Framework**: Jetpack Compose with Material3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit 2.9.0, OkHttp, Gson
- **Async Operations**: Kotlin Coroutines & Flow
- **Build System**: Gradle 8.2.1 with Kotlin DSL

## Prerequisites

Before running this project, ensure you have the following installed:

1. **Android Studio** (Latest version recommended - Hedgehog or later)
   - Download from: https://developer.android.com/studio

2. **JDK 17** (Required for this project)
   - Android Studio typically includes this

3. **Android SDK** with the following components:
   - Android SDK Platform 34
   - Android SDK Build-Tools
   - Android Emulator (if running on emulator)

4. **Backend API Server**
   - The app connects to a backend API at `http://10.0.2.2:8080/`
   - `10.0.2.2` is the special alias to your host loopback interface (localhost) from Android Emulator
   - Ensure your backend server is running on `localhost:8080` before launching the app

## How to Run the Project

### Method 1: Using Android Studio (Recommended)

1. **Open the Project**
   ```
   - Launch Android Studio
   - Click "Open" or "Open an Existing Project"
   - Navigate to: C:\Users\Vireak\IdeaProjects\kompi
   - Click "OK"
   ```

2. **Sync Gradle**
   - Android Studio will automatically start syncing Gradle
   - If not, click: File → Sync Project with Gradle Files
   - Wait for the sync to complete (first time may take a few minutes)

3. **Set Up an Android Device**

   **Option A: Physical Device**
   - Enable Developer Options on your Android device
   - Enable USB Debugging
   - Connect your device via USB
   - Accept the USB debugging prompt on your device

   **Option B: Android Emulator**
   - Click "Device Manager" in Android Studio
   - Click "Create Device"
   - Select a device (e.g., Pixel 5)
   - Select a system image (API 34 or 24+)
   - Click "Finish"
   - Start the emulator

4. **Start Backend Server**
   - Ensure your backend API server is running on `localhost:8080`
   - The app expects endpoints for authentication (login, register, OTP verification)

5. **Run the Application**
   - Select your device/emulator from the device dropdown
   - Click the green "Run" button (▶️) or press Shift+F10
   - Wait for the build to complete
   - The app will automatically install and launch on your device

### Method 2: Using Command Line

1. **Navigate to Project Directory**
   ```powershell
   cd C:\Users\Vireak\IdeaProjects\kompi
   ```

2. **Build the Project**
   ```powershell
   .\gradlew.bat build
   ```

3. **Install on Connected Device**
   ```powershell
   .\gradlew.bat installDebug
   ```

4. **Run on Connected Device**
   ```powershell
   .\gradlew.bat installDebug
   adb shell am start -n com.example.kompi_app/.screen.MainActivity
   ```

### Method 3: Build APK for Manual Installation

1. **Build Debug APK**
   ```powershell
   cd C:\Users\Vireak\IdeaProjects\kompi
   .\gradlew.bat assembleDebug
   ```

2. **Locate the APK**
   - APK will be generated at:
   - `app\build\outputs\apk\debug\app-debug.apk`

3. **Install Manually**
   ```powershell
   adb install app\build\outputs\apk\debug\app-debug.apk
   ```

## Project Structure

```
kompi/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/kompi_app/
│   │   │   │   ├── model/          # Data models
│   │   │   │   ├── network/        # API service & Retrofit
│   │   │   │   ├── viewmodel/      # ViewModels
│   │   │   │   ├── screen/         # Compose UI screens
│   │   │   │   ├── navigation/     # Navigation setup
│   │   │   │   └── ui/theme/       # Theme & styling
│   │   │   ├── res/                # Resources (drawables, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                   # Unit tests
│   │   └── androidTest/            # Instrumented tests
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
└── settings.gradle.kts
```

## Key Features

- User Authentication (Login/Register)
- OTP Verification
- Book Browsing
- Categories
- Favorites
- Modern UI with Material3 Design

## API Configuration

The app connects to a backend API. The base URL is configured in:
- **File**: `app/src/main/java/com/example/kompi_app/network/RetrofitInstance.kt`
- **Current URL**: `http://10.0.2.2:8080/` (for emulator)

**Note**: If running on a physical device, you'll need to update the BASE_URL to your computer's actual IP address (e.g., `http://192.168.1.100:8080/`)

## Troubleshooting

### Build Fails
- Ensure you have JDK 17 installed
- Try: File → Invalidate Caches → Invalidate and Restart
- Clean build: `.\gradlew.bat clean build`

### App Crashes on Launch
- Check if the backend server is running
- Verify network permissions in AndroidManifest.xml
- Check Logcat in Android Studio for error messages

### Cannot Connect to API
- **On Emulator**: Backend should be on `localhost:8080`
- **On Physical Device**: Update BASE_URL to your computer's IP address
- Ensure firewall allows connections
- Check that backend server is running and accessible

### Gradle Sync Issues
- Check internet connection (first sync downloads dependencies)
- Try: File → Sync Project with Gradle Files
- Delete `.gradle` folder and sync again

## Testing

### Run Unit Tests
```powershell
.\gradlew.bat test
```

### Run Instrumented Tests
```powershell
.\gradlew.bat connectedAndroidTest
```

## Building for Production

### Build Release APK
```powershell
.\gradlew.bat assembleRelease
```

**Note**: You'll need to configure signing keys for release builds in `app/build.gradle.kts`

## Dependencies

Key dependencies include:
- Jetpack Compose & Material3
- Retrofit & OkHttp (Networking)
- Gson (JSON parsing)
- Navigation Compose
- Lifecycle & ViewModel
- Coroutines & Flow

For complete dependency list, see `app/build.gradle.kts` and `gradle/libs.versions.toml`

## License

[Add your license information here]

## Contact

[Add your contact information here]

