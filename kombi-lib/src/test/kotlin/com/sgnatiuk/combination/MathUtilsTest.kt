package com.sgnatiuk.combination

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class MathUtilsTest {

    @ParameterizedTest
    @CsvSource(
        value = [
            "5, 1, 5",
            "5, 2, 10",
            "5, 3, 10",
            "5, 4, 5",
            "5, 5, 1",
            "10, 2, 45",
            "30, 20, 30045015",
            "50, 5, 2118760",
            "50, 20, 47129212243960"
        ]
    )
    fun `binomial must return binomial coefficient`(n: Int, k: Int, expectedValue: Long) {
        assertEquals(expectedValue, MathUtils.binomial(n, k))
    }
}
