package uz.digital.noteappwithcompose.detail_screen

import uz.digital.noteappwithcompose.entity.Note

sealed class DetailEvent {
    data class GetNoteById(val id: Int): DetailEvent()
    data class SaveNote(val note: Note): DetailEvent()
    data class UpdateNote(val note: Note): DetailEvent()
    data class DeleteNote(val note: Note): DetailEvent()
}