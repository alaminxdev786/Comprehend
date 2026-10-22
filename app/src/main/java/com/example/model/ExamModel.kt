package com.example.model

data class Question(
    val id: Int,
    val subject: SubjectType,
    val topic: String,
    val questionBn: String,
    val questionEn: String,
    val optionsBn: List<String>,
    val optionsEn: List<String>,
    val correctIndex: Int,
    val explanationBn: String,
    val explanationEn: String,
    val sourceOrYear: String // e.g. "৪৪তম বিসিএস প্রিলিমিনারি"
)

enum class SubjectType(val titleBn: String, val titleEn: String, val iconName: String) {
    BANGLA("বাংলা ভাষা ও সাহিত্য", "Bangla Language & Lit", "MenuBook"),
    ENGLISH("ইংরেজি ভাষা ও সাহিত্য", "English Language", "Language"),
    BANGLADESH("বাংলাদেশ বিষয়াবলী", "Bangladesh Affairs", "Flag"),
    INTERNATIONAL("আন্তর্জাতিক বিষয়াবলী", "International Affairs", "Public"),
    MATH("গাণিতিক যুক্তি ও মানসিক দক্ষতা", "Mathematics & Mental Ability", "Calculate"),
    SCIENCE("সাধারণ বিজ্ঞান ও তথ্যপ্রযুক্তি", "General Science & ICT", "Science")
}

data class QuizState(
    val subject: SubjectType?,
    val isMockExam: Boolean = false,
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val userAnswers: Map<Int, Int> = emptyMap(), // questionIndex -> selectedOptionIndex
    val bookmarks: Set<Int> = emptySet(),
    val isSubmitted: Boolean = false,
    val score: Double = 0.0,
    val timeRemainingSeconds: Int = 120,
    val isTimerActive: Boolean = false
)

data class ExamResultRecord(
    val id: String,
    val subjectTitle: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val skippedAnswers: Int,
    val score: Double,
    val dateFormatted: String
)
