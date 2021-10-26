package uz.olimjon_rustamov.dictionarycoroutines.roomDB

import uz.olimjon_rustamov.dictionarycoroutines.home.models.LastSearched

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override fun getLastSearched(): List<LastSearched> =
        appDatabase.lastSearchDao().getLastSearched()

    override fun insertLastSearched(last: LastSearched) =
        appDatabase.lastSearchDao().insertLastSearched(last)

    override fun deleteLastSearched(last: LastSearched) =
        appDatabase.lastSearchDao().deleteLastSearched(last)

    override fun updateLastSearched(last: LastSearched) =
        appDatabase.lastSearchDao().updateLastSearched(last)

    override fun getSavedLastSearched(): List<LastSearched> =
        appDatabase.lastSearchDao().getSavedLastSearched()

}