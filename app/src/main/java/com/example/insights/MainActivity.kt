package com.example.insights

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
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
            when (currentRoute) {
                "Home" -> HomeTopAppBar { showBottomSheet = true }
                "Insights" -> InsightsTopAppBar(navController)
                "Investments" -> InvestmentsTopAppBar(navController)
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("Insights") },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun InvestmentsTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("Investments") },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(onSettingsClick: () -> Unit) {
    TopAppBar(
        title = {
            // Center the logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo), // Use the JP Morgan logo here
                    contentDescription = "JP Morgan logo",
                    modifier = Modifier.size(96.dp, 32.dp)
                )
            }
        },
        navigationIcon = {
            // Add a transparent IconButton to balance the space
            IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
                Spacer(modifier = Modifier.size(24.dp)) // Invisible content
            }
        },
        actions = {
            IconButton(onClick = { onSettingsClick() }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black
                )
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
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
                selected = currentRoute == item.title,
                onClick = {
                    navController.navigate(item.title) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Blue, // Change the color when selected
                    selectedTextColor = Color.Blue,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

data class NavigationItem(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "Home", modifier = modifier) {
        composable("Home") { HomeScreen() }
        composable("Insights") { InsightsScreen() }
        composable("Investments") { InvestmentsScreen() }
    }
}
@OptIn(ExperimentalMaterial3Api::class)

data class CarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    @StringRes val contentDescriptionResId: Int
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val carouselItems = listOf(
        CarouselItem(0, R.drawable.skyline, R.string.carousel_image_1_description),
        CarouselItem(1, R.drawable.skyline, R.string.carousel_image_2_description),
        CarouselItem(2, R.drawable.skyline, R.string.carousel_image_3_description),
        CarouselItem(3, R.drawable.skyline, R.string.carousel_image_4_description),
        CarouselItem(4, R.drawable.skyline, R.string.carousel_image_5_description)
    )

    val carouselState = rememberCarouselState { carouselItems.count() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Adjusted to accommodate the carousel at the top
    ) {
        HorizontalMultiBrowseCarousel(
            state = carouselState,
            modifier = Modifier
                .width(412.dp)
                .height(221.dp),
            preferredItemWidth = 186.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { i ->
            val item = carouselItems[i]
            Image(
                modifier = Modifier
                    .height(205.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .fillMaxWidth(),
                painter = painterResource(id = item.imageResId),
                contentDescription = stringResource(item.contentDescriptionResId),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Home Screen", style = MaterialTheme.typography.titleLarge)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
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
@OptIn(ExperimentalMaterial3Api::class)
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
@OptIn(ExperimentalMaterial3Api::class)
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