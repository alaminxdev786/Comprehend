# Comprehend — All-in-One Bengali Super App (কমপ্রিহেন্ড)

Comprehend is a modular, Bengali-first Android super application designed for students, job aspirants, and everyday citizens in Bangladesh. It unifies BCS and government job exam preparation, spoken English vocabulary, Bengali/Gregorian/Hijri calendars, location-aware Islamic prayer times, everyday utility calculators, kids foundational education (NCTB syllabus), and document utilities into an intuitive Material 3 interface.

### User Review & Critical Decisions

> [!IMPORTANT]
> The following architectural decisions have been aligned based on your development environment and requirements:
> - **Platform & Technology Stack**: Built natively with modern Android (Kotlin 2.2 + Jetpack Compose + Material 3) in this AI Studio cloud environment. This enables instant compilation, testing on the streaming Android emulator, and clean offline-first architecture.
> - **Language Architecture**: Bengali primary UI with an instant English toggle in Settings; all questions, lessons, and labels support bilingual rendering.
> - **Full Modular Scope**: Structured architecture accommodating all requested modules (Home Dashboard, BCS Exam Prep, Spoken English, Calendar & Prayer Times, Daily & Financial Calculators, Kids Education, and Utilities).
> - **Data Persistence & Offline Resilience**: Local SQLite persistence via Room for question banks, vocabulary cards, calculator history, and bookmarks so core features work 100% offline without cellular network dependence.

- **Confirmed Decision 1**: Native Android (Kotlin + Jetpack Compose + Material 3) is selected for seamless emulator preview, fast builds, and high touch responsiveness.
- **Confirmed Decision 2**: Bengali primary interface with an English switch in TopAppBar/Settings for instant language localization.
- **Confirmed Decision 3**: Complete core module architecture covering Exam Prep, English, Islamic & Calendar, Kids Education, and Everyday Calculators in the first release.

---

### 1. Overview & Core Concept

- **What It Does**:
  - **Home Dashboard (হোম)**: Categorized Bengali dashboard featuring search, quick access cards, daily streak, daily vocabulary word, and upcoming prayer time card.
  - **BCS & Job Prep (চাকরির প্রস্তুতি)**: Subject-wise authentic MCQs (Bangla, English, Math, General Knowledge, Science), timed mock exam tests, instant score calculation, detailed Bengali explanations, wrong-answer review, and bookmarks.
  - **Spoken English & Vocabulary (ইংরেজি শেখা)**: Everyday spoken dialogues, high-frequency vocabulary flashcards with Bengali definitions, phonetics, and example sentences.
  - **Bengali, Gregorian & Hijri Calendar (দিনপঞ্জি)**: Triple-date synchronized calendar with Bangladesh national holidays, government holiday markers, and Islamic moon-sighting notices.
  - **Islamic Utilities (নামাজের সময়সূচি)**: 5 daily prayer times (Fajr, Dhuhr, Asr, Maghrib, Isha) plus Sehri and Iftar times with division-based prayer timing calculation and manual district selector.
  - **Everyday Calculators (ক্যালকুলেটর ও হিসাব)**: Standard calculator, Percentage calculator, Profit/Loss business calculator, and Land/Unit converter (শতক, কাঠা, বিঘা to sq. ft / sq. meter).
  - **Kids Preschool to Class 5 Learning (ছোটদের পাঠশালা)**: Bengali alphabet (স্বরবর্ণ, ব্যঞ্জনবর্ণ), counting (১–১০০), basic arithmetic exercises with visual feedback, and rhymes.
  - **Document & Utilities (ডকুমেন্ট টুলস)**: QR code scanner and generator, on-device text-to-speech reader for Bengali text.

- **Target Audience**:
  - BCS & Government job candidates in Bangladesh.
  - Students and language learners building spoken English fluency.
  - Parents and young learners (Preschool to Class 5).
  - Everyday citizens seeking accurate Bengali dates, prayer times, and business calculation tools.

- **Key Value**:
  - 100% offline accessibility for learning and calculation modules.
  - High fidelity, authentic Bengali typography and culturally resonant Material 3 design.
  - No bloated web wrappers or laggy hybrid rendering.

---

### 2. User Experience & Visual Design

- **Visual Identity & Atmosphere**:
  - *Aesthetic Direction*: Warm, scholarly Bengali editorial meets modern Google Material 3 ("বাঙালিয়ানা ও আধুনিকতা").
  - *Color Tokens*:
    - **Primary**: Deep Forest Green (`#006A4E` - Bangladesh National Flag Green tone) with Container (`#A3F2CD`).
    - **Secondary & Accent**: Rich Terracotta / Crimson Accent (`#BA1A1A` / `#F44336` - Solar Red) and Warm Golden Ochre (`#8D6E63` / `#D4A373`).
    - **Surface & Background**: Pure Soft Off-White (`#FCFDF6`) in light mode; Deep Slate Navy (`#111927`) in dark mode.
    - **Card Elevation**: Layered elevated tonal cards with 16dp rounded corners and subtle border strokes.
  - *Typography*:
    - Clear, highly legible Bengali Unicode typography paired with clean sans-serif for numbers and English terms.
    - Standard font scaling using `sp` units to ensure accessibility across Android font scale settings.

- **Navigation & Layout Structure**:
  - **Bottom Navigation Bar (M3)** with 4 primary destinations:
    1. `হোম (Home)`: Hub of all feature cards, daily tips, and active shortcuts.
    2. `প্রস্তুতি (Job Prep)`: BCS categories, mock tests, and performance history.
    3. `শেখা (Learning)`: Spoken English, Vocabulary, and Kids School.
    4. `টুলস (Utilities)`: Prayer times, Calendars, Calculators, QR tools.
  - **Top App Bar**:
    - App title ("Comprehend | কমপ্রিহেন্ড"), Language quick-switch toggle (`বাংলা / EN`), and Settings/Dark mode action.
  - **Edge-to-Edge & Accessibility**:
    - Full edge-to-edge rendering with `WindowInsets.safeDrawing` and navigation bar padding.
    - Minimum 48dp touch targets on all quiz options, calculator keys, and navigation items.

---

### 3. Key Product Decisions & Trade-Offs

- **Decision 1: Native Jetpack Compose Architecture vs. Hybrid**:
  - *Chosen*: Pure Jetpack Compose with Kotlin coroutines and M3.
  - *Why*: Seamless execution within the AI Studio Android container, real-time live preview in the cloud emulator, zero setup friction for the user, and superior 60fps performance on low-end Android hardware.
- **Decision 2: Local Persistence Engine (Room Database)**:
  - *Chosen*: Android Room SQLite database pre-seeded with authentic BCS questions, vocabulary words, and local history.
  - *Why*: Reliable offline access everywhere in Bangladesh, instantaneous loading with zero API latency or network costs.
- **Decision 3: Calculation Algorithms for Calendars & Prayer Times**:
  - *Chosen*: On-device deterministic astronomical calculation algorithms adapted for Bangladesh districts (Karachi / Islamic Foundation Bangladesh method) with manual district picker (Dhaka, Chittagong, Rajshahi, Khulna, Sylhet, Barisal, Rangpur, Mymensingh).
  - *Why*: Works completely offline without external API rate-limiting or downtime.

---

### 4. Technical Architecture & Data Strategy

```
┌─────────────────────────────────────────────────────────────────┐
│                    Comprehend Android App                       │
│                     (Jetpack Compose UI)                        │
└────────────────────────────────┬────────────────────────────────┘
                                 │
         ┌───────────────────────┴───────────────────────┐
         ▼                                               ▼
┌─────────────────────────────────┐   ┌─────────────────────────────────┐
│        Presentation Layer       │   │           State & Nav           │
│ - HomeScreen & Search           │   │ - MainViewModel                 │
│ - JobPrepScreen (BCS Quiz & Exam│   │ - ExamViewModel & Timer         │
│ - EnglishLearningScreen         │   │ - CalendarPrayerViewModel       │
│ - CalendarPrayerScreen          │   │ - CalculatorViewModel           │
│ - KidsLearningScreen            │   │ - BottomBar Navigation Graph    │
│ - CalculatorScreen              │   │ - AppPreferences (Language/Theme│
│ - DocumentToolsScreen           │   └─────────────────────────────────┘
└────────────────┬────────────────┘                   │
                 │                                    │
                 ▼                                    ▼
┌───────────────────────────────────────────────────────────────────────┐
│                          Domain & Data Layer                          │
│ - ComprehendRepository (Unified Data Access)                          │
│ - ComprehendDatabase (Room SQLite Persistence)                        │
│   ├── QuestionDao & QuestionEntity (Subject-wise BCS MCQs)            │
│   ├── VocabularyDao & VocabEntity (English-Bangla terms & sentences)  │
│   ├── ExamAttemptDao (Saved scores, wrong answer bookmarks)           │
│   └── KidsLessonRepository (Alphabet, numbers, math drills)          │
│ - PrayerTimeCalculator (Bangladesh astronomical coordinates)          │
│ - BengaliCalendarEngine (Suryasiddhanta & reformed Bangla calendar)   │
└───────────────────────────────────────────────────────────────────────┘
```

#### Core Data Entities

1. **Question**: `id`, `subject` (Bangla, English, Math, General Knowledge, Science), `topic`, `questionTextBengali`, `options` (List<String>), `correctOptionIndex`, `explanationBengali`, `yearOrSource` (e.g. "44th BCS").
2. **VocabularyWord**: `id`, `word`, `pronunciation`, `meaningBengali`, `partOfSpeech`, `exampleSentence`, `exampleBengali`, `category` (Beginner, Intermediate, Daily Life).
3. **PrayerTimesSchedule**: `fajr`, `sunrise`, `dhuhr`, `asr`, `maghrib`, `isha`, `sehriEnd`, `iftarTime`, `district`.
4. **CalendarDay**: `gregorianDate`, `bengaliDay`, `bengaliMonth`, `bengaliYear`, `hijriDay`, `hijriMonth`, `hijriYear`, `isHoliday`, `holidayTitle`.
5. **ExamResult**: `id`, `timestamp`, `subject`, `totalQuestions`, `score`, `wrongQuestionsList`.

#### First Milestone Implementation Tasks
1. Update `metadata.json` and `res/values/strings.xml` to brand the application as **Comprehend | কমপ্রিহেন্ড**.
2. Build the cohesive Material 3 theme in `Theme.kt` with Bangladesh-inspired deep green and warm ochre color schemes, supporting both Light and Dark modes.
3. Configure Room Database and DAO repositories with pre-seeded authentic question banks and vocabulary items.
4. Implement the core **Home Dashboard** with module cards, search filter, and bottom navigation.
5. Implement the **BCS & Job Prep** module with full quiz interactive flow, countdown timer, explanation reveal, and score report.
6. Implement the **Spoken English** flashcard and dialogue trainer.
7. Implement the **Bengali & Hijri Calendar + Prayer Times** module with district selector.
8. Implement the **Multi-mode Calculator** (General, Business Profit/Loss, Land Converter).
9. Implement the **Kids Primary Education** module (অ, আ, ক, খ, ১-১০, নামতা ও অঙ্ক).
10. Test and compile the app with `compile_applet` to ensure 100% build health.
