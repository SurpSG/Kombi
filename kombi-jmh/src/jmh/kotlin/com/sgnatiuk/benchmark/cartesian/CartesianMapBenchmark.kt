package com.sgnatiuk.benchmark.cartesian

import com.sgnatiuk.cartesian.CartesianBuilder.cartesianProductOf
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
open class CartesianMapBenchmark {

    @Param("7")
    var itemsQuantity: Int = 0

    lateinit var mapOf: Map<Int, List<Int>>

    @Setup(Level.Trial)
    fun doSetup() {
         mapOf = (1..itemsQuantity).map { index ->
            index to List(index) { it }
        }.toMap()
    }

    @Benchmark
    fun cartesianProductMap(blackhole: Blackhole) {
        for (map in cartesianProductOf(mapOf, false)) {
            blackhole.consume(map)
        }
    }

    @Benchmark
    fun cartesianProductMapKeepOrder(blackhole: Blackhole) {
        for (map in cartesianProductOf(mapOf, true)) {
            blackhole.consume(map)
        }
    }
}
