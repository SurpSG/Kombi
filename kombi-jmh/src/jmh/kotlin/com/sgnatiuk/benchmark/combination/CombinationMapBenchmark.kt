package com.sgnatiuk.benchmark.combination

import com.sgnatiuk.combination.Combination
import com.sgnatiuk.combination.combinationsOf
import org.openjdk.jmh.annotations.*

open class CombinationMapBenchmark {

    @State(Scope.Benchmark)
    open class SuperState {
        var result: Map<Int, String>? = null
        lateinit var cartesianProduct: Combination<Map<Int, String>>
        lateinit var iterator: Iterator<Map<Int, String>>

        @Setup(Level.Trial)
        fun doSetup() {
            cartesianProduct = combinationsOf(
                    (1..30).map {
                        it to it.toString()
                    }.toMap()
            )
            iterator = cartesianProduct.iterator()
        }

        @TearDown(Level.Trial)
        fun doTearDown() {
        }
    }

    @Benchmark
    fun nextCombinationMap(state: SuperState) {
        state.result = state.iterator.next()
    }
}
