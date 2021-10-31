package uz.olimjon_rustamov.dictionarycoroutines.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/"

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiservice:ApiService= getRetrofit().create(ApiService::class.java)
}