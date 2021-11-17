package com.example.coroutineapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.coroutineapplication.data.People

@Dao
interface PeopleDao {
    @Insert(onConflict = REPLACE)
    fun upsert(people: People):Long

    @Query("SELECT * FROM _people_table WHERE _id=1")
    fun getPeople(): LiveData<People>
}