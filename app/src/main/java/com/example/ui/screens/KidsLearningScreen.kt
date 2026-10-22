package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import com.example.data.SampleDataProvider
import com.example.model.AlphabetType
import com.example.viewmodel.ComprehendViewModel
import com.example.viewmodel.UiState

@Composable
fun KidsLearningScreen(
    state: UiState,
    viewModel: ComprehendViewModel,
    modifier: Modifier = Modifier
) {
    val isBn = state.isBengali

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Star rewards banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isBn) "ছোটদের পাঠশালা (NCTB)" else "Kids Primary Learning",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isBn) "${PrayerTimeCalculator.convertToBengaliDigits(state.kidsStars)} টি স্টার" else "${state.kidsStars} Stars",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }

        // Category Selector
        ScrollableTabRow(
            selectedTabIndex = state.activeKidsCategory.ordinal,
            containerColor = Color.Transparent,
            edgePadding = 0.dp,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            AlphabetType.values().forEach { cat ->
                Tab(
                    selected = state.activeKidsCategory == cat,
                    onClick = { viewModel.setKidsCategory(cat) },
                    text = {
                        Text(
                            text = if (isBn) cat.titleBn else cat.titleEn,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                    },
                    modifier = Modifier.testTag("tab_kids_${cat.name}")
                )
            }
        }

        when (state.activeKidsCategory) {
            AlphabetType.VOWELS -> AlphabetCardView(
                items = SampleDataProvider.vowelsList,
                currentIndex = state.currentKidsIndex,
                onNext = { viewModel.nextKidsItem(SampleDataProvider.vowelsList.size) },
                onPrev = { viewModel.prevKidsItem(SampleDataProvider.vowelsList.size) },
                isBn = isBn
            )
            AlphabetType.CONSONANTS -> AlphabetCardView(
                items = SampleDataProvider.consonantsList,
                currentIndex = state.currentKidsIndex,
                onNext = { viewModel.nextKidsItem(SampleDataProvider.consonantsList.size) },
                onPrev = { viewModel.prevKidsItem(SampleDataProvider.consonantsList.size) },
                isBn = isBn
            )
            AlphabetType.NUMBERS -> AlphabetCardView(
                items = SampleDataProvider.kidsNumbers,
                currentIndex = state.currentKidsIndex,
                onNext = { viewModel.nextKidsItem(SampleDataProvider.kidsNumbers.size) },
                onPrev = { viewModel.prevKidsItem(SampleDataProvider.kidsNumbers.size) },
                isBn = isBn
            )
            AlphabetType.TABLES -> MultiplicationTableView(isBn)
            AlphabetType.MATH_DRILL -> MathDrillQuizView(state, viewModel, isBn)
        }
    }
}

@Composable
private fun AlphabetCardView(
    items: List<com.example.model.AlphabetItem>,
    currentIndex: Int,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    isBn: Boolean
) {
    val item = items.getOrNull(currentIndex) ?: return

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Large Symbol & Letter
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.size(100.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = item.symbolOrEmoji,
                            fontSize = 48.sp
                        )
                    }
                }

                Text(
                    text = item.letter,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 72.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = "${item.pronunciationBn} (${item.wordBn} - ${item.wordEn})",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "ছড়া: \"${item.exampleSentenceBn}\"",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Text(
                    text = "${currentIndex + 1} / ${items.size}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = onPrev,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isBn) "আগেরটি" else "Previous")
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onNext,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isBn) "পরেরটি" else "Next", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun MultiplicationTableView(isBn: Boolean) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(10) { index ->
            val multiplier = index + 1
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isBn) "৫ এর ঘরের নামতা: ৫ × ${PrayerTimeCalculator.convertToBengaliDigits(multiplier)}"
                        else "Table of 5: 5 × $multiplier",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (isBn) "= ${PrayerTimeCalculator.convertToBengaliDigits(5 * multiplier)}" else "= ${5 * multiplier}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun MathDrillQuizView(
    state: UiState,
    viewModel: ComprehendViewModel,
    isBn: Boolean
) {
    val problem = state.activeMathProblem ?: return

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isBn) "অঙ্কের সঠিক উত্তর বেছে নাও:" else "Choose the correct answer:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (isBn) "${PrayerTimeCalculator.convertToBengaliDigits(problem.num1)} ${problem.operator} ${PrayerTimeCalculator.convertToBengaliDigits(problem.num2)} = ?"
                    else "${problem.num1} ${problem.operator} ${problem.num2} = ?",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 40.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // 4 Options
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            problem.options.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    row.forEach { optionValue ->
                        val isSelected = state.mathQuizSelectedAnswer == optionValue
                        val isCorrect = optionValue == problem.correctAnswer
                        val showResult = state.isMathQuizAnswerCorrect != null

                        val containerColor = when {
                            showResult && isCorrect -> Color(0xFFC8E6C9)
                            showResult && isSelected && !isCorrect -> Color(0xFFFFCDD2)
                            isSelected -> MaterialTheme.colorScheme.primaryContainer
                            else -> MaterialTheme.colorScheme.surface
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    if (state.mathQuizSelectedAnswer == null) {
                                        viewModel.answerMathProblem(optionValue)
                                    }
                                }
                                .testTag("math_option_$optionValue"),
                            shape = RoundedCornerShape(14.dp),
                            color = containerColor,
                            shadowElevation = 1.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = if (isBn) PrayerTimeCalculator.convertToBengaliDigits(optionValue) else "$optionValue",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        if (state.isMathQuizAnswerCorrect != null) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (state.isMathQuizAnswerCorrect == true) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (state.isMathQuizAnswerCorrect == true) "🎉 সাবাশ! তোমার উত্তর সঠিক হয়েছে (+১ স্টার)" else "❌ একটু ভুল হয়েছে, আবার চেষ্টা করো!",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (state.isMathQuizAnswerCorrect == true) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(14.dp)
                )
            }

            Button(
                onClick = { viewModel.generateNewMathProblem() },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isBn) "পরবর্তী অঙ্ক" else "Next Problem", fontWeight = FontWeight.Bold)
            }
        }
    }
}
