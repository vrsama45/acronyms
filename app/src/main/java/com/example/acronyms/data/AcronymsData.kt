package com.example.acronyms.data

data class AcronymMeaningResponse(val sf: String?, val lfs: ArrayList<AcronymMeaning>? = null)

data class AcronymMeaning(val lf: String?, val freq: Int?)