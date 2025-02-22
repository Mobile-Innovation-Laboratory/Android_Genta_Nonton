package com.example.nonton.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.nonton.ui.viewmodel.MovieViewModel
import com.example.nonton.ui.navigation.Screen
import com.example.nonton.data.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MovieViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val apiKey = "603a01579ae8725f68c7e821e9f53591"
    val movies by viewModel.movies.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMovies(apiKey)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nonton App", fontSize = 20.sp) })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { navController.navigate(Screen.Home.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Note") },
                    label = { Text("Note") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Note.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Profile.route) }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(movies) { movie ->
                MovieItem(movie, navController)
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate(Screen.Detail.createRoute(movie.id.toString())) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(movie.getFullPosterUrl()),
                contentDescription = movie.title,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = movie.overview, maxLines = 3, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }
    }
}
