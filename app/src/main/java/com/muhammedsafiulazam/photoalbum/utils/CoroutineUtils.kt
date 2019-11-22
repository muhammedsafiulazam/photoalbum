package com.muhammedsafiulazam.photoalbum.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object CoroutineUtils {
    val DISPATCHER_MAIN: CoroutineDispatcher = Dispatchers.Main
    val DISPATCHER_IO: CoroutineDispatcher = Dispatchers.IO
}