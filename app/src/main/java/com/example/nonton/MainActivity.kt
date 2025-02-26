package com.example.nonton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nonton.data.repository.NotesRepository
import com.example.nonton.ui.navigation.Screen
//import com.example.nonton.ui.screen.AddEditNoteScreen
import com.example.nonton.ui.screen.DetailScreen
import com.example.nonton.ui.screen.FavoriteScreen
import com.example.nonton.ui.screen.HomeScreen
import com.example.nonton.ui.screen.LoginScreen
import com.example.nonton.ui.screen.NoteListScreen
import com.example.nonton.ui.screen.ProfileScreen
import com.example.nonton.ui.screen.RegisterScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            MovieApp()
        }
    }
}

@Composable
fun MovieApp() {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "defaultUserId"
    val repository = NotesRepository(userId)

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen(navController)
        }
        composable(Screen.Note.route) {
            NoteListScreen(navController, repository)
        }
//        composable(Screen.AddNote.route){
//            AddEditNoteScreen(navController)
//        }
        composable("detail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            DetailScreen(movieId, navController)
        }
        composable("favorite") {
            FavoriteScreen(navController)
        }

    }
}