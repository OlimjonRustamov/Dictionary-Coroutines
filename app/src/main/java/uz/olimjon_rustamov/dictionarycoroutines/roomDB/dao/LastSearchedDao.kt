package uz.olimjon_rustamov.dictionarycoroutines.roomDB.dao

import androidx.room.*
import uz.olimjon_rustamov.dictionarycoroutines.home.models.LastSearched

@Dao
interface LastSearchedDao {

    @Query("select * from lastsearched limit 10")
    fun getLastSearched(): List<LastSearched>

    @Insert
    fun insertLastSearched(last: LastSearched)

    @Delete
    fun deleteLastSearched(last: LastSearched)

    @Update
    fun updateLastSearched(last: LastSearched)

    @Query("select * from lastsearched where lastsearched.isSaved")
    fun getSavedLastSearched():List<LastSearched>
}