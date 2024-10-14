package com.example.insights

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MarketInsightsScreen(navController: NavHostController) {
    MarketInsightsTopAppBar(navController)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Market Insights", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun PortfolioInsightsScreen(navController: NavHostController) {
    PortfolioInsightsTopAppBar(navController)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Portfolio Insights", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun RetirementInsightsScreen(navController: NavHostController) {
    RetirementInsightsTopAppBar(navController)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Retirement Insights", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun EtfInsightsScreen(navController: NavHostController) {
    EtfInsightsTopAppBar(navController)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("ETF Insights", style = MaterialTheme.typography.titleLarge)
    }
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MarketInsightsTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("Market Insights") },
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
fun PortfolioInsightsTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("Portfolio Insights") },
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
fun RetirementInsightsTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("Retirement Insights") },
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
fun EtfInsightsTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("ETF Insights") },
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


