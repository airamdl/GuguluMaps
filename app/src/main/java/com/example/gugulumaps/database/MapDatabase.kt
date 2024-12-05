package com.example.gugulumaps.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.gugulumaps.dao.MarkerDao
import com.example.gugulumaps.entities.CoordinatesEntity
import com.example.gugulumaps.entities.MarkerEntity


@Database(
    entities = [MarkerEntity::class, CoordinatesEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : MapDatabase() {
    abstract fun MarkerDao(): MarkerDao
    //abstract fun ():

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "map_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}