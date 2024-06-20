package com.example.myfilms.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector

data class NavDrawerItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

fun basicDrawerItems(): List<NavDrawerItem> {
    return listOf(
        NavDrawerItem(
            title = "Main Screen",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavDrawerItem(
            title = "Edit Main",
            selectedIcon = Icons.Filled.Edit,
            unselectedIcon = Icons.Outlined.Edit
        ),
        NavDrawerItem(
            title = "List",
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List
        )

    )
}
