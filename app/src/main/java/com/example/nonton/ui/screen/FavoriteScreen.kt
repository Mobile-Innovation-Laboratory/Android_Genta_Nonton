package com.example.nonton.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.nonton.data.local.entity.FavoriteMovie
import com.example.nonton.ui.viewmodel.FavoriteMovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteMovieViewModel = viewModel()) {
    val favorites by viewModel.getAllFavorites().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Movies", fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = if (favorites.isEmpty()) Alignment.Center else Alignment.TopStart
        ) {
            if (favorites.isEmpty()) {
                Text(text = "No favorite movies yet", color = Color.Gray, fontSize = 18.sp)
            } else {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(favorites) { movie ->
                        FavoriteMovieItem(
                            movie = movie,
                            onRemove = { viewModel.removeFromFavorites(movie) },
                            onClick = { navController.navigate("detail/${movie.id}") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteMovieItem(movie: FavoriteMovie, onRemove: () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberImagePainter(movie.posterPath),
                contentDescription = movie.title,
                modifier = Modifier
                    .size(90.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
            }
            IconButton(onClick = onRemove) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
