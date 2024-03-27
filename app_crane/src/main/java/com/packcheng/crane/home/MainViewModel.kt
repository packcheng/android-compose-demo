package com.packcheng.crane.home

import androidx.compose.samples.crane.di.DefaultDispatcher
import androidx.lifecycle.ViewModel
import com.packcheng.crane.data.DestinationsRepository
import com.packcheng.crane.util.ZbcLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val destinationsRepository: DestinationsRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    init {
        ZbcLog.i(TAG, "$destinationsRepository")
        ZbcLog.i(TAG, "$defaultDispatcher")
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

    override fun onCleared() {
        super.onCleared()
        ZbcLog.i(TAG, "onCleared $this")
    }
}