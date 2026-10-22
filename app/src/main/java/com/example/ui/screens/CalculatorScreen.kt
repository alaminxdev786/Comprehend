package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CalculatorMode
import com.example.viewmodel.ComprehendViewModel
import com.example.viewmodel.UiState

@Composable
fun CalculatorScreen(
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
        // Mode Selector Scrollable Tabs
        ScrollableTabRow(
            selectedTabIndex = state.calcMode.ordinal,
            containerColor = Color.Transparent,
            edgePadding = 0.dp,
            modifier = Modifier.padding(top = 10.dp, bottom = 12.dp)
        ) {
            CalculatorMode.values().forEach { mode ->
                Tab(
                    selected = state.calcMode == mode,
                    onClick = { viewModel.setCalculatorMode(mode) },
                    text = {
                        Text(
                            text = if (isBn) mode.titleBn else mode.titleEn,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                    },
                    modifier = Modifier.testTag("tab_calc_${mode.name}")
                )
            }
        }

        when (state.calcMode) {
            CalculatorMode.STANDARD -> StandardCalculatorView(state, viewModel)
            CalculatorMode.PERCENTAGE -> PercentageCalculatorView(isBn)
            CalculatorMode.PROFIT_LOSS -> ProfitLossCalculatorView(state, viewModel, isBn)
            CalculatorMode.LAND_CONVERTER -> LandConverterView(state, viewModel, isBn)
        }
    }
}

@Composable
private fun StandardCalculatorView(state: UiState, viewModel: ComprehendViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        // Display card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                if (state.calcFormula.isNotEmpty()) {
                    Text(
                        text = state.calcFormula,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = state.calcDisplay,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons Grid
        val buttons = listOf(
            listOf("C", "÷", "%", "⌫"),
            listOf("7", "8", "9", "×"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", "00", ".", "=")
        )

        Column(
            modifier = Modifier.weight(0.65f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            buttons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { label ->
                        val isOperator = label in listOf("÷", "×", "-", "+", "%", "=")
                        val isSpecial = label in listOf("C", "⌫")

                        val btnColor = when {
                            label == "=" -> MaterialTheme.colorScheme.primary
                            isOperator -> MaterialTheme.colorScheme.primaryContainer
                            isSpecial -> MaterialTheme.colorScheme.secondaryContainer
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val textColor = when {
                            label == "=" -> Color.White
                            isOperator -> MaterialTheme.colorScheme.onPrimaryContainer
                            isSpecial -> MaterialTheme.colorScheme.onSecondaryContainer
                            else -> MaterialTheme.colorScheme.onSurface
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    when (label) {
                                        "C" -> viewModel.onCalculatorClear()
                                        "⌫" -> {
                                            if (state.calcDisplay.length > 1) {
                                                viewModel.onCalculatorDigit("") // handled
                                            } else {
                                                viewModel.onCalculatorClear()
                                            }
                                        }
                                        "=" -> viewModel.onCalculatorEquals()
                                        "+", "-", "×", "÷", "%" -> viewModel.onCalculatorOperator(label)
                                        else -> viewModel.onCalculatorDigit(label)
                                    }
                                }
                                .testTag("btn_calc_$label"),
                            shape = RoundedCornerShape(14.dp),
                            color = btnColor,
                            shadowElevation = 1.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    ),
                                    color = textColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfitLossCalculatorView(
    state: UiState,
    viewModel: ComprehendViewModel,
    isBn: Boolean
) {
    var costPriceInput by remember { mutableStateOf("1200") }
    var sellingPriceInput by remember { mutableStateOf("1500") }

    LaunchedEffect(costPriceInput, sellingPriceInput) {
        val cp = costPriceInput.toDoubleOrNull() ?: 0.0
        val sp = sellingPriceInput.toDoubleOrNull() ?: 0.0
        viewModel.calculateProfitLoss(cp, sp)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = if (isBn) "ব্যবসায়িক লাভ ও ক্ষতি হিসাব" else "Business Profit & Loss Calculation",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            OutlinedTextField(
                value = costPriceInput,
                onValueChange = { costPriceInput = it },
                label = { Text(if (isBn) "ক্রয়মূল্য (টাকা)" else "Cost Price (Tk)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_cost_price"),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
        }

        item {
            OutlinedTextField(
                value = sellingPriceInput,
                onValueChange = { sellingPriceInput = it },
                label = { Text(if (isBn) "বিক্রয়মূল্য (টাকা)" else "Selling Price (Tk)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_selling_price"),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
        }

        state.profitLossResult?.let { result ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (result.isProfit) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = if (result.isProfit) (if (isBn) "মোট লাভ হয়েছে" else "Net Profit") else (if (isBn) "মোট ক্ষতি হয়েছে" else "Net Loss"),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (result.isProfit) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = if (isBn) "টাকার পরিমাণ" else "Amount Difference",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Black.copy(alpha = 0.6f)
                                )
                                Text(
                                    text = "${String.format("%.2f", result.differenceAmount)} ৳",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    color = if (result.isProfit) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = if (isBn) "শতকরা হার (%)" else "Percentage (%)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Black.copy(alpha = 0.6f)
                                )
                                Text(
                                    text = "${String.format("%.2f", result.percentage)} %",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    color = if (result.isProfit) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LandConverterView(
    state: UiState,
    viewModel: ComprehendViewModel,
    isBn: Boolean
) {
    var decimalInput by remember { mutableStateOf("10") }

    LaunchedEffect(decimalInput) {
        val amount = decimalInput.toDoubleOrNull() ?: 0.0
        viewModel.calculateLandUnits(amount)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = if (isBn) "বাংলাদেশের প্রচলিত ভূমির পরিমাপ ও রূপান্তর" else "Bangladesh Land Unit Converter",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            OutlinedTextField(
                value = decimalInput,
                onValueChange = { decimalInput = it },
                label = { Text(if (isBn) "জমির পরিমাণ (শতক / ডেসিমাল)" else "Land Area (Shotok / Decimal)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_land_shotok"),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
        }

        state.landUnitResult?.let { land ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        LandRow(label = if (isBn) "কাঠা (১ কাঠা = ১.৬৫ শতক)" else "Katha (1 Katha = 1.65 Shotok)", value = "${String.format("%.3f", land.katha)} কাঠা")
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                        LandRow(label = if (isBn) "বিঘা (১ বিঘা = ২০ কাঠা = ৩৩ শতক)" else "Bigha (1 Bigha = 20 Katha)", value = "${String.format("%.3f", land.bigha)} বিঘা")
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                        LandRow(label = if (isBn) "একর (১ একর = ১০০ শতক)" else "Acre (1 Acre = 100 Shotok)", value = "${String.format("%.3f", land.acre)} একর")
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                        LandRow(label = if (isBn) "বর্গফুট (১ শতক = ৪৩৫.৬ বর্গফুট)" else "Square Feet (435.6 sq ft/shotok)", value = "${String.format("%.1f", land.sqFeet)} sq ft")
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                        LandRow(label = if (isBn) "বর্গমিটার (১ শতক = ৪০.৪৬ বর্গমিটার)" else "Square Meters (40.46 sq m/shotok)", value = "${String.format("%.2f", land.sqMeters)} sq m")
                    }
                }
            }
        }
    }
}

@Composable
private fun PercentageCalculatorView(isBn: Boolean) {
    var totalAmount by remember { mutableStateOf("5000") }
    var percentageRate by remember { mutableStateOf("15") }

    val total = totalAmount.toDoubleOrNull() ?: 0.0
    val rate = percentageRate.toDoubleOrNull() ?: 0.0
    val percentValue = (total * rate) / 100.0
    val discountedTotal = total - percentValue

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(
            text = if (isBn) "শতকরা ও ডিসকাউন্ট হিসাব" else "Percentage & Discount Calculator",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = totalAmount,
            onValueChange = { totalAmount = it },
            label = { Text(if (isBn) "মূল টাকা / পরিমাণ" else "Total Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = percentageRate,
            onValueChange = { percentageRate = it },
            label = { Text(if (isBn) "শতকরা হার (%)" else "Percentage (%)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "${rate}% এর মান: ${String.format("%.2f", percentValue)} ৳",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (isBn) "ডিসকাউন্ট পরবর্তী মূল্য: ${String.format("%.2f", discountedTotal)} ৳"
                    else "Amount after discount: ${String.format("%.2f", discountedTotal)} Tk",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )
            }
        }
    }
}

@Composable
private fun LandRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
