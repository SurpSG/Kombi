package com.sgnatiuk.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

internal class RangeExtTest {

    @Test
    fun `verify range 1-2 rangeLength equals 2`() {
        assertEquals(2, (1L..2).rangeLength())
    }

    @Test
    fun `verify range 0-2 rangeLength equals 3`() {
        assertEquals(3, (0L..2).rangeLength())
    }

    @Test
    fun `verify range -1 to 2 rangeLength equals 4`() {
        assertEquals(4, (-1L..2).rangeLength())
    }

    @Test
    fun `verify range -1 to 0 rangeLength equals 2`() {
        assertEquals(2, (-1L..0).rangeLength())
    }

    @Test
    fun `verify range -2 to -1 rangeLength equals 2`() {
        assertEquals(2, (-2L..-1).rangeLength())
    }

    @Test
    fun `verify split range of 1-2 by 2 return 1-1 and 2-2`() {
        val ranges = setOf(1L..1, 2L..2)
        val split = (1L..2).split(2).toSet()
        assertEquals(ranges, split)
    }

    @Test
    fun `verify split range of 1-9 by 3 return three equals ranges`() {
        val ranges = setOf(
                1L..3,
                4L..6,
                7L..9
        )
        val split = (1L..9).split(3).toSet()
        assertEquals(ranges, split)
    }

    @Test
    fun `verify split range of 1-9 by 2 return three ranges`() {
        val ranges = setOf(
                1L..4,
                5L..9
        )
        val split = (1L..9).split(2).toSet()
        assertEquals(ranges, split)
    }
}