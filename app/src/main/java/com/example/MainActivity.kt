package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.ComprehendBottomBar
import com.example.ui.components.ComprehendTopBar
import com.example.ui.screens.*
import com.example.ui.theme.ComprehendTheme
import com.example.viewmodel.ComprehendViewModel
import com.example.viewmodel.SubScreen

class MainActivity : ComponentActivity() {

    private val viewModel: ComprehendViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            // System Back button handling
            BackHandler(enabled = state.currentSubScreen != SubScreen.NONE) {
                viewModel.navigateBack()
            }

            ComprehendTheme(darkTheme = state.isDarkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        ComprehendTopBar(
                            isBengali = state.isBengali,
                            isDarkMode = state.isDarkMode,
                            currentSubScreen = state.currentSubScreen,
                            onToggleLanguage = { viewModel.toggleLanguage() },
                            onToggleTheme = { viewModel.toggleTheme() },
                            onNavigateBack = { viewModel.navigateBack() }
                        )
                    },
                    bottomBar = {
                        // Show bottom bar on primary tabs or subject select
                        if (state.currentSubScreen != SubScreen.BCS_QUIZ) {
                            ComprehendBottomBar(
                                isBengali = state.isBengali,
                                activeTab = state.activeTab,
                                onTabSelected = { viewModel.setActiveTab(it) }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        AppContent(state = state, viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
private fun AppContent(
    state: com.example.viewmodel.UiState,
    viewModel: ComprehendViewModel
) {
    if (state.currentSubScreen != SubScreen.NONE) {
        when (state.currentSubScreen) {
            SubScreen.BCS_SUBJECT_SELECT,
            SubScreen.BCS_QUIZ,
            SubScreen.BCS_REVIEW -> JobPrepScreen(state = state, viewModel = viewModel)
            SubScreen.ENGLISH_LEARNING -> EnglishLearningScreen(state = state, viewModel = viewModel)
            SubScreen.CALENDAR_PRAYER -> CalendarPrayerScreen(state = state, viewModel = viewModel)
            SubScreen.CALCULATOR -> CalculatorScreen(state = state, viewModel = viewModel)
            SubScreen.KIDS_SCHOOL -> KidsLearningScreen(state = state, viewModel = viewModel)
            SubScreen.QR_TOOLS,
            SubScreen.VOICE_TOOLS -> DocumentToolsScreen(state = state, viewModel = viewModel)
            SubScreen.NONE -> HomeScreen(state = state, viewModel = viewModel)
        }
    } else {
        when (state.activeTab) {
            0 -> HomeScreen(state = state, viewModel = viewModel)
            1 -> JobPrepScreen(state = state, viewModel = viewModel)
            2 -> EnglishLearningScreen(state = state, viewModel = viewModel)
            3 -> CalendarPrayerScreen(state = state, viewModel = viewModel)
            else -> HomeScreen(state = state, viewModel = viewModel)
        }
    }
}
