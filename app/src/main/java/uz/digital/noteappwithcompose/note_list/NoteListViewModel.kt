package uz.digital.noteappwithcompose.note_list

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uz.digital.noteappwithcompose.repository.NoteRepository

class NoteListViewModel(
    private val repository: NoteRepository
): ViewModel() {

    private val _state: MutableStateFlow<NoteListState> = MutableStateFlow(NoteListState())
    val state: StateFlow<NoteListState> get() = _state

    fun onEvent(event: NoteListEvent) {
        when(event) {
            is NoteListEvent.OnScreenLaunched -> {
                viewModelScope.launch {
                    repository.getAllNotes()
                        .onStart {
                            _state.value = _state.value.copy(isLoading = true)
                            delay(1000L)
                        }
                        .catch {
                            _state.value = _state.value.copy(isLoading = false, error = it.message!!)
                        }
                        .collect {
                            _state.value = _state.value.copy(isLoading = false, success = it)
                        }
                }
            }
        }
    }
}