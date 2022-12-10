package uz.digital.noteappwithcompose.note_list

sealed class NoteListEvent {
    object OnScreenLaunched: NoteListEvent()
}