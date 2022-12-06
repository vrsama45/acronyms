package com.example.acronyms.viewstate

import com.example.acronyms.data.AcronymMeaning

data class AcronymMeaningState(
    val showLoading: Boolean = false,
    val acronymInput: String? = null,
    val acronymMeaningList: List<AcronymMeaning>? = null,
    val isError: Boolean = false
)