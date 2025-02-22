package com.example.nonton.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nonton.R
import com.example.nonton.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val userName = user?.displayName ?: "User"
    val userEmail = user?.email ?: "user@example.com"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .shadow(8.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_person_24),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                auth.signOut()
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .shadow(4.dp, shape = RoundedCornerShape(25.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Logout")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Logout", style = MaterialTheme.typography.bodyLarge)
        }
    }
}