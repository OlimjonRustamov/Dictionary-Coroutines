package uz.olimjon_rustamov.dictionarycoroutines.roomDB

import uz.olimjon_rustamov.dictionarycoroutines.home.models.LastSearched

interface DatabaseHelper {

    fun getLastSearched(): List<LastSearched>

    fun insertLastSearched(last: LastSearched)

    fun deleteLastSearched(last: LastSearched)

    fun updateLastSearched(last: LastSearched)

    fun getSavedLastSearched(): List<LastSearched>
}