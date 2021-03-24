package fr.henry.tapptic.network

import fr.henry.tapptic.data.Numbers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    fun getNumberByName(@Query("name") name: String): Call<NumbersResponse>

    @GET(".")
    fun getNumbersList(): Call<List<NumbersResponse>>
}