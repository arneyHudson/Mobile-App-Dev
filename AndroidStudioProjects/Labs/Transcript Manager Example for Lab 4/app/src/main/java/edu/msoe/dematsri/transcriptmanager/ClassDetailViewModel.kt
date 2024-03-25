package edu.msoe.dematsri.transcriptmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class ClassDetailViewModel(classID: UUID) : ViewModel() {
    private val classRepository = ClassDetailRepository.get()

    val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private val _classDetail: MutableStateFlow<ClassDetail?> = MutableStateFlow(null)
    val classDetail: StateFlow<ClassDetail?> = _classDetail.asStateFlow()
    init {
        CoroutineScope(coroutineContext).launch {
            _classDetail.value = classRepository.getClassDetail(classID)
        }
    }

    fun updateClass(onUpdate: (ClassDetail) -> ClassDetail) {
        _classDetail.update { oldClass ->
            oldClass?.let { onUpdate(it) }
       }
    }

    override fun onCleared() {
        super.onCleared()

        _classDetail.value?.let {classRepository.updateClass(it)}
    }
}

class ClassDetailViewModelFactory(
    private val classID: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ClassDetailViewModel(classID) as T
    }
}
