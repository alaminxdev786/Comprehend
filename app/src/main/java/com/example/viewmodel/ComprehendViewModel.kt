package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BengaliCalendarEngine
import com.example.data.PrayerTimeCalculator
import com.example.data.SampleDataProvider
import com.example.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

enum class SubScreen {
    NONE,
    BCS_SUBJECT_SELECT,
    BCS_QUIZ,
    BCS_REVIEW,
    ENGLISH_LEARNING,
    CALENDAR_PRAYER,
    CALCULATOR,
    KIDS_SCHOOL,
    QR_TOOLS,
    VOICE_TOOLS
}

data class UiState(
    val isBengali: Boolean = true,
    val isDarkMode: Boolean = false,
    val activeTab: Int = 0, // 0: Home, 1: Job Prep, 2: Learn, 3: Tools
    val currentSubScreen: SubScreen = SubScreen.NONE,
    val searchQuery: String = "",

    // Prayer & Calendar
    val selectedDistrict: BangladeshDistrict = SampleDataProvider.districts[0],
    val prayerTimes: PrayerTimes = PrayerTimeCalculator.calculateTimesForDate(SampleDataProvider.districts[0]),
    val bengaliDate: BengaliDate = BengaliCalendarEngine.getBengaliDate(),
    val hijriDate: HijriDate = BengaliCalendarEngine.getEstimatedHijriDate(),

    // BCS Quiz
    val quizState: QuizState = QuizState(subject = null),
    val lastExamResult: ExamResultRecord? = null,

    // English
    val activeVocabIndex: Int = 0,
    val isVocabFlipped: Boolean = false,
    val activeEnglishTab: Int = 0, // 0: Vocab, 1: Conversation

    // Calculator
    val calcMode: CalculatorMode = CalculatorMode.STANDARD,
    val calcDisplay: String = "0",
    val calcFormula: String = "",
    val calcHistory: List<String> = emptyList(),
    val profitLossResult: ProfitLossResult? = null,
    val landUnitResult: LandUnitResult? = null,

    // Kids School
    val activeKidsCategory: AlphabetType = AlphabetType.VOWELS,
    val currentKidsIndex: Int = 0,
    val kidsStars: Int = 0,
    val activeMathProblem: KidsMathProblem? = null,
    val mathQuizSelectedAnswer: Int? = null,
    val isMathQuizAnswerCorrect: Boolean? = null,

    // QR & Voice tools
    val qrInputText: String = "https://bangladesh.gov.bd",
    val voiceText: String = "বাংলা আমাদের মাতৃভাষা, বাংলাকে ভালোবাসুন।",
    val isSpeaking: Boolean = false
)

class ComprehendViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        refreshCalendarAndPrayer()
        generateNewMathProblem()
    }

    fun toggleLanguage() {
        _uiState.update { it.copy(isBengali = !it.isBengali) }
    }

    fun toggleTheme() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }

    fun setActiveTab(index: Int) {
        _uiState.update {
            it.copy(
                activeTab = index,
                currentSubScreen = SubScreen.NONE,
                searchQuery = ""
            )
        }
    }

    fun navigateToSubScreen(screen: SubScreen) {
        _uiState.update { it.copy(currentSubScreen = screen) }
    }

    fun navigateBack() {
        _uiState.update {
            if (it.currentSubScreen == SubScreen.BCS_REVIEW) {
                it.copy(currentSubScreen = SubScreen.BCS_SUBJECT_SELECT)
            } else if (it.currentSubScreen != SubScreen.NONE) {
                it.copy(currentSubScreen = SubScreen.NONE)
            } else {
                it
            }
        }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    // --- Prayer & Calendar ---
    fun selectDistrict(district: BangladeshDistrict) {
        val newPrayer = PrayerTimeCalculator.calculateTimesForDate(district)
        _uiState.update {
            it.copy(
                selectedDistrict = district,
                prayerTimes = newPrayer
            )
        }
    }

    fun refreshCalendarAndPrayer() {
        val now = Calendar.getInstance()
        val district = _uiState.value.selectedDistrict
        _uiState.update {
            it.copy(
                prayerTimes = PrayerTimeCalculator.calculateTimesForDate(district, now),
                bengaliDate = BengaliCalendarEngine.getBengaliDate(now),
                hijriDate = BengaliCalendarEngine.getEstimatedHijriDate(now)
            )
        }
    }

    // --- BCS & Job Prep ---
    fun startQuiz(subject: SubjectType?, isMockExam: Boolean = false) {
        timerJob?.cancel()
        val questions = if (subject == null || isMockExam) {
            SampleDataProvider.bcsQuestions.shuffled()
        } else {
            SampleDataProvider.bcsQuestions.filter { it.subject == subject }.shuffled()
        }

        val totalTime = if (isMockExam) questions.size * 60 else questions.size * 90

        _uiState.update {
            it.copy(
                currentSubScreen = SubScreen.BCS_QUIZ,
                quizState = QuizState(
                    subject = subject,
                    isMockExam = isMockExam,
                    questions = questions,
                    currentIndex = 0,
                    userAnswers = emptyMap(),
                    isSubmitted = false,
                    score = 0.0,
                    timeRemainingSeconds = totalTime.coerceAtLeast(60),
                    isTimerActive = true
                )
            )
        }

        startQuizTimer()
    }

    private fun startQuizTimer() {
        timerJob = viewModelScope.launch {
            while (_uiState.value.quizState.timeRemainingSeconds > 0 && !_uiState.value.quizState.isSubmitted) {
                delay(1000)
                _uiState.update { state ->
                    val newTime = state.quizState.timeRemainingSeconds - 1
                    if (newTime <= 0) {
                        state.copy(
                            quizState = state.quizState.copy(
                                timeRemainingSeconds = 0,
                                isTimerActive = false
                            )
                        )
                    } else {
                        state.copy(quizState = state.quizState.copy(timeRemainingSeconds = newTime))
                    }
                }
            }
            if (!_uiState.value.quizState.isSubmitted) {
                submitQuiz()
            }
        }
    }

    fun selectQuizAnswer(questionIndex: Int, optionIndex: Int) {
        if (_uiState.value.quizState.isSubmitted) return
        _uiState.update { state ->
            val updated = state.quizState.userAnswers.toMutableMap()
            updated[questionIndex] = optionIndex
            state.copy(quizState = state.quizState.copy(userAnswers = updated))
        }
    }

    fun nextQuizQuestion() {
        _uiState.update { state ->
            val nextIndex = (state.quizState.currentIndex + 1).coerceAtMost(state.quizState.questions.size - 1)
            state.copy(quizState = state.quizState.copy(currentIndex = nextIndex))
        }
    }

    fun prevQuizQuestion() {
        _uiState.update { state ->
            val prevIndex = (state.quizState.currentIndex - 1).coerceAtLeast(0)
            state.copy(quizState = state.quizState.copy(currentIndex = prevIndex))
        }
    }

    fun toggleBookmark(questionId: Int) {
        _uiState.update { state ->
            val bookmarks = state.quizState.bookmarks.toMutableSet()
            if (bookmarks.contains(questionId)) {
                bookmarks.remove(questionId)
            } else {
                bookmarks.add(questionId)
            }
            state.copy(quizState = state.quizState.copy(bookmarks = bookmarks))
        }
    }

    fun submitQuiz() {
        timerJob?.cancel()
        val current = _uiState.value.quizState
        var correctCount = 0
        var wrongCount = 0

        current.questions.forEachIndexed { index, question ->
            val selected = current.userAnswers[index]
            if (selected != null) {
                if (selected == question.correctIndex) {
                    correctCount++
                } else {
                    wrongCount++
                }
            }
        }

        val skippedCount = current.questions.size - (correctCount + wrongCount)
        // BCS Standard marking: +1 for correct, -0.5 for wrong
        val totalScore = (correctCount * 1.0) - (wrongCount * 0.5)

        val record = ExamResultRecord(
            id = System.currentTimeMillis().toString(),
            subjectTitle = current.subject?.titleBn ?: "বিসিএস পূর্ণাঙ্গ মডেল টেস্ট",
            totalQuestions = current.questions.size,
            correctAnswers = correctCount,
            wrongAnswers = wrongCount,
            skippedAnswers = skippedCount,
            score = totalScore.coerceAtLeast(0.0),
            dateFormatted = "আজ"
        )

        _uiState.update { state ->
            state.copy(
                quizState = state.quizState.copy(
                    isSubmitted = true,
                    isTimerActive = false,
                    score = record.score
                ),
                lastExamResult = record,
                currentSubScreen = SubScreen.BCS_REVIEW
            )
        }
    }

    // --- English Learning ---
    fun setEnglishTab(index: Int) {
        _uiState.update { it.copy(activeEnglishTab = index) }
    }

    fun flipVocabCard() {
        _uiState.update { it.copy(isVocabFlipped = !it.isVocabFlipped) }
    }

    fun nextVocabWord() {
        _uiState.update {
            val next = (it.activeVocabIndex + 1) % SampleDataProvider.vocabWords.size
            it.copy(activeVocabIndex = next, isVocabFlipped = false)
        }
    }

    fun prevVocabWord() {
        _uiState.update {
            val total = SampleDataProvider.vocabWords.size
            val prev = (it.activeVocabIndex - 1 + total) % total
            it.copy(activeVocabIndex = prev, isVocabFlipped = false)
        }
    }

    // --- Calculators ---
    fun setCalculatorMode(mode: CalculatorMode) {
        _uiState.update { it.copy(calcMode = mode) }
    }

    fun onCalculatorDigit(digit: String) {
        _uiState.update { state ->
            val newDisplay = if (state.calcDisplay == "0" && digit != ".") digit else state.calcDisplay + digit
            state.copy(calcDisplay = newDisplay)
        }
    }

    fun onCalculatorOperator(op: String) {
        _uiState.update { state ->
            state.copy(
                calcFormula = "${state.calcDisplay} $op",
                calcDisplay = "0"
            )
        }
    }

    fun onCalculatorClear() {
        _uiState.update {
            it.copy(
                calcDisplay = "0",
                calcFormula = ""
            )
        }
    }

    fun onCalculatorEquals() {
        val state = _uiState.value
        if (state.calcFormula.isEmpty()) return
        val parts = state.calcFormula.split(" ")
        if (parts.size >= 2) {
            val firstNum = parts[0].toDoubleOrNull() ?: 0.0
            val op = parts[1]
            val secondNum = state.calcDisplay.toDoubleOrNull() ?: 0.0

            val result = when (op) {
                "+" -> firstNum + secondNum
                "-" -> firstNum - secondNum
                "×", "*" -> firstNum * secondNum
                "÷", "/" -> if (secondNum != 0.0) firstNum / secondNum else 0.0
                "%" -> (firstNum * secondNum) / 100.0
                else -> secondNum
            }

            val resultStr = if (result % 1.0 == 0.0) result.toLong().toString() else String.format("%.2f", result)
            val historyEntry = "${state.calcFormula} ${state.calcDisplay} = $resultStr"

            _uiState.update {
                it.copy(
                    calcDisplay = resultStr,
                    calcFormula = "",
                    calcHistory = listOf(historyEntry) + it.calcHistory.take(4)
                )
            }
        }
    }

    fun calculateProfitLoss(costPrice: Double, sellingPrice: Double) {
        val diff = sellingPrice - costPrice
        val isProfit = diff >= 0
        val percentage = if (costPrice > 0) (Math.abs(diff) / costPrice) * 100 else 0.0
        val result = ProfitLossResult(
            costPrice = costPrice,
            sellingPrice = sellingPrice,
            isProfit = isProfit,
            differenceAmount = Math.abs(diff),
            percentage = percentage
        )
        _uiState.update { it.copy(profitLossResult = result) }
    }

    fun calculateLandUnits(decimalAmount: Double) {
        // Standard Bangladesh Land Measurement:
        // 1 Shotok (Decimal) = 435.6 sq ft = 40.46 sq meters
        // 1 Katha = 1.65 Shotok
        // 1 Bigha = 20 Katha = 33 Shotok
        // 1 Acre = 100 Shotok
        val katha = decimalAmount / 1.65
        val bigha = decimalAmount / 33.0
        val acre = decimalAmount / 100.0
        val sqFeet = decimalAmount * 435.6
        val sqMeters = decimalAmount * 40.4686

        val result = LandUnitResult(
            shotokDecimal = decimalAmount,
            katha = katha,
            bigha = bigha,
            acre = acre,
            sqFeet = sqFeet,
            sqMeters = sqMeters
        )
        _uiState.update { it.copy(landUnitResult = result) }
    }

    // --- Kids Learning ---
    fun setKidsCategory(category: AlphabetType) {
        _uiState.update {
            it.copy(
                activeKidsCategory = category,
                currentKidsIndex = 0
            )
        }
        if (category == AlphabetType.MATH_DRILL) {
            generateNewMathProblem()
        }
    }

    fun nextKidsItem(maxItems: Int) {
        _uiState.update {
            val next = (it.currentKidsIndex + 1) % maxItems
            it.copy(currentKidsIndex = next)
        }
    }

    fun prevKidsItem(maxItems: Int) {
        _uiState.update {
            val prev = (it.currentKidsIndex - 1 + maxItems) % maxItems
            it.copy(currentKidsIndex = prev)
        }
    }

    fun generateNewMathProblem() {
        val num1 = (1..9).random()
        val num2 = (1..9).random()
        val isAddition = listOf(true, false).random()
        val operator = if (isAddition) "+" else "-"
        val actualN1 = if (!isAddition && num1 < num2) num2 else num1
        val actualN2 = if (!isAddition && num1 < num2) num1 else num2
        val answer = if (isAddition) actualN1 + actualN2 else actualN1 - actualN2

        val wrongOptions = mutableSetOf<Int>()
        while (wrongOptions.size < 3) {
            val candidate = (answer - 3..answer + 4).random()
            if (candidate >= 0 && candidate != answer) {
                wrongOptions.add(candidate)
            }
        }

        val allOptions = (wrongOptions.toList() + answer).shuffled()

        _uiState.update {
            it.copy(
                activeMathProblem = KidsMathProblem(actualN1, actualN2, operator, answer, allOptions),
                mathQuizSelectedAnswer = null,
                isMathQuizAnswerCorrect = null
            )
        }
    }

    fun answerMathProblem(selected: Int) {
        val problem = _uiState.value.activeMathProblem ?: return
        val isCorrect = selected == problem.correctAnswer
        _uiState.update {
            it.copy(
                mathQuizSelectedAnswer = selected,
                isMathQuizAnswerCorrect = isCorrect,
                kidsStars = if (isCorrect) it.kidsStars + 1 else it.kidsStars
            )
        }
    }

    // --- QR & Voice ---
    fun setQrInput(text: String) {
        _uiState.update { it.copy(qrInputText = text) }
    }

    fun setVoiceText(text: String) {
        _uiState.update { it.copy(voiceText = text) }
    }
}
