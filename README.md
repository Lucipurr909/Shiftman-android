# Shiftman Android - Offline Shift Manager

A completely offline Android app for managing work shifts with no login required. All data is stored locally on your device.

## Features

✅ **No Internet Required** - Works completely offline  
✅ **No Login** - All data stored locally on device  
✅ **Natural Language Parsing** - Enter shifts like "Mon 8-4" or "15th 09:00 to 17:00"  
✅ **Shift Types** - Day, Night, Extra, Off, Vacation  
✅ **Calendar View** - See your schedule at a glance  
✅ **Fast & Lightweight** - SQLite database with Room ORM  

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Database**: Room (SQLite)
- **Architecture**: MVVM with Repositories
- **Async**: Kotlin Coroutines + Flow

## Project Structure

```
app/src/main/
├── java/com/example/shiftmanager/
│   ├── data/
│   │   ├── ShiftEntity.kt          # Database entity
│   │   ├── ShiftDao.kt             # Database queries
│   │   ├── ShiftDatabase.kt        # Room setup
│   │   └── ShiftRepository.kt      # Data access layer
│   ├── domain/
│   │   ├── Shift.kt                # Domain model
│   │   └── ScheduleParser.kt       # Offline parser
│   ├── ui/
│   │   ├── theme/                  # Material Design 3 theme
│   │   ├── screens/
│   │   │   ├── CalendarScreen.kt   # Main calendar view
│   │   │   └── AddShiftScreen.kt   # Add/Edit shift
│   │   └── MainActivity.kt
│   └── ShiftManagerApp.kt          # Navigation
└── AndroidManifest.xml
```

## Getting Started

### Prerequisites
- Android Studio Giraffe or newer
- Android SDK 24+
- Kotlin 1.8+

### Setup

1. Clone the repository
   ```bash
   git clone https://github.com/Lucipurr909/Shiftman-android.git
   cd Shiftman-android
   ```

2. Open in Android Studio
   - File → Open → Select project folder

3. Sync Gradle
   - Build → Sync Now

4. Run
   ```bash
   ./gradlew installDebug
   ```
   Or use Android Studio's Run button

## Usage

### Adding Shifts

The app supports natural language shift parsing:

- **Day of week**: `Mon 8-4` → Creates Monday 8:00 AM - 4:00 PM
- **Date number**: `15th 09:00 to 17:00` → Creates shift on 15th of current month
- **ISO date**: `2024-05-20: 14:30-22:30` → Specific date and time
- **Special types**: `off`, `vacation` → Creates all-day shifts

Examples:
```
Mon 8-4
Tues 12-8
Wed 09:00 to 17:00
15th 09:00 to 17:00
2024-05-20: 14:30-22:30
off
vacation
```

### Shift Types

- **Day**: Regular day shift
- **Night**: Night/graveyard shift
- **Extra**: Additional/overtime shift
- **Off**: Day off
- **Vacation**: Vacation day

## Data Storage

All shifts are stored in a local SQLite database (via Room ORM):
- **Location**: App's private directory
- **Backup**: Data persists across app updates
- **Export**: Manual export via file sharing (future feature)

## Building for Release

```bash
./gradlew assembleRelease
```

Unsigned APK: `app/build/outputs/apk/release/app-release-unsigned.apk`

## Differences from Web Version

| Feature | Web | Android |
|---------|-----|----------|
| Authentication | Firebase Auth | None |
| Database | Cloud Firestore | Local SQLite |
| AI Parser | Gemini API | Local regex parser |
| Internet | Required | Not required |
| Login | Required | Not needed |
| Data Backup | Automatic | Manual export (future) |

## Offline Parsing Logic

The `ScheduleParser` uses regex patterns to understand common shift input formats without requiring internet or AI services:

```kotlin
// Examples that work offline:
parseSchedule("Mon 8-4")                    // Monday 08:00-16:00
parseSchedule("15th 09:00 to 17:00")        // 15th of current month
parseSchedule("2024-05-20: 14:30-22:30")    // ISO format
parseSchedule("off")                        // All-day off
```

## Future Features

- [ ] Cloud sync (optional)
- [ ] Shift reminders/notifications
- [ ] Statistics & analytics
- [ ] Export to calendar
- [ ] Dark mode
- [ ] Recurring shifts
- [ ] Manual recurring patterns

## License

MIT

## Author

Lucipurr909
