package com.greeners.greenguardian.presentation.feature.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<S, E>(initialState: S) : ViewModel(), KoinComponent {

    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<E?>()
    val effect = _effect.asSharedFlow().throttleFirst(500).mapNotNull { it }


    protected fun updateState(updater: (S) -> S) {
        _state.update(updater)
    }

    protected fun sendNewEffect(newEffect: E) {
        viewModelScope.launch(Dispatchers.IO) {
            _effect.emit(newEffect)
        }
    }

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: () -> Unit,
        inScope: CoroutineScope = viewModelScope
    ): Job {
        return runWithErrorCheck(
            onError = { _, _ -> onError() },
            inScope = inScope
        ) {
            val result = function()
            onSuccess(result)
        }
    }

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onErrorWithThrowable: (Throwable) -> Unit,
        inScope: CoroutineScope = viewModelScope
    ): Job {
        return runWithErrorCheck(
            onError = { throwable, _ -> onErrorWithThrowable(throwable) },
            inScope = inScope
        ) {
            val result = function()
            onSuccess(result)
        }
    }

    private fun runWithErrorCheck(
        onError: (Throwable, String?) -> Unit,
        inScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Unconfined,
        function: suspend () -> Unit
    ): Job {
        return inScope.launch(dispatcher) {
            try {
                function()
            } catch (e: Exception) {
                Log.e("BaseViewModel", "Error: ${e.message}")
                onError(e, e.message)
            }
        }
    }

    private fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> {
        var job: Job = Job().apply { complete() }
        return onCompletion { job.cancel() }.run {
            flow {
                coroutineScope {
                    collect { value ->
                        if (!job.isActive) {
                            emit(value)
                            job = launch { delay(windowDuration) }
                        }
                    }
                }
            }
        }
    }

}