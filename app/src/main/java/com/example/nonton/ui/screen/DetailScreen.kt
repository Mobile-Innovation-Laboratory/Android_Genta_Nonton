package com.example.nonton.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.nonton.data.local.entity.FavoriteMovie
import com.example.nonton.data.model.Movie
import com.example.nonton.ui.viewmodel.FavoriteMovieViewModel
import com.example.nonton.ui.viewmodel.MovieViewModel
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: String,
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteMovieViewModel = viewModel()
) {
    var movie by remember { mutableStateOf<Movie?>(null) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(movieId) {
        val movieResult = viewModel.getMovieById(movieId, "603a01579ae8725f68c7e821e9f53591")
        movie = movieResult
        isFavorite = favoriteViewModel.isFavorite(movieResult?.id ?: 0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = movie?.title ?: "Detail Film",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    FavoriteIcon(
                        isFavorite = isFavorite,
                        onToggleFavorite = {
                            movie?.let { movieData ->
                                val favoriteMovie = FavoriteMovie(
                                    id = movieData.id,
                                    title = movieData.title,
                                    posterUrl = movieData.getFullPosterUrl(),
                                    overview = movieData.overview,
                                    releaseDate = movieData.releaseDate ?: "Unknown"
                                )
                                if (isFavorite) {
                                    favoriteViewModel.removeFavorite(favoriteMovie)
                                } else {
                                    favoriteViewModel.addFavorite(favoriteMovie)
                                }
                                isFavorite = !isFavorite
                            }
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        if (movie == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            movie?.let { movie ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(movie.getFullPosterUrl()),
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Release Date: ${movie.releaseDate ?: "Unknown"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteIcon(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    IconButton(onClick = onToggleFavorite) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (isFavorite) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}
