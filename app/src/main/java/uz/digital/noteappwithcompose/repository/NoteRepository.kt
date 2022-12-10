package uz.digital.noteappwithcompose.repository

import uz.digital.noteappwithcompose.database.NoteDao
import uz.digital.noteappwithcompose.entity.Note

class NoteRepository(
    private val dao: NoteDao
) {
    suspend fun saveNote(note: Note) = dao.saveNote(note)
    suspend fun deleteNote(note: Note) = dao.deleteNote(note)
    fun getAllNotes() = dao.getAllNotes()
    suspend fun updateNote(note: Note) = dao.updateNote(note)
    suspend fun getNoteById(id: Int) = dao.getNoteById(id)
}