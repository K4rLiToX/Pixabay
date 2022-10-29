package com.carlosdiestro.pixabay.images.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.pixabay.images.domain.usecases.GetImagesUseCase
import com.carlosdiestro.pixabay.images.domain.usecases.SubmitQueryUseCase
import com.carlosdiestro.pixabay.utils.Constants
import com.carlosdiestro.pixabay.utils.toSimplePLO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val submitQueryUseCase: SubmitQueryUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<ImagesState> = MutableStateFlow(ImagesState())
    val state = _state.asStateFlow()

    init {
        fetchImages()
        submitQuery(Constants.DEFAULT_QUERY)
    }

    fun onEvent(event: ImagesEvent) {
        when (event) {
            is ImagesEvent.SubmitQuery -> {
                submitQuery(event.value)
                fetchImages(event.value)
            }
        }
    }

    private fun fetchImages(value: String = "") {
        viewModelScope.launch {
            val query = if (value == "") Constants.DEFAULT_QUERY else value
            getImagesUseCase(query).collect { response ->
                _state.update {
                    it.copy(
                        images = response.toSimplePLO()
                    )
                }
            }
        }
    }

    private fun submitQuery(value: String) {
        viewModelScope.launch {
            submitQueryUseCase(value)
        }
    }
}