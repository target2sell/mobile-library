package com.target2sell.library.error

sealed class T2SError(message: String) : Throwable(message) {

    data class DefaultError(override val message: String) : T2SError(message)

    object EnablerCMPError : T2SError("Can't proceed call, EnablerCMP must be set to true")

    object UUIDMissingError : T2SError("Can't proceed call, UUID is empty or null")
}

fun Throwable.toT2SError() = T2SError.DefaultError(
    this.message ?: "Default Error"
)


