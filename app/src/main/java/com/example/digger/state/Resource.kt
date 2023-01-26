package com.example.digger.state


sealed class Resource<T>(val result: T? = null, val error: Throwable? = null) {
    class Success<T>(result: T) : Resource<T>(result)
    class Loading<T>(result: T? = null) : Resource<T>(result)
    class Error<T>(throwable: Throwable, result: T? = null) : Resource<T>(result, throwable)
}
