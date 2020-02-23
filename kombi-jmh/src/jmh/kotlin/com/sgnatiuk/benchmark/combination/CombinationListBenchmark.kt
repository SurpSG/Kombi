package com.sgnatiuk.benchmark.combination

import com.sgnatiuk.combination.Combination
import com.sgnatiuk.combination.CombinationsBuilder.combinationsOf
import org.openjdk.jmh.annotations.*

open class CombinationListBenchmark {

    @State(Scope.Benchmark)
    open class SuperState {
        var result: List<Int>? = null
        var list = List(30){ it }
        lateinit var cartesianProduct: Combination<List<Int>>
        lateinit var iterator: Iterator<List<Int>>

        @Setup(Level.Trial)
        fun doSetup() {
            cartesianProduct = combinationsOf(list)
            iterator = cartesianProduct.iterator()
        }

        @TearDown(Level.Trial)
        fun doTearDown() {
        }
    }

    @Benchmark
    fun nextCombinationList(state: SuperState) {
        state.result = state.iterator.next()
    }
}
