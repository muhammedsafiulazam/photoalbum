package com.muhammedsafiulazam.vinci

import java.util.concurrent.Future

/**
 * VinciTask is responsible for tracking task.
 * Specially it keeps reference of Future<*> which allows cancellation.
 */

class VinciTask(val url: String, private val future: Future<*>?) {
    fun cancel() {
        future?.cancel(true)
    }
}