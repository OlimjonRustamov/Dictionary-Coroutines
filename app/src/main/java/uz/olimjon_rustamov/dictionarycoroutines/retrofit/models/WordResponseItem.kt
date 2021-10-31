package uz.olimjon_rustamov.dictionarycoroutines.retrofit.models

import java.io.Serializable

data class WordResponseItem(
    val meanings: List<Meaning>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val word: String
):Serializable