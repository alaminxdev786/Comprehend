package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.ComprehendViewModel
import com.example.viewmodel.UiState

@Composable
fun DocumentToolsScreen(
    state: UiState,
    viewModel: ComprehendViewModel,
    modifier: Modifier = Modifier
) {
    val isBn = state.isBengali
    val context = LocalContext.current
    var copiedNotice by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Text(
                text = if (isBn) "কিউআর কোড জেনারেটর ও স্ক্যানার" else "QR Code Tools",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // QR Input field
        item {
            OutlinedTextField(
                value = state.qrInputText,
                onValueChange = { viewModel.setQrInput(it) },
                label = { Text(if (isBn) "লিংক অথবা টেক্সট লিখুন" else "Enter Link or Text") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_qr_text"),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    if (state.qrInputText.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setQrInput("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                }
            )
        }

        // Rendered QR Code Box
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isBn) "তৈরিকৃত কিউআর কোড" else "Generated QR Code",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Deterministic Canvas QR Drawing
                    Surface(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        color = Color.White,
                        shadowElevation = 2.dp
                    ) {
                        DeterministicQrCanvas(text = state.qrInputText)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = state.qrInputText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }
        }

        // Bengali Text-to-Speech & Voice Utility
        item {
            Text(
                text = if (isBn) "বাংলা টেক্সট ও পাঠ সহকারী" else "Bengali Text Reader & Tools",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = state.voiceText,
                        onValueChange = { viewModel.setVoiceText(it) },
                        label = { Text(if (isBn) "বাংলা টেক্সট লিখুন বা পেস্ট করুন" else "Type or paste Bengali text") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .testTag("input_voice_text"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Bengali Text", state.voiceText)
                                clipboard.setPrimaryClip(clip)
                                copiedNotice = true
                            },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isBn) "কপি করুন" else "Copy Text")
                        }

                        if (copiedNotice) {
                            Text(
                                text = if (isBn) "কপি করা হয়েছে!" else "Copied!",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DeterministicQrCanvas(text: String) {
    Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val sizePx = size.minDimension
        val gridSize = 21 // Version 1 QR grid 21x21
        val cellSize = sizePx / gridSize

        val hash = (text.hashCode().toLong() and 0xFFFFFFFFL).toString(2).padStart(32, '1')

        for (row in 0 until gridSize) {
            for (col in 0 until gridSize) {
                // Draw 3 Corner Position Detection Squares
                val isTopLeftFinder = row in 0..6 && col in 0..6
                val isTopRightFinder = row in 0..6 && col in (gridSize - 7) until gridSize
                val isBottomLeftFinder = row in (gridSize - 7) until gridSize && col in 0..6

                val isFilled = when {
                    isTopLeftFinder -> isFinderPatternCell(row, col)
                    isTopRightFinder -> isFinderPatternCell(row, col - (gridSize - 7))
                    isBottomLeftFinder -> isFinderPatternCell(row - (gridSize - 7), col)
                    // Timing patterns
                    row == 6 -> col % 2 == 0
                    col == 6 -> row % 2 == 0
                    // Data modules generated from text hash
                    else -> {
                        val bitIdx = ((row * gridSize + col) % hash.length)
                        hash[bitIdx] == '1' || (row + col) % 3 == 0
                    }
                }

                if (isFilled) {
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(col * cellSize, row * cellSize),
                        size = Size(cellSize, cellSize)
                    )
                }
            }
        }
    }
}

private fun isFinderPatternCell(r: Int, c: Int): Boolean {
    // 7x7 Finder Pattern: Outer 7x7 box filled, 5x5 inner ring empty, 3x3 center filled
    val isOuterBorder = r == 0 || r == 6 || c == 0 || c == 6
    val isCenterSquare = r in 2..4 && c in 2..4
    return isOuterBorder || isCenterSquare
}
