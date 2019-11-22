package com.muhammedsafiulazam.photoalbum.core

import com.muhammedsafiulazam.photoalbum.utils.UnitTestUtils
import org.junit.After
import org.junit.Before

open class BaseUnitTest {

    @Before
    fun beforeTest() {
        UnitTestUtils.setup()
    }

    @After
    fun afterTest() {

    }
}