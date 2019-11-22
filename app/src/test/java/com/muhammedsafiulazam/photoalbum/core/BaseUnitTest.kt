package com.muhammedsafiulazam.photoalbum.core

import com.muhammedsafiulazam.photoalbum.utils.UnitTestUtils
import org.junit.After
import org.junit.Before

open class BaseUnitTest {

    val DELAY_MINIMUM: Long = 1000
    val DELAY_AVERAGE: Long = 2500
    val DELAY_MAXIMUM: Long = 5000

    @Before
    fun beforeTest() {
        UnitTestUtils.setup()
    }

    @After
    fun afterTest() {

    }
}