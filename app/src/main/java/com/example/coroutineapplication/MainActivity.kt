package com.example.coroutineapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.coroutineapplication.data.People
import com.example.coroutineapplication.database.PeopleDatabase
import com.example.coroutineapplication.repository.Repository
import com.example.coroutineapplication.utils.Download
import com.example.coroutineapplication.viewModel.MainViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val TAG = "CoroutineMainActivity"

    private var textView: TextView? = null

    var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.text_View_id)

        val db = PeopleDatabase(this)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel?.initializeViewModel(Repository(db))
        mainViewModel?.getData(this)

        observe()
    }

    fun observe() {
        mainViewModel?.data?.observe(this, this::observeData)
        mainViewModel?.getSavedPeople()?.observe(this, this::observeSavedData)
    }

    private fun observeData(response: Download<People>) {
        when (response) {
            is Download.Success -> {
                response.data.let { people ->
                    textView?.text = people?.name ?: ""
                }
            }
            is Download.Error -> {}
            is Download.Loading -> {}
        }
    }

    private fun observeSavedData(people: People?) {
        textView?.text = people?.name ?: ""
    }
}