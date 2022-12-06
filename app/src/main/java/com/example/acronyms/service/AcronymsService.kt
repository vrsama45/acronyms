package com.example.acronyms.service

import com.example.acronyms.data.AcronymMeaningResponse
import retrofit2.Response

interface AcronymsService {
    suspend fun getAcronymMeanings(acronym: String):  Response<List<AcronymMeaningResponse>>
}