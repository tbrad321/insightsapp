package com.example.insights

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.unit.Density
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.gestures.ScrollScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


data class CarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    @StringRes val contentDescriptionResId: Int,
    val title: String // Title text for each article card
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    // Carousel items
    val carouselItems = listOf(
        CarouselItem(0, R.drawable.skyline, R.string.carousel_image_1_description, stringResource(R.string.article_title_1)),
        CarouselItem(1, R.drawable.skyline, R.string.carousel_image_2_description, stringResource(R.string.article_title_2)),
        CarouselItem(2, R.drawable.skyline, R.string.carousel_image_3_description, stringResource(R.string.article_title_3)),
        CarouselItem(3, R.drawable.skyline, R.string.carousel_image_4_description, stringResource(R.string.article_title_4)),
        CarouselItem(4, R.drawable.skyline, R.string.carousel_image_5_description, stringResource(R.string.article_title_5))
    )

    // LazyRow scroll state
    val listState = rememberLazyListState()

    // Track which item is centered (visible)
    val carouselState = remember { mutableStateOf(0) }

    // Update carouselState based on the scroll position of LazyRow
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
        }.collect { (index, scrollOffset) ->
            val visibleIndex = if (scrollOffset > 0) index + 1 else index
            carouselState.value = visibleIndex
        }
    }

    // Remember SnapFlingBehavior for snapping effect
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Enables vertical scrolling
            .padding(top = 8.dp), // Padding only at the top to avoid white space on the sides
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Box to wrap the entire LazyRow section
        Box(
            modifier = Modifier
                .fillMaxWidth() // Ensure the Box takes up the full width
                .background(color = colorResource(R.color.purple_200)) // Apply background to the entire section
        ) {
            LazyRow(
                state = listState,
                flingBehavior = snapFlingBehavior,
                contentPadding = PaddingValues(16.dp), // No extra padding for the LazyRow
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Space between items
            ) {
                itemsIndexed(carouselItems) { index, item ->
                    ArticleCard(
                        imageResId = item.imageResId,
                        title = item.title,
                        modifier = Modifier
                            .width(300.dp) // Width of each card
                    )
                }
            }
        }

        // Dots indicator to show the number of items and the current position
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(carouselItems.size) { index ->
                val color = if (index == carouselState.value) Color.Blue else Color.Gray
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(6.dp)
                        .padding(horizontal = 2.dp)
                        .background(color, RoundedCornerShape(50))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        LatestInsightsBox()
    }
}
