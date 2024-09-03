package com.example.coroutinetasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _loadingState = MutableSharedFlow<String>(replay = 1)
    val loadingState = _loadingState.asSharedFlow()

    private var job: Job? = null

    fun startLoading() {
        job?.cancel()
        job = viewModelScope.launch {
            loadingFlow().collect{
                _loadingState.emit(it)
            }
    }}

    private fun loadingFlow() = flow{
        var page = 1
        while (true) {
            delay(3000)
            emit("Loaded page $page")
            page++
        }

    }.cancellable()

    fun stopLoading() {
        job?.cancel()
    }
}