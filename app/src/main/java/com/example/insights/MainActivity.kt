package com.example.insights

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.insights.R
import com.example.insights.ui.theme.InsightsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InsightsTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(false)
    var showBottomSheet by remember { mutableStateOf(false) }

    // This block handles the state of the bottom sheet
    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    Scaffold(
        topBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute == "Home") {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_logo),
                                contentDescription = "JP Morgan logo",
                                modifier = Modifier.size(96.dp, 32.dp)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
                            Spacer(modifier = Modifier.size(24.dp)) // Invisible content
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                showBottomSheet = true // Open the bottom sheet
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar(navController)
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavigationHost(navController)
            }
        }
    )

    // BottomSheet content
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            SettingsBottomSheetContent(onDismiss = { showBottomSheet = false })
        }
    }
}

@Composable
fun NavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem("Home", Icons.Filled.Home),
        NavigationItem("Insights", Icons.Filled.Home),
        NavigationItem("Investments", Icons.Filled.Home)
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = navController.currentBackStackEntry?.destination?.route == item.title,
                onClick = {
                    navController.navigate(item.title) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class NavigationItem(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "Home", modifier = modifier) {
        composable("Home") { HomeScreen() }
        composable("Insights") { InsightsScreen() }
        composable("Investments") { InvestmentsScreen() }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home Screen", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun InsightsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Insights Screen", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun InvestmentsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Investments Screen", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun SettingsBottomSheetContent(onDismiss: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Region Settings",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(8.dp)
                .clickable { onDismiss() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Disclaimers",
            modifier = Modifier
                .padding(8.dp)
                .clickable { onDismiss() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Privacy Policy",
            modifier = Modifier
                .padding(8.dp)
                .clickable { onDismiss() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Support",
            modifier = Modifier
                .padding(8.dp)
                .clickable { onDismiss() }
        )
    }
}
