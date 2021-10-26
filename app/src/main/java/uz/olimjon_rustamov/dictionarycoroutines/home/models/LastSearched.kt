package uz.olimjon_rustamov.dictionarycoroutines.home.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class LastSearched {
    @PrimaryKey(autoGenerate = true)
    var id: Int ?=null

    @ColumnInfo
    var title: String? = null

    @ColumnInfo
    var description: String? = null

    @ColumnInfo
    var isSaved: Boolean? = null

    constructor()

    @Ignore
    constructor(title: String?, description: String?, isSaved: Boolean?) {
        this.title = title
        this.description = description
        this.isSaved = isSaved
    }
}