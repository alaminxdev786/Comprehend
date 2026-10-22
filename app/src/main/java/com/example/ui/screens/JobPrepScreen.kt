package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PrayerTimeCalculator
import com.example.model.Question
import com.example.model.SubjectType
import com.example.viewmodel.ComprehendViewModel
import com.example.viewmodel.SubScreen
import com.example.viewmodel.UiState

@Composable
fun JobPrepScreen(
    state: UiState,
    viewModel: ComprehendViewModel,
    modifier: Modifier = Modifier
) {
    when (state.currentSubScreen) {
        SubScreen.BCS_QUIZ -> QuizView(state, viewModel, modifier)
        SubScreen.BCS_REVIEW -> QuizReviewView(state, viewModel, modifier)
        else -> SubjectSelectionView(state, viewModel, modifier)
    }
}

@Composable
private fun SubjectSelectionView(
    state: UiState,
    viewModel: ComprehendViewModel,
    modifier: Modifier = Modifier
) {
    val isBn = state.isBengali

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Mock exam banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { viewModel.startQuiz(null, isMockExam = true) }
                    .testTag("btn_start_mock_exam"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = if (isBn) "লাইভ মডেল টেস্ট" else "Live Mock Test",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (isBn) "বিসিএস প্রিলিমিনারি পূর্ণাঙ্গ পরীক্ষা" else "Full BCS Preliminary Mock Test",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (isBn) "সকল বিষয়ের সমন্বয়ে যাচাইকৃত প্রশ্নব্যাংক • নেগেটিভ মার্কিং (-০.৫) প্রযোজ্য"
                        else "Comprehensive question bank with negative marking (-0.5)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { viewModel.startQuiz(null, isMockExam = true) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (isBn) "পরীক্ষা শুরু করুন" else "Start Exam Now",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Section title
        item {
            Text(
                text = if (isBn) "বিষয়ভিত্তিক প্রস্তুতি" else "Subject-wise Preparation",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Subject items
        itemsIndexed(SubjectType.values()) { _, subject ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { viewModel.startQuiz(subject, isMockExam = false) }
                    .testTag("btn_subject_${subject.name}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = when (subject) {
                                    SubjectType.BANGLA -> Icons.Default.MenuBook
                                    SubjectType.ENGLISH -> Icons.Default.Language
                                    SubjectType.BANGLADESH -> Icons.Default.Flag
                                    SubjectType.INTERNATIONAL -> Icons.Default.Public
                                    SubjectType.MATH -> Icons.Default.Calculate
                                    SubjectType.SCIENCE -> Icons.Default.Science
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isBn) subject.titleBn else subject.titleEn,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isBn) "বিগত বছরের বিসিএস ও পিএসসি প্রশ্নাবলী" else "Previous BCS & PSC Exam Questions",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizView(
    state: UiState,
    viewModel: ComprehendViewModel,
    modifier: Modifier = Modifier
) {
    val quiz = state.quizState
    val isBn = state.isBengali
    val currentQuestion = quiz.questions.getOrNull(quiz.currentIndex)

    if (currentQuestion == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val selectedOption = quiz.userAnswers[quiz.currentIndex]
    val isBookmarked = quiz.bookmarks.contains(currentQuestion.id)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top status row: Timer & Question Counter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Timer pill
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (quiz.timeRemainingSeconds <= 30) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if (quiz.timeRemainingSeconds <= 30) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    val mins = quiz.timeRemainingSeconds / 60
                    val secs = quiz.timeRemainingSeconds % 60
                    Text(
                        text = if (isBn) "${PrayerTimeCalculator.convertToBengaliDigits(mins)}:${String.format("%02d", secs)}"
                        else String.format("%02d:%02d", mins, secs),
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (quiz.timeRemainingSeconds <= 30) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Question counter
            Text(
                text = if (isBn) "প্রশ্ন ${PrayerTimeCalculator.convertToBengaliDigits(quiz.currentIndex + 1)} / ${PrayerTimeCalculator.convertToBengaliDigits(quiz.questions.size)}"
                else "Question ${quiz.currentIndex + 1} / ${quiz.questions.size}",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            // Bookmark button
            IconButton(
                onClick = { viewModel.toggleBookmark(currentQuestion.id) },
                modifier = Modifier.testTag("btn_bookmark")
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Progress bar
        val progress = (quiz.currentIndex + 1).toFloat() / quiz.questions.size.toFloat()
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Question card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = currentQuestion.sourceOrYear,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (isBn) currentQuestion.questionBn else currentQuestion.questionEn,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        lineHeight = 24.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Options list
        val options = if (isBn) currentQuestion.optionsBn else currentQuestion.optionsEn
        val optionLabels = if (isBn) listOf("ক", "খ", "গ", "ঘ") else listOf("A", "B", "C", "D")

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            options.forEachIndexed { index, optionText ->
                val isSelected = selectedOption == index
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { viewModel.selectQuizAnswer(quiz.currentIndex, index) }
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .testTag("option_$index"),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                        else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = optionLabels[index],
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = optionText,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Bottom Navigation Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { viewModel.prevQuizQuestion() },
                enabled = quiz.currentIndex > 0,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = if (isBn) "পূর্ববর্তী" else "Previous")
            }

            Spacer(modifier = Modifier.width(12.dp))

            if (quiz.currentIndex == quiz.questions.size - 1) {
                Button(
                    onClick = { viewModel.submitQuiz() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_submit_quiz")
                ) {
                    Text(text = if (isBn) "জমা দিন" else "Submit", fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = { viewModel.nextQuizQuestion() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (isBn) "পরবর্তী" else "Next")
                }
            }
        }
    }
}

@Composable
private fun QuizReviewView(
    state: UiState,
    viewModel: ComprehendViewModel,
    modifier: Modifier = Modifier
) {
    val isBn = state.isBengali
    val result = state.lastExamResult
    val quiz = state.quizState

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Score Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isBn) "পরীক্ষার ফলাফল" else "Exam Results",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isBn) "${String.format("%.1f", result?.score ?: 0.0)} / ${PrayerTimeCalculator.convertToBengaliDigits(result?.totalQuestions ?: 0)}"
                        else "${String.format("%.1f", result?.score ?: 0.0)} / ${result?.totalQuestions ?: 0}",
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = if (isBn) "প্রাপ্ত নম্বর (নেগেটিভ মার্কিং সহ)" else "Final Score (with -0.5 penalty)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ScoreStat(
                            label = if (isBn) "সঠিক" else "Correct",
                            count = "${result?.correctAnswers ?: 0}",
                            color = Color(0xFF2E7D32)
                        )
                        ScoreStat(
                            label = if (isBn) "ভুল (-০.৫)" else "Wrong",
                            count = "${result?.wrongAnswers ?: 0}",
                            color = MaterialTheme.colorScheme.error
                        )
                        ScoreStat(
                            label = if (isBn) "উত্তর দেননি" else "Skipped",
                            count = "${result?.skippedAnswers ?: 0}",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Action buttons
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.navigateToSubScreen(SubScreen.BCS_SUBJECT_SELECT) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = if (isBn) "বিষয় পরিবর্তন" else "Change Subject")
                }

                Button(
                    onClick = { viewModel.startQuiz(quiz.subject, isMockExam = quiz.isMockExam) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = if (isBn) "আবার পরীক্ষা দিন" else "Retake Exam")
                }
            }
        }

        item {
            Text(
                text = if (isBn) "বিস্তারিত পর্যালোচনা ও ব্যাখ্যা" else "Detailed Review & Explanations",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Question by question review
        itemsIndexed(quiz.questions) { index, question ->
            val userAnswer = quiz.userAnswers[index]
            val isCorrect = userAnswer == question.correctIndex
            val isSkipped = userAnswer == null

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isBn) "প্রশ্ন ${PrayerTimeCalculator.convertToBengaliDigits(index + 1)}" else "Question ${index + 1}",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = when {
                                isCorrect -> Color(0xFFE8F5E9)
                                isSkipped -> MaterialTheme.colorScheme.surfaceVariant
                                else -> MaterialTheme.colorScheme.errorContainer
                            }
                        ) {
                            Text(
                                text = when {
                                    isCorrect -> if (isBn) "সঠিক (+১)" else "Correct (+1)"
                                    isSkipped -> if (isBn) "অনুত্তরিত (০)" else "Skipped (0)"
                                    else -> if (isBn) "ভুল (-০.৫)" else "Wrong (-0.5)"
                                },
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = when {
                                    isCorrect -> Color(0xFF2E7D32)
                                    isSkipped -> MaterialTheme.colorScheme.onSurfaceVariant
                                    else -> MaterialTheme.colorScheme.onErrorContainer
                                },
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isBn) question.questionBn else question.questionEn,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    val options = if (isBn) question.optionsBn else question.optionsEn
                    val optionLabels = if (isBn) listOf("ক", "খ", "গ", "ঘ") else listOf("A", "B", "C", "D")

                    options.forEachIndexed { optIdx, optText ->
                        val isUserPick = userAnswer == optIdx
                        val isRightAnswer = optIdx == question.correctIndex

                        val bg = when {
                            isRightAnswer -> Color(0xFFE8F5E9)
                            isUserPick -> Color(0xFFFFEBEE)
                            else -> Color.Transparent
                        }

                        val textColor = when {
                            isRightAnswer -> Color(0xFF2E7D32)
                            isUserPick -> MaterialTheme.colorScheme.error
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(bg, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${optionLabels[optIdx]}. $optText",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = if (isRightAnswer || isUserPick) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = textColor
                            )
                            if (isRightAnswer) {
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(16.dp)
                                )
                            } else if (isUserPick) {
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Explanation Box
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = if (isBn) "ব্যাখ্যা (${question.sourceOrYear}):" else "Explanation (${question.sourceOrYear}):",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isBn) question.explanationBn else question.explanationEn,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScoreStat(label: String, count: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
    }
}
