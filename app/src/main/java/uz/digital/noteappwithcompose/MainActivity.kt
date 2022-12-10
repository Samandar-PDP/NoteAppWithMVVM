package uz.digital.noteappwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uz.digital.noteappwithcompose.database.NoteDatabase
import uz.digital.noteappwithcompose.detail_screen.DetailScreen
import uz.digital.noteappwithcompose.detail_screen.DetailViewModel
import uz.digital.noteappwithcompose.detail_screen.DetailViewModelFactory
import uz.digital.noteappwithcompose.note_list.NoteListScreen
import uz.digital.noteappwithcompose.note_list.NoteListViewModel
import uz.digital.noteappwithcompose.note_list.NoteViewModelFactory
import uz.digital.noteappwithcompose.repository.NoteRepository
import uz.digital.noteappwithcompose.screen.Screen
import uz.digital.noteappwithcompose.ui.theme.NoteAppWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = NoteDatabase.invoke(this).dao
        val repository = NoteRepository(dao)
        val noteListViewModelFactory = NoteViewModelFactory(repository)
        val detailViewModelFactory = DetailViewModelFactory(repository)
        val noteListViewModel =
            ViewModelProvider(this, noteListViewModelFactory)[NoteListViewModel::class.java]
        val detailViewModel =
            ViewModelProvider(this, detailViewModelFactory)[DetailViewModel::class.java]
        setContent {
            NoteAppWithComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navHostController = rememberNavController()
                    NavHost(
                        navController = navHostController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(route = Screen.Home.route) {
                            NoteListScreen(navHostController, noteListViewModel)
                        }
                        composable(
                            route = "${Screen.Detail.route}/{id}/{title}/{content}",
                            arguments = listOf(
                                navArgument(
                                    name = "id"
                                ) {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val id = it.arguments?.getInt("id") ?: -1
                            val title = it.arguments?.getString("title") ?: ""
                            val content = it.arguments?.getString("content") ?: ""
                            DetailScreen(
                                navHostController = navHostController,
                                id = id,
                                _title = title,
                                _content = content,
                                viewModel = detailViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
