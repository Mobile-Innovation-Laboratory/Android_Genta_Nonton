package com.example.nonton.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.example.nonton.data.local.entity.FavoriteMovie
import com.example.nonton.ui.viewmodel.FavoriteMovieViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, movieId: String, viewModel: FavoriteMovieViewModel = viewModel()) {
    val isFavorite by viewModel.isFavorite(movieId.toInt()).collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    val movie by viewModel.getMovieById(movieId.toInt()).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Details", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        movie?.let { movieData ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(movieData.posterPath),
                    contentDescription = movieData.title,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = movieData.title, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = movieData.overview, fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (isFavorite) {
                                viewModel.removeFromFavorites(movieData)
                            } else {
                                viewModel.addToFavorites(movieData)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFavorite) Color.Red else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = if (isFavorite) "Remove from Favorites" else "Add to Favorites")
                }
            }
        } ?: run {
            // Tampilkan data sementara jika film tidak ditemukan di database
            val placeholderMovie = FavoriteMovie(
                id = movieId.toInt(),
                title = "Unknown Movie",
                overview = "No details available",
                posterPath = "https://via.placeholder.com/200"
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(placeholderMovie.posterPath),
                    contentDescription = placeholderMovie.title,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = placeholderMovie.title, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = placeholderMovie.overview, fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.addToFavorites(placeholderMovie)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = "Add to Favorites")
                }
            }
        }
    }
}


