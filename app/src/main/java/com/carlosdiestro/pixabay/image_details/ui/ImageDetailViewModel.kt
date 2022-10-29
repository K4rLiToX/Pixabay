package com.carlosdiestro.pixabay.image_details.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.pixabay.image_details.domain.usecases.GetImageUseCase
import com.carlosdiestro.pixabay.utils.toPLO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state: MutableStateFlow<ImageDetailState> = MutableStateFlow(ImageDetailState())
    val state = _state.asStateFlow()

    init {
        fetchImage()
    }

    private fun fetchImage() {
        viewModelScope.launch {
            getImageUseCase(savedStateHandle.get<Int>("id")!!).collect { response ->
                _state.update {
                    it.copy(
                        image = response.toPLO()
                    )
                }
            }
        }
    }
}