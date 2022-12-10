package uz.digital.noteappwithcompose.note_list

import uz.digital.noteappwithcompose.entity.Note

data class NoteListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: List<Note> = emptyList()
)