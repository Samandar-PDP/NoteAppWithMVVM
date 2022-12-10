package uz.digital.noteappwithcompose.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.digital.noteappwithcompose.repository.NoteRepository

class NoteViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteListViewModel(repository) as T
    }
}