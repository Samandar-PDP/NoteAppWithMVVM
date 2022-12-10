package uz.digital.noteappwithcompose.detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.digital.noteappwithcompose.repository.NoteRepository

class DetailViewModel(
    private val repository: NoteRepository
) : ViewModel() {
    private val _state: MutableStateFlow<DetailState> = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> get() = _state
    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.GetNoteById -> {
                viewModelScope.launch {
                    val note = repository.getNoteById(event.id)
                    _state.value = DetailState(note)
                }
            }
            is DetailEvent.DeleteNote -> {
                viewModelScope.launch {
                    repository.deleteNote(event.note)
                }
            }
            is DetailEvent.SaveNote -> {
                viewModelScope.launch {
                    repository.saveNote(event.note)
                }
            }
            is DetailEvent.UpdateNote -> {
                viewModelScope.launch {
                    repository.updateNote(event.note)
                }
            }
        }
    }
}