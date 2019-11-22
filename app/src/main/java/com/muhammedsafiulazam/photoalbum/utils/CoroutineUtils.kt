package com.muhammedsafiulazam.photoalbum.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object CoroutineUtils {
    var DISPATCHER_MAIN: CoroutineDispatcher = Dispatchers.Main
    var DISPATCHER_IO: CoroutineDispatcher = Dispatchers.IO
}