package com.larrykapija.moviesapp.ui.screens.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.larrykapija.moviesapp.network.response.Movie

@Composable
fun MovieItem(movie: Movie, index: Int, focusedItemIndex: Int) {
    val isFocused = index == focusedItemIndex

    val rotationDegrees by animateFloatAsState(targetValue = when {
        index < focusedItemIndex -> -10f
        index > focusedItemIndex -> 10f
        else -> 0f
    }, label = "movie item rotation animation"
    )

    val alpha by animateFloatAsState(targetValue = if (isFocused) 1f else 0.7f, label = "movie item alpha animation")
    val scale by animateFloatAsState(targetValue = if (isFocused) 1f else 0.75f, label = "movie item scale animation")

    AsyncImage(
        model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
        contentDescription = "Movie Poster",
        modifier = Modifier
            .padding(top = if (isFocused) 0.dp else 30.dp,
                bottom = if (isFocused) 30.dp else 0.dp
            )
            .graphicsLayer {
                rotationZ = rotationDegrees
                this.alpha = alpha
                scaleX = scale
                scaleY = scale
            }
            .size(200.dp, 300.dp),
    )
}