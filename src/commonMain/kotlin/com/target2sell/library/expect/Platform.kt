package com.target2sell.library.expect

expect class Platform() {
    fun device(): String
    fun osVersion(): String
    fun platform(): String
    fun generateUUID(): String
}