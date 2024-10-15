package com.example.insights.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.insights.R

// Set of Material typography styles to start with
val Typography = Typography()

val jpmampromed = FontFamily(
    Font(R.font.jpmampromed_family, FontWeight.Normal)
)
val body2Strong = TextStyle(
    fontFamily = jpmampromed,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 22.sp
)