package com.example.bookshelf.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.data.model.Volumes
import com.example.bookshelf.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed class AppUIState {
    data class Success(val data: Volumes) : AppUIState()
    data object Loading : AppUIState()
    data class Error(val msg: String) : AppUIState()
}

@HiltViewModel
class BookshelfViewModel @Inject constructor(private val bookRepository: BookRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<AppUIState>(AppUIState.Loading)
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow()


    init {
        fetchVolumes()
    }

    fun fetchVolumes() {
        viewModelScope.launch {
            _uiState.value = AppUIState.Loading
            _uiState.value = try {
                AppUIState.Success(bookRepository.getVolumes("freak+inauthor:keyes"))
            } catch (e: IOException) {
                AppUIState.Error(msg = e.message.toString())
            } catch (e: HttpException) {
                AppUIState.Error(msg = e.response()?.errorBody()?.toString() ?: "")
            }
        }
    }
}