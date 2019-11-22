package com.muhammedsafiulazam.photoalbum.event

import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.core.BaseUnitTest
import com.muhammedsafiulazam.photoalbum.network.model.Error
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.asserter

class EventManagerUnitTest : BaseUnitTest() {

    private val DUMMY_EVENT_TYPE: String = "DUMMY_EVENT_TYPE"
    private val DUMMY_EVENT_DATA: String = "DUMMY_EVENT_DATA"
    private val DUMMY_EVENT_ERROR: Error = Error(0, "")

    @Test
    fun exchangeEvent() = runBlocking {
        var e: Event? = null

        val eventManager: IEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        eventManager.subscribe(callback = { event: Event -> Unit
            e = event
        })

        eventManager.send(createDummyEvent())

        delay(DELAY_MINIMUM)
        asserter.assertTrue("", e != null && e!!.data != null && e!!.error != null)
    }

    private fun createDummyEvent() : Event {
        return Event(DUMMY_EVENT_TYPE, DUMMY_EVENT_DATA, DUMMY_EVENT_ERROR)
    }
}