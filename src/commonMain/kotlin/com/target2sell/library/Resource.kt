package com.target2sell.library

import com.target2sell.library.error.T2SError

sealed class Resource<T> {
    data class Success<T>(val result: T) : Resource<T>()
    data class Error<T>(val error: T2SError) : Resource<T>()
}



