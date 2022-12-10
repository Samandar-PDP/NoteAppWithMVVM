package uz.digital.noteappwithcompose.note_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uz.digital.noteappwithcompose.R
import uz.digital.noteappwithcompose.entity.Note
import uz.digital.noteappwithcompose.screen.Screen
import uz.digital.noteappwithcompose.ui.theme.Shapes

@Composable
fun NoteListScreen(navHostController: NavHostController, viewModel: NoteListViewModel) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(NoteListEvent.OnScreenLaunched)
    }
    if (state.isLoading) {
        Loading()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        text = stringResource(id = R.string.note_list)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navHostController.navigate("${Screen.Detail.route}/-1/null/null") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        LazyColumn {
            itemsIndexed(state.success) { index, item ->
                NoteItem(
                    index = index,
                    note = item,
                    onItemClick = {
                        navHostController.navigate("${Screen.Detail.route}/${item.id}/${item.title}/${item.content}")
                    }
                )
            }
        }
    }
}

@Composable
fun NoteItem(index: Int, note: Note, onItemClick: () -> Unit) {
    Card(
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(6.dp)
            .clickable {
                onItemClick()
            }
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${index.plus(1)}",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Column {
                Text(
                    text = note.title,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = note.content,
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun NoteItemPreview() {

}
