package com.example.artistexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.artistexplorer.ui.theme.ArtistExplorerTheme
import com.example.artistexplorer.view.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtistExplorerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "artist"
                    ) {
                        composable("artist") {
                            ArtistScreen(navController = navController)
                        }
                        composable(
                            "albumDetail/{albumId}",
                            arguments = listOf(navArgument("albumId") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val albumId = backStackEntry.arguments?.getString("albumId") ?: ""
                            AlbumDetailScreen(albumId = albumId)
                        }
                    }
                }
            }
        }
    }
}
