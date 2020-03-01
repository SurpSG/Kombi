package com.sgnatiuk.benchmark.cartesian

import com.sgnatiuk.cartesian.CartesianBuilder.cartesianProductOf
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(2)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
open class CartesianMapBenchmark {

    @Param("5", "7")
    var itemsQuantity: Int = 0

    lateinit var mapOf: Map<Int, List<Int>>

    @Setup(Level.Trial)
    fun doSetup() {
         mapOf = (1..itemsQuantity).map { index ->
            index to List(index) { it }
        }.toMap()
    }

    @Benchmark
    fun Kombi_cartesianProduct_Maps(blackhole: Blackhole) {
        for (map in cartesianProductOf(mapOf, false)) {
            blackhole.consume(map)
        }
    }

    @Benchmark
    fun Kombi_cartesianProduct_Maps_keepingOrder(blackhole: Blackhole) {
        for (map in cartesianProductOf(mapOf, true)) {
            blackhole.consume(map)
        }
    }
}
