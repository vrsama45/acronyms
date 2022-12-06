package com.example.acronyms.service

import com.example.acronyms.data.AcronymMeaningResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class AcronymsServiceImpl : AcronymsService {

    interface AcronymsServiceApi {
        @GET("acromine/dictionary.py")
        suspend fun getAcronymMeanings(@Query("sf") acronym: String): Response<List<AcronymMeaningResponse>>

        companion object {

            var BASE_URL = "http://www.nactem.ac.uk/software/"
            fun create(): AcronymsServiceApi {
                val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                return retrofit.create(AcronymsServiceApi::class.java)

            }
        }
    }

    private val apiInterface = AcronymsServiceApi.create()

    override suspend fun getAcronymMeanings(acronym: String): Response<List<AcronymMeaningResponse>> {
        return apiInterface.getAcronymMeanings(acronym)
    }
}