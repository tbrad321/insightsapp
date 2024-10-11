package com.example.insights

import androidx.compose.material3.*
import android.content.Intent
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
    var showProductBottomSheet by remember { mutableStateOf(false) }
    var showSettingsBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            when (currentRoute) {
                "Home" -> HomeTopAppBar { showSettingsBottomSheet = true }
                "Insights" -> InsightsTopAppBar(navController)
                "Investments/{selectedProduct}" -> InvestmentsTopAppBar(navController)
            }
        },
        bottomBar = {
            NavigationBar(navController) {
                showProductBottomSheet = true
            }
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavigationHost(navController, showProductBottomSheet)
            }
        }
    )

    // Custom BottomSheet for product selection (3/4 height, non-expandable)
    if (showProductBottomSheet) {
        CustomBottomSheet(
            onDismiss = { showProductBottomSheet = false },
            content = {
                ProductSelectionSheet(
                    navController = navController, // Pass navController for navigation
                    onProductSelected = { selectedProduct ->
                        // Close the sheet
                        showProductBottomSheet = false

                        // Perform navigation to the Investments screen, passing the selected product
                        navController.navigate("Investments/$selectedProduct")
                    }
                )
            }
        )
    }

    // ModalBottomSheet for settings
    if (showSettingsBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSettingsBottomSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            SettingsBottomSheetContent(onDismiss = { showSettingsBottomSheet = false })
        }
    }
}

@Composable
fun CustomBottomSheet(onDismiss: () -> Unit, content: @Composable () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val sheetHeight = screenHeight * 0.75f // 3/4 of the screen height

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(sheetHeight) // Set the custom height for the bottom sheet
                .background(Color.White, RoundedCornerShape(16.dp)) // Rounded on all corners
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            content()
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
                Spacer(modifier = Modifier.size(24.dp))
            }
        },
        actions = {
            IconButton(onClick = { onSettingsClick() }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    modifier = Modifier.size(16.dp),
                    contentDescription = "Settings",
                    tint = Color.Gray
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBar(navController: NavHostController, onInvestmentsClick: () -> Unit) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val items = listOf(
        NavigationItem("Home", Icons.Filled.Home),
        NavigationItem("Insights", Icons.Filled.Check),
        NavigationItem("Investments", Icons.Filled.ShoppingCart)
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute?.startsWith(item.title) == true, // Ensure correct selection
                onClick = {
                    if (item.title == "Home") {
                        navController.navigate("Home") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false // This ensures it goes back to Home
                            }
                            launchSingleTop = true
                        }
                    } else if (item.title == "Investments") {
                        onInvestmentsClick() // Show the product selection bottom sheet
                    } else {
                        navController.navigate(item.title) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Blue,
                    selectedTextColor = Color.Blue,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

data class NavigationItem(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)


@Composable
fun NavigationHost(navController: NavHostController, showProductBottomSheet: Boolean, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "Home", modifier = modifier) {
        composable("Home") { HomeScreen(navController) }
        composable("Insights") { InsightsScreen(navController) }
        composable("Investments/{selectedProduct}") { backStackEntry ->
            val selectedProduct = backStackEntry.arguments?.getString("selectedProduct")
            InvestmentsScreen(navController, selectedProduct)
        }
    }
}

@Composable
fun InsightsScreen(navController: NavHostController) {
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
fun InvestmentsScreen(navController: NavHostController, selectedProduct: String?) {
    // Define the base URL for the investments site
    val baseUrl = "https://am.jpmorgan.com/us/en/asset-management/adv/products/fund-explorer"

    // Update the URL based on the selected product type
    val url = when (selectedProduct) {
        "Mutual Funds" -> "$baseUrl/mutual-fund?Source=MI"
        "ETFs" -> "$baseUrl/etf?Source=MI"
        "Money Market Funds" -> "$baseUrl/money-market?Source=MI"
        "529 Portfolios" -> "$baseUrl/529?Source=MI"
        "Alternatives" -> "$baseUrl/alternatives?Source=MI"
        "SmartRetirement© Funds" -> "$baseUrl/smart-retirement?Source=MI"
        "Separately Managed Accounts" -> "$baseUrl/sma?Source=MI"
        "Commingled Funds" -> "$baseUrl/commingled-fund?Source=MI"
        else -> "$baseUrl/mutual-fund?Source=MI" // Default URL if no product is selected
    }

    // Display the WebView with the correct URL
    InvestmentsWebviewScreen(url = url)
}

@Composable
fun ProductSelectionSheet(navController: NavHostController, onProductSelected: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Select your product type",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )

        // List of product types
        val productTypes = listOf(
            "Mutual Funds",
            "ETFs",
            "Money Market Funds",
            "529 Portfolios",
            "Alternatives",
            "SmartRetirement© Funds",
            "Separately Managed Accounts",
            "Commingled Funds"
        )

        // Display product types as clickable text
        productTypes.forEach { productType ->
            Text(
                text = productType,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onProductSelected(productType) // Trigger navigation after selection
                    }
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
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