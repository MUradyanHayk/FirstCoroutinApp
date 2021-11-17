package com.example.coroutineapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coroutineapplication.data.People
import com.example.coroutineapplication.utils.Constants

@Database(entities = [People::class], version = Constants.DATABASE_VERSION)
abstract class PeopleDatabase : RoomDatabase() {
    abstract fun getPeopleDao(): PeopleDao

    companion object {
        @Volatile
        private var INSTANCE: PeopleDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context): PeopleDatabase? {
            synchronized(LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = createDatabase(context)
                }
            }
            return INSTANCE
        }

        private fun createDatabase(context: Context): PeopleDatabase {
            return Room.databaseBuilder(context.applicationContext, PeopleDatabase::class.java, "people_db").allowMainThreadQueries().build()
        }
    }
}