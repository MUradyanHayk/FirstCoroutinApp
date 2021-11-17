package com.example.coroutineapplication.utils

sealed class Download<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Download<T>(data)
    class Error<T>(message: String, data: T? = null) : Download<T>(data, message)
    class Loading<T>() : Download<T>()
}
