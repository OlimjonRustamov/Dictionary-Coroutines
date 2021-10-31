package uz.olimjon_rustamov.dictionarycoroutines.retrofit

import retrofit2.Response
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.WordResponse
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.WordResponseItem

class ApiHelperImpl(val apiServise: ApiService) : ApiHelper {

    override suspend fun getWord(word: String): List<WordResponseItem> =
        apiServise.getWord(word)

}