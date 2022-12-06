package com.example.acronyms.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acronyms.data.AcronymMeaning
import com.example.acronyms.data.AcronymMeaningResponse
import com.example.acronyms.service.AcronymsServiceImpl
import com.example.acronyms.viewstate.AcronymMeaningState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AcronymsViewModel(private val service: AcronymsServiceImpl = AcronymsServiceImpl()) :
    ViewModel() {

    private val _uiState = MutableStateFlow(AcronymMeaningState())
    val uiState: StateFlow<AcronymMeaningState> = _uiState.asStateFlow()

    fun getAcronymMeanings(acronym: String) {

        viewModelScope.launch {
            try {
                val result = service.getAcronymMeanings(acronym.trim())
                if (result.errorBody() != null) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            showLoading = false,
                            isError = true,
                            acronymInput = acronym,
                            acronymMeaningList = null
                        )
                    }
                } else {
                    when (val meanings = getMeaningList(result.body())) {
                        null -> {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    showLoading = false,
                                    isError = true,
                                    acronymInput = acronym,
                                    acronymMeaningList = null
                                )
                            }
                        }
                        else -> {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    showLoading = false,
                                    isError = false,
                                    acronymInput = acronym,
                                    acronymMeaningList = meanings
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        showLoading = false,
                        isError = true,
                        acronymInput = acronym,
                        acronymMeaningList = null
                    )
                }
            }
        }
    }

    private fun getMeaningList(list: List<AcronymMeaningResponse>?): List<AcronymMeaning>? {
        list?.let { responseList ->
            if (responseList.isNotEmpty()) {
                responseList[0].lfs?.let {
                    return if (it.size > 0) it else emptyList()
                }
            } else {
                return emptyList()
            }
        }

        return null
    }
}