package com.masa.sideeffectshandlerdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<MainEvents>()
    val sharedFlow = _sharedFlow

    init {
        emitEvent(MainEvents.ShowSnackBar("test"))
    }

    fun emitEvent(events: MainEvents) {
        viewModelScope.launch {
            _sharedFlow.emit(events)
        }
    }

    suspend fun fetchMedia(url: String?, type: String?): Media? {
        if (url == null && type == null) {
            return null
        }

        delay(3000L)
        return Media.Video
    }

    sealed class MainEvents {
        data class ShowSnackBar(val message: String) : MainEvents()
    }

    sealed class Media {
        object Video : Media()
        object Image : Media()
        object Audio : Media()
    }
}
