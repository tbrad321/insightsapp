package com.example.insights


import androidx.compose.foundation.background
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

// InsightsIconsRow.kt
@Composable
fun LatestInsightsBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Outer padding around the box
            .shadow(0.dp, shape = RoundedCornerShape(8.dp)) // Apply shadow for elevation
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp)) // Light gray background with rounded corners
            .padding(16.dp) // Inner padding for content inside the box
    ) {
        Column {
            // Header
            Text(
                text = "Latest Insights",
                style = MaterialTheme.typography.headlineSmall, // Use headlineSmall in place of h6
                modifier = Modifier.padding(bottom = 16.dp) // Padding between header and buttons
            )
            // The row containing all four buttons
            InsightsIconsRow()
        }
    }
}

@Composable
fun InsightsIconsRow() {
    val insightsList = listOf(
        InsightItem("Market", Color.Blue),
        InsightItem("Portfolio", Color.Green),
        InsightItem("Retirement", Color.Yellow),
        InsightItem("ETF", Color(0xFFFFA500)) // Example orange color
    )

    // Row for holding the insights
    Row(
        modifier = Modifier
            .fillMaxWidth() // Take up the full width of the screen
            .padding(horizontal = 0.dp), // Reduce horizontal padding to use more space on the sides
        horizontalArrangement = Arrangement.spacedBy(4.dp) //
    ) {
        insightsList.forEach { item ->
            InsightIconItem(
                item = item,
                modifier = Modifier.weight(1f) // Each item takes 1/4 of the available space
            )
        }
    }
}

@Composable
fun InsightIconItem(item: InsightItem, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(4.dp) // Reduced padding around each item to use more space
            .wrapContentSize(),
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
                imageVector = Icons.Default.ArrowForward, // Replace with custom vector if needed
                contentDescription = null,
                tint = Color.White, // Icon color
                modifier = Modifier.fillMaxSize(0.5f) // Icon takes 50% of the Box size
            )
        }
        Spacer(modifier = Modifier.height(4.dp)) // Space between the icon and text

        // Text label below the icon
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center, // Center align the text
            maxLines = 2, // Allow text to wrap to 2 lines
            modifier = Modifier.fillMaxWidth(), // Let text take the full width of its container
            overflow = TextOverflow.Ellipsis // Handle text overflow
        )
    }
}

// Data class to hold label and color for each insight
data class InsightItem(val label: String, val color: Color)