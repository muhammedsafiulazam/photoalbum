package com.muhammedsafiulazam.vinci

import java.util.concurrent.Future

class Task(val url: String, val future: Future<*>?) {
    fun cancel() {
        future?.cancel(true)
    }
}