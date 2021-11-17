package com.example.coroutineapplication.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineapplication.data.People
import com.example.coroutineapplication.network.NetworkManager
import com.example.coroutineapplication.repository.Repository
import com.example.coroutineapplication.utils.Download
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {
    var data: MutableLiveData<Download<People>> = MutableLiveData()
    private var repository: Repository? = null

    fun initializeViewModel(repository: Repository) {
        this.repository = repository
    }

    fun getData(context: Context): Job {
        return viewModelScope.launch {
            if (NetworkManager.hasInternetConnection(context)) {
                safePeopleCall()
            }
        }
    }

    fun getSavedPeople(): LiveData<People>? {
        return repository?.getSavedData()
    }
    private suspend fun safePeopleCall() {
        this.data.postValue(Download.Loading())
        val response = repository?.getData()
        if (response != null) {
            data.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<People>): Download<People> {
        if (response.isSuccessful) {
            response.body()?.let {
                savePeople(it)
                return Download.Success(it)
            }
        }

        return Download.Error(response.message())
    }

    fun savePeople(people: People) {
        viewModelScope.launch {
            repository?.upsert(people)
        }
    }
}