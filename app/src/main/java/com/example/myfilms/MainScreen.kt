package com.example.myfilms

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myfilms.data.basicDrawerItems
import com.example.myfilms.ui.navigation.FilmNavHost
import com.example.myfilms.ui.view_models.FilmViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class MainScreen (@StringRes val title: Int) {
    Start(title = R.string.app_name),
    EditStart(R.string.edit_start),
    ListOfFilms(R.string.list_of_films),
    Details(R.string.details),
    Create(R.string.create),
    Edit(R.string.edit)
}

private fun getScreenName(name: String): String {
    when (name) {
        "Edit Main" -> return MainScreen.EditStart.name
        "List" -> return MainScreen.ListOfFilms.name
        else -> return MainScreen.Start.name
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    scope: CoroutineScope,
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = { Text("Film Application") }, //TODO zmienić to gówno,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmApp(
    viewModel: FilmViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                basicDrawerItems().forEachIndexed { index, navDrawerItem ->
                    NavigationDrawerItem(
                        label = {
                                Text(text = navDrawerItem.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            navController.navigate(getScreenName(navDrawerItem.title))
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    navDrawerItem.selectedIcon
                                } else navDrawerItem.unselectedIcon,
                                contentDescription = navDrawerItem.title
                            )
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                MainAppBar(
                    scope = scope,
                    drawerState = drawerState
                )
            }
        ) { innerPadding ->
            FilmNavHost(
                navController = navController,
                viewModel = viewModel,
                innerPadding = innerPadding
            )
        }
    }

}

@Preview
@Composable
fun FilmAppPreview() {
    FilmApp()
}