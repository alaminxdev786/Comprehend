package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FeatureCard
import com.example.viewmodel.ComprehendViewModel
import com.example.viewmodel.SubScreen
import com.example.viewmodel.UiState

@Composable
fun HomeScreen(
    state: UiState,
    viewModel: ComprehendViewModel,
    modifier: Modifier = Modifier
) {
    val isBn = state.isBengali

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header: Synchronized Date & Prayer Bar
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { viewModel.navigateToSubScreen(SubScreen.CALENDAR_PRAYER) }
                    .testTag("hero_date_prayer_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                                )
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "${state.bengaliDate.monthNameBn} ${state.bengaliDate.dayNumber}, ${state.bengaliDate.yearBn} বঙ্গাব্দ (${state.bengaliDate.seasonBn})",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                            Text(
                                text = "${state.hijriDate.dayNumber} ${state.hijriDate.monthNameBn} ${state.hijriDate.yearBn} হিজরি",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = if (isBn) "পরবর্তী সালাত: ${state.prayerTimes.nextPrayerNameBn}" else "Next Prayer: ${state.prayerTimes.nextPrayerNameEn}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiaryContainer,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isBn) "বাকি আছে ${state.prayerTimes.timeRemainingFormatted} (${state.selectedDistrict.nameBn})"
                                else "${state.prayerTimes.timeRemainingFormatted} remaining (${state.selectedDistrict.nameEn})",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_search_bar"),
                placeholder = {
                    Text(text = if (isBn) "বিসিএস প্রশ্ন, ইংরেজি শব্দ বা ক্যালকুলেটর খুঁজুন..." else "Search BCS questions, English, or tools...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (state.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setSearchQuery("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }

        // Category: চাকরির প্রস্তুতি ও ক্যারিয়ার
        item {
            Text(
                text = if (isBn) "চাকরির প্রস্তুতি ও পড়াশোনা" else "Career & Exam Prep",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            FeatureCard(
                title = if (isBn) "বিসিএস ও সরকারি চাকরি প্রস্তুতি" else "BCS & Govt Job Exam Prep",
                subtitle = if (isBn) "বিষয়ভিত্তিক প্রশ্নব্যাংক, ব্যাখ্যা ও নেগেটিভ মার্কিংযুক্ত মডেল টেস্ট" else "Subject-wise verified MCQs & timed mock tests",
                icon = Icons.Default.MenuBook,
                tag = "৪৪-৪৫তম বিসিএস",
                testTag = "card_bcs_prep",
                onClick = { viewModel.navigateToSubScreen(SubScreen.BCS_SUBJECT_SELECT) }
            )
        }

        // Category: ভাষা ও দক্ষতা
        item {
            Text(
                text = if (isBn) "ভাষা ও দক্ষতা উন্নয়ন" else "Language & Skills",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            FeatureCard(
                title = if (isBn) "স্পোকেন ইংলিশ ও শব্দভাণ্ডার" else "Spoken English & Vocabulary",
                subtitle = if (isBn) "দৈনন্দিন প্রয়োজনীয় শব্দ, উচ্চারণ, বাংলা অর্থ ও বাস্তব কথোপকথন" else "Daily vocabulary flashcards, pronunciation & dialogues",
                icon = Icons.Default.Translate,
                tag = "ফ্ল্যাশকার্ড",
                testTag = "card_english_learning",
                onClick = { viewModel.navigateToSubScreen(SubScreen.ENGLISH_LEARNING) }
            )
        }

        item {
            FeatureCard(
                title = if (isBn) "ছোটদের পাঠশালা (প্রাক-প্রাথমিক থেকে ৫ম)" else "Kids Primary School (NCTB)",
                subtitle = if (isBn) "বাংলা স্বরবর্ণ, ব্যঞ্জনবর্ণ, ১-১০ গণনা, নামতা ও মজার অঙ্কের খেলা" else "Bangla alphabet, numbers, multiplication & math drills",
                icon = Icons.Default.ChildCare,
                tag = "NCTB সিলেবাস",
                testTag = "card_kids_school",
                onClick = { viewModel.navigateToSubScreen(SubScreen.KIDS_SCHOOL) }
            )
        }

        // Category: প্রতিদিনের হিসাব ও টুলস
        item {
            Text(
                text = if (isBn) "প্রতিদিনের হিসাব ও জীবনযাত্রা" else "Everyday Utilities & Lifestyle",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            FeatureCard(
                title = if (isBn) "নামাজের সময়সূচি ও বাংলা দিনপঞ্জি" else "Prayer Times & Bengali Calendar",
                subtitle = if (isBn) "৬৪ জেলার সেহরি, ইফতার ও ৫ ওয়াক্ত নামাজের সঠিক হিসাব" else "District prayer times, Sehri, Iftar & holidays",
                icon = Icons.Default.Mosque,
                tag = "৬৪ জেলা",
                testTag = "card_prayer_calendar",
                onClick = { viewModel.navigateToSubScreen(SubScreen.CALENDAR_PRAYER) }
            )
        }

        item {
            FeatureCard(
                title = if (isBn) "ক্যালকুলেটর, লাভ-ক্ষতি ও জমির হিসাব" else "Multi-mode Calculator & Land Converter",
                subtitle = if (isBn) "শতকরা, ব্যবসার লাভ-ক্ষতি এবং শতক, কাঠা ও বিঘা রূপান্তর" else "Financial profit/loss & BD land unit conversion",
                icon = Icons.Default.Calculate,
                testTag = "card_calculator",
                onClick = { viewModel.navigateToSubScreen(SubScreen.CALCULATOR) }
            )
        }

        item {
            FeatureCard(
                title = if (isBn) "কিউআর কোড ও বাংলা ভয়েস টুলস" else "QR Code & Bengali Voice Tools",
                subtitle = if (isBn) "কিউআর কোড তৈরি, টেক্সট স্ক্যান ও বাংলা পাঠ সহকারী" else "Generate QR codes & listen to Bengali text",
                icon = Icons.Default.QrCode2,
                testTag = "card_doc_tools",
                onClick = { viewModel.navigateToSubScreen(SubScreen.QR_TOOLS) }
            )
        }

        // Official verification note footer
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (isBn) "সকল বিসিএস প্রশ্ন ও শিক্ষামূলক তথ্যসমূহ বাংলাদেশ সরকারি কর্ম কমিশন (BPSC) ও জাতীয় শিক্ষাক্রম বোর্ড (NCTB) অনুমোদিত সূত্রের সাথে সামঞ্জস্যপূর্ণ।"
                        else "All exam questions and educational content are cross-referenced with BPSC and NCTB curriculum standards.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
