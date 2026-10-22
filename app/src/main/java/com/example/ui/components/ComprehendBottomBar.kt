package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun ComprehendBottomBar(
    isBengali: Boolean,
    activeTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        windowInsets = WindowInsets.navigationBars,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = NavigationBarDefaults.Elevation
    ) {
        // Tab 0: Home
        NavigationBarItem(
            selected = activeTab == 0,
            onClick = { onTabSelected(0) },
            icon = {
                Icon(
                    imageVector = if (activeTab == 0) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text(text = if (isBengali) "হোম" else "Home")
            },
            modifier = Modifier.testTag("nav_item_home")
        )

        // Tab 1: Job Prep
        NavigationBarItem(
            selected = activeTab == 1,
            onClick = { onTabSelected(1) },
            icon = {
                Icon(
                    imageVector = if (activeTab == 1) Icons.Filled.School else Icons.Outlined.School,
                    contentDescription = "Job Prep"
                )
            },
            label = {
                Text(text = if (isBengali) "প্রস্তুতি" else "BCS Prep")
            },
            modifier = Modifier.testTag("nav_item_prep")
        )

        // Tab 2: Learning
        NavigationBarItem(
            selected = activeTab == 2,
            onClick = { onTabSelected(2) },
            icon = {
                Icon(
                    imageVector = if (activeTab == 2) Icons.Filled.Translate else Icons.Outlined.Translate,
                    contentDescription = "Learning"
                )
            },
            label = {
                Text(text = if (isBengali) "শেখা" else "Learning")
            },
            modifier = Modifier.testTag("nav_item_learning")
        )

        // Tab 3: Tools & Calculator
        NavigationBarItem(
            selected = activeTab == 3,
            onClick = { onTabSelected(3) },
            icon = {
                Icon(
                    imageVector = if (activeTab == 3) Icons.Filled.Calculate else Icons.Outlined.Calculate,
                    contentDescription = "Tools"
                )
            },
            label = {
                Text(text = if (isBengali) "টুলস" else "Tools")
            },
            modifier = Modifier.testTag("nav_item_tools")
        )
    }
}
