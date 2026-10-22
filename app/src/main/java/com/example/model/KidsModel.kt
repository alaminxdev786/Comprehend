package com.example.model

enum class AlphabetType(val titleBn: String, val titleEn: String) {
    VOWELS("স্বরবর্ণ (১১টি)", "Vowels (11)"),
    CONSONANTS("ব্যঞ্জনবর্ণ (৩৯টি)", "Consonants (39)"),
    NUMBERS("সংখ্যা ও গণনা (১-১০)", "Numbers (1-10)"),
    TABLES("নামতা (১-১০)", "Multiplication Tables"),
    MATH_DRILL("ছোটদের অঙ্ক", "Arithmetic Drills")
}

data class AlphabetItem(
    val letter: String,
    val wordBn: String,
    val wordEn: String,
    val pronunciationBn: String,
    val symbolOrEmoji: String,
    val exampleSentenceBn: String
)

data class KidsMathProblem(
    val num1: Int,
    val num2: Int,
    val operator: String, // "+", "-", "×"
    val correctAnswer: Int,
    val options: List<Int>
)
