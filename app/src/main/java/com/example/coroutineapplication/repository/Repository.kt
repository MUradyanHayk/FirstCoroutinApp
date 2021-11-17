package com.example.coroutineapplication.repository

import androidx.lifecycle.LiveData
import com.example.coroutineapplication.data.People
import com.example.coroutineapplication.database.PeopleDatabase
import com.example.coroutineapplication.network.api.RetrofitInstance
import retrofit2.Response

class Repository(val db: PeopleDatabase?) {
    suspend fun getData(): Response<People> {
        return RetrofitInstance.api.getData()
    }

    fun upsert(people: People):Long? {
        return db?.getPeopleDao()?.upsert(people)
    }

    fun getSavedData(): LiveData<People>? {
        return db?.getPeopleDao()?.getPeople()
    }
}