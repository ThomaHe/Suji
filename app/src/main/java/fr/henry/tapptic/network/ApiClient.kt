package fr.henry.tapptic.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit: Retrofit
    private val okhttpClient: OkHttpClient
    val serviceAPI: ApiService

    init {
        val builder = OkHttpClient().newBuilder()

        okhttpClient = builder.build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://dev.tapptic.com/test/json.php/")
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        serviceAPI = retrofit.create(ApiService::class.java)
    }
}