package uz.olimjon_rustamov.dictionarycoroutines.retrofit

import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.WordResponse
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.WordResponseItem

interface ApiHelper {
    suspend fun getWord(word: String): List<WordResponseItem>

}