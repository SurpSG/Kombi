package com.sgnatiuk.cartesian

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class CartesianProductSpliteratorTest {

    @Test
    fun `spliterator not should contain flag sized when cartesian product has combinations more then max long`() {
        val n = 1000
        val list = List(n) { it + 1 }
        val data = listOf(
                list, list,
                list, list,
                list, list,
                list
        )

        val spliterator = CartesianProductSpliterator(CartesianBuilder.cartesianProductOf(data))
        assertEquals(0, spliterator.characteristics() and Spliterator.SIZED)

    }
}