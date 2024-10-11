package com.example.insights


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


// InsightsIconsRow.kt
@Composable
fun LatestInsightsBox(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(0.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Latest Insights",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            InsightsIconsRow(navController = navController)  // Pass navController here
        }
    }
}

@Composable
fun InsightsIconsRow(navController: NavHostController) {
    val insightsList = listOf(
        InsightItem("Market", Color.Blue),
        InsightItem("Portfolio", Color.Green),
        InsightItem("Retirement", Color.Yellow),
        InsightItem("ETF", Color(0xFFFFA500))
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        insightsList.forEach { item ->
            InsightIconItem(
                item = item,
                navController = navController,  // Pass navController here
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun InsightIconItem(item: InsightItem, navController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .wrapContentSize()
            .clickable {
                when (item.label) {
                    "Market" -> navController.navigate("Insights/Market")
                    "Portfolio" -> navController.navigate("Insights/Portfolio")
                    "Retirement" -> navController.navigate("Insights/Retirement")
                    "ETF" -> navController.navigate("Insights/Etf")
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon inside a circle with shadow
        Box(
            modifier = Modifier
                .size(50.dp)
                .shadow(4.dp, shape = CircleShape) // Add shadow
                .background(color = item.color, shape = CircleShape), // Circular background
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.fillMaxSize(0.5f)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Text label below the icon
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth(),
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Data class to hold label and color for each insight
data class InsightItem(val label: String, val color: Color)