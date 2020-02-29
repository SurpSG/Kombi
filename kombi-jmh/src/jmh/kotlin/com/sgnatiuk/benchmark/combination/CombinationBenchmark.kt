package com.sgnatiuk.benchmark.combination

import com.sgnatiuk.combination.CombinationsBuilder.combinationsOf
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(2)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
open class CombinationBenchmark {

    @Param("11", "19")
    var itemsQuantity: Int = 0

    lateinit var list: List<Int>
    lateinit var map: Map<Int, String>

    @Setup(Level.Trial)
    fun doSetup() {
        list = List(itemsQuantity){ it }
        map = (1..itemsQuantity).map { it to it.toString() }.toMap()
        println("\n=================================================")
        println("itemsQuantity=$itemsQuantity")
        println("combinationsQuantity=${combinationsOf(list).combinationsNumber()}")
        println("=================================================\n")
    }

    @Benchmark
    fun Kombi_combinations_list(blackhole: Blackhole) {
        for (nextCombination in combinationsOf(list)) {
            blackhole.consume(nextCombination)
        }
    }

    @Benchmark
    fun Kombi_combinations_map(blackhole: Blackhole) {
        for (nextCombination in combinationsOf(map)) {
            blackhole.consume(nextCombination)
        }
    }
}
