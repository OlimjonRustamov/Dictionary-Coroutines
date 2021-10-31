package uz.olimjon_rustamov.dictionarycoroutines.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.WordResponseItem

interface ApiService {
    @GET("en/{word}")
    suspend fun getWord(@Path("word") word: String): List<WordResponseItem>

}