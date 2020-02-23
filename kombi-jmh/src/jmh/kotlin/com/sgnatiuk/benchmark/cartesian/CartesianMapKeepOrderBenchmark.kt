package com.sgnatiuk.benchmark.cartesian

import com.sgnatiuk.cartesian.CartesianBuilder.cartesianProductOf
import com.sgnatiuk.cartesian.CartesianProduct
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

open class CartesianMapKeepOrderBenchmark {

    @State(Scope.Benchmark)
    open class SuperState {
        var result: Map<Int, String>? = null
        lateinit var cartesianProduct: CartesianProduct<Map<Int, String>>
        lateinit var iterator: Iterator<Map<Int, String>>

        @Setup(Level.Trial)
        fun doSetup() {
            val mapOf = (1..10).reversed().map { index ->
                index to List(index){ it.toString() }
            }.toMap()
            cartesianProduct = cartesianProductOf(mapOf, true)
            iterator = cartesianProduct.iterator()
        }

        @TearDown(Level.Trial)
        fun doTearDown() {
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    fun nextCartesianList(state: SuperState) {
        state.result = state.iterator.next()
    }
}
