package com.sgnatiuk.benchmark.cartesian

import com.sgnatiuk.cartesian.CartesianProduct
import com.sgnatiuk.cartesian.cartesianProductOf
import org.openjdk.jmh.annotations.*

open class CartesianListBenchmark {

    @State(Scope.Benchmark)
    open class SuperState {
        var result: List<Int>? = null
        lateinit var cartesianProduct: CartesianProduct<List<Int>>
        lateinit var iterator: Iterator<List<Int>>

        @Setup(Level.Trial)
        fun doSetup() {
            val size = 10
            val listOfLists = List(size){ i ->
                List(size - i){ it }
            }
            cartesianProduct = cartesianProductOf(listOfLists)
            iterator = cartesianProduct.iterator()
        }

        @TearDown(Level.Trial)
        fun doTearDown() {
        }
    }

    @Benchmark
    fun nextCartesianMap(state: SuperState) {
        state.result = state.iterator.next()
    }
}
