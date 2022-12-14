package uz.digital.noteappwithcompose.detail_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uz.digital.noteappwithcompose.entity.Note

@Composable
fun DetailScreen(
    id: Int,
    _title: String,
    _content: String,
    navHostController: NavHostController,
    viewModel: DetailViewModel
) {
    val isForUpdate by remember {
        mutableStateOf(_title != "null")
    }
    var title by remember {
        mutableStateOf(if (_title != "null") _title else "")
    }
    var content by remember {
        mutableStateOf(if (_content != "null") _content else "")
    }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (_title != "null") _title else "Create Note"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (isForUpdate) {
                        IconButton(onClick = {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    "Deleted!"
                                )
                                viewModel.onEvent(
                                    DetailEvent.DeleteNote(
                                        Note(
                                            id,
                                            _title,
                                            _content
                                        )
                                    )
                                )
                                navHostController.popBackStack()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (isForUpdate) {
                    scope.launch {
                        viewModel.onEvent(DetailEvent.UpdateNote(Note(id, title, content)))
                        scaffoldState.snackbarHostState.showSnackbar(
                            "Updated"
                        )
                    }
                } else {
                    scope.launch {
                        viewModel.onEvent(
                            DetailEvent.SaveNote(
                                Note(
                                    title = title,
                                    content = content
                                )
                            )
                        )
                        scaffoldState.snackbarHostState.showSnackbar(
                            "Successfully saved!"
                        )
                        navHostController.popBackStack()
                    }
                }
            }) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = "")
            }
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = {
                    Text(text = "Enter note title")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = content,
                onValueChange = {
                    content = it
                },
                label = {
                    Text(text = "Enter note content")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}