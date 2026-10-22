package com.example.model

data class VocabWord(
    val id: Int,
    val word: String,
    val pronunciationBn: String,
    val partOfSpeech: String,
    val meaningBn: String,
    val definitionEn: String,
    val exampleSentenceEn: String,
    val exampleSentenceBn: String,
    val level: VocabLevel
)

enum class VocabLevel(val labelBn: String, val labelEn: String) {
    ESSENTIAL("দৈনন্দিন প্রয়োজনীয়", "Essential Daily"),
    INTERMEDIATE("মাঝারি পর্যায়", "Intermediate"),
    ADVANCED_BCS("উন্নত / বিসিএস", "Advanced & BCS")
}

data class ConversationPhrase(
    val id: Int,
    val category: String, // e.g. "কর্মক্ষেত্র (Workplace)", "রেস্তোরাঁ (Restaurant)", "সাক্ষাৎকার (Interview)"
    val englishText: String,
    val banglaMeaning: String,
    val pronunciationBn: String
)
