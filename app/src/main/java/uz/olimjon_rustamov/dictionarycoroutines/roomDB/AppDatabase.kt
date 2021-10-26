package uz.olimjon_rustamov.dictionarycoroutines.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.olimjon_rustamov.dictionarycoroutines.home.models.LastSearched
import uz.olimjon_rustamov.dictionarycoroutines.roomDB.dao.LastSearchedDao

@Database(entities = [LastSearched::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun lastSearchDao(): LastSearchedDao

}