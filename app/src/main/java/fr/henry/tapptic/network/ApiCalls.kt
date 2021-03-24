package fr.henry.tapptic.network

import fr.henry.tapptic.data.Numbers
import retrofit2.Call

object ApiCalls {

    fun getNumberByName(name: String): Call<NumbersResponse> {
        return ApiClient.serviceAPI.getNumberByName(name)
    }

    fun getNumbersList(): Call<List<NumbersResponse>> {
        return ApiClient.serviceAPI.getNumbersList()
    }
}