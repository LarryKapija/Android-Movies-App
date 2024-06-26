package com.larrykapija.moviesapp.ui.screens.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun BackgroundImage(imageUrl: String) {
    val currentImageUrl = remember { mutableStateOf(imageUrl) }
    val previousImageUrl = remember { mutableStateOf(imageUrl) }
    val opacity = remember { Animatable(0f) }

    val gradientColor = MaterialTheme.colorScheme.secondary

    LaunchedEffect(imageUrl) {
        if (imageUrl != currentImageUrl.value) {
            previousImageUrl.value = currentImageUrl.value
            currentImageUrl.value = imageUrl
            opacity.snapTo(0f)
            opacity.animateTo(1f, animationSpec = tween(durationMillis = 1000))
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(previousImageUrl.value)
                .crossfade(true)
                .build(),
            contentDescription = "old background image",
            modifier = Modifier
                .matchParentSize()
                .blur(5.dp),
            contentScale = ContentScale.Crop
        )

        // New image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(currentImageUrl.value)
                .crossfade(true)
                .build(),
            contentDescription = "new background image",
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer { alpha = opacity.value }
                .blur(5.dp),
            contentScale = ContentScale.Crop
        )

        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasHeight = size.height

            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.3f),
                        Color.Transparent,
                        Color.Transparent,
                        gradientColor.copy(alpha = 0.5f),
                        gradientColor.copy(alpha = 0.7f),
                        gradientColor.copy(alpha = 0.9f),
                        gradientColor
                    )
                    ,
                    startY = canvasHeight * 0f,
                    endY = Float.POSITIVE_INFINITY,
                )
            )
        }
    }
}