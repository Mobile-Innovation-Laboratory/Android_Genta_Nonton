package com.example.nonton.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.nonton.data.model.Movie
import com.example.nonton.ui.viewmodel.FavoriteMovieViewModel
import com.example.nonton.ui.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: String,
    navController: NavController,
    viewModel: MovieViewModel = viewModel()
) {
    val movieState = remember { mutableStateOf<Movie?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(movieId) {
        coroutineScope.launch {
            val movie = viewModel.getMovieById(movieId, "603a01579ae8725f68c7e821e9f53591")
            movieState.value = movie
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = movieState.value?.title ?: "Detail Film") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (movieState.value == null) {
            // Tampilkan loading saat data belum tersedia
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val movie = movieState.value!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = rememberImagePainter(movie.getFullPosterUrl()),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = movie.title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
                Text(text = "Release Date: ${movie.releaseDate ?: "Unknown"}", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = movie.overview, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp))
            }
        }
    }
}




