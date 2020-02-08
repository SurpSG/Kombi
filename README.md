# Kombi
[ ![Download](https://api.bintray.com/packages/sgnatiuk/kombi/kombi/images/download.svg) ](https://bintray.com/sgnatiuk/kombi/kombi/_latestVersion)
[![Build Status](https://travis-ci.org/SurpSG/Kombi.svg?branch=master)](https://travis-ci.org/SurpSG/Kombi)
[![Coverage Status](https://coveralls.io/repos/github/SurpSG/Kombi/badge.svg)](https://coveralls.io/github/SurpSG/Kombi)

**Kombi** is library to generate
* all possible [combinations](https://en.wikipedia.org/wiki/Combination) from given set of items
* [cartesian product](https://en.wikipedia.org/wiki/Cartesian_product) from given sets of items

All items computations are lazy that provides a small memory footprint. 

To get even better throughput(see [Benchmarking](#benchmarking) section), computations can be easily parallelized by splitting to equal independent subsets(see [Parallel computation](#parallel-computation) section).    

## Installation
### Maven 
```xml
<repositories>
    <repository>
        <id>jcenter</id>
        <url>https://jcenter.bintray.com/</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
      <groupId>com.sgnatiuk</groupId>
      <artifactId>kombi</artifactId>
      <version>2.2</version>
    </dependency>
</dependencies>
```
### Gradle
```groovy
repositories {  
   jcenter()  
}

dependencies {
    compile 'com.sgnatiuk:kombi:2.2'
}
```
## Combinations
`Combination<Collection<T>` is an iterable object, so you can use it in 'for-each' loop.
To get items lazily you can use the iterator by calling `iterator()` function or you can get them as the stream by calling `stream()` function. 
### Usage for lists
#### Java
```java
    List<String> inputData = Arrays.asList("A", "B", "C");
    
    Combination<List<String>> combinations = CombinationsBuilder.combinationsOf(inputData);
    combinations.stream().forEach(System.out::println);
```
#### Kotlin
```kotlin
    import com.sgnatiuk.combination.combinationsOf
    
    val data = listOf("A", "B", "C")
    combinationsOf(data).forEach(::println)
    
```
The output:
```
    [A]
    [B]
    [A, B]
    [C]
    [A, C]
    [B, C]
    [A, B, C]
```

### Usage for maps
#### Java
```java
    Map<Integer, String> data = new HashMap<>();
    data.put(1, "1");
    data.put(2, "2");
    data.put(3, "3");
    
    Combination<Map<Integer, String>> cartesianProduct = CombinationsBuilder.combinationsOf(data);
    cartesianProduct.forEach(System.out::println);
```
#### Kotlin
```kotlin
    import com.sgnatiuk.combination.combinationsOf
    
    val data = mapOf(
            1 to "1",
            2 to "2",
            3 to "3"
    )
    combinationsOf(data).forEach(::println)
```
The output:
```
    {1=1}
    {2=2}
    {1=1, 2=2}
    {3=3}
    {1=1, 3=3}
    {2=2, 3=3}
    {1=1, 2=2, 3=3}
```

## Cartesian product
`CartesianProduct<Collection<T>` is an iterable object, so you can use it in 'for-each' loop.
To get items lazily you can use the iterator by calling `iterator()` function or you can get them as the stream by calling `stream()` function.
### Usage for lists
#### Java
```java
    List<List<Integer>> data = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5),
            Arrays.asList(6)
    );
    
    CartesianProduct<List<Integer>> cartesianProduct = CartesianBuilder.cartesianProductOf(data, false);
    cartesianProduct.forEach(System.out::println);
```
#### Kotlin
```kotlin
    import com.sgnatiuk.combination.combinationsOf

    val data = listOf(
            listOf(1, 2, 3),
            listOf(4, 5),
            listOf(6)
    )
    cartesianProductOf(data).forEach(::println)
```
The output:
```
    [1, 4, 6]
    [1, 5, 6]
    [2, 4, 6]
    [2, 5, 6]
    [3, 4, 6]
    [3, 5, 6]
```

### Usage for maps
#### Java
```java
    Map<Integer, List<Integer>> data = new HashMap<>();
    data.put(1, Arrays.asList(1, 2, 3));
    data.put(2, Arrays.asList(4, 5));
    data.put(3, Arrays.asList(6));
    
    CartesianProduct<Map<Integer, Integer>> cartesianProduct = CartesianBuilder.cartesianProductOf(data, true);
    cartesianProduct.forEach(System.out::println);
```
#### Kotlin
```kotlin
    import com.sgnatiuk.cartesian.cartesianProductOf

    val data = mapOf(
            1 to listOf(1, 2, 3),
            2 to listOf(4, 5),
            3 to listOf(6)
    )
    cartesianProductOf(data).forEach(::println)
```
The output:
```
    {1=1, 2=4, 3=6}
    {1=1, 2=5, 3=6}
    {1=2, 2=4, 3=6}
    {1=2, 2=5, 3=6}
    {1=3, 2=4, 3=6}
    {1=3, 2=5, 3=6}
```

## Parallel computation

```java
        // generate data for combinations
        List<Integer> data = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        Combination<List<Integer>> combinations = CombinationsBuilder.combinationsOf(data);

        int threads = 4;
        ExecutorService threadPool = Executors.newFixedThreadPool(threads);
        AtomicInteger count = new AtomicInteger();

        // split to equal parts to compute parallelly
        List<Combination<List<Integer>>> subSets = combinations.split( threads );
        for (Combination<List<Integer>> subSet : subSets) {
            threadPool.submit(() -> {
                for (List<Integer> combination : subSet) {
                    // uncomment line below to print computed combination
                    // System.out.println(combination);
                    count.incrementAndGet();
                }
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println("Combinations: " + count + ", expectedCombinations: "+combinations.getCombinationsNumber());
```

## Benchmarking
Measured throughput of generation of combination/cartesian product item (generated items per second)

Benchmark results:
```
Ubuntu 16.04 TLS
Intel® Core™ i5-2500 CPU @ 3.30GHz × 4
JMH version: 1.19
VM version: JDK 1.8.0_144, VM 25.144-b01
VM invoker: /usr/lib/jvm/java-8-oracle/jre/bin/java
VM options: -Xms4g -Xmx4g
Warmup: 10 iterations, 1 s each
Measurement: 10 iterations, 1 s each
Timeout: 10 min per iteration
Threads: 1 thread, will synchronize iterations
Benchmark mode: Throughput, ops/time

Benchmark                                                          Mode  Cnt         Score        Error  Units
c.s.b.cartesian.CartesianListBenchmark.nextCartesianMap           thrpt   10  11145467.663 ± 154455.324  ops/s
c.s.b.cartesian.CartesianListKeepOrderBenchmark.nextCartesianMap  thrpt   10  11235119.916 ±  68636.744  ops/s
c.s.b.cartesian.CartesianMapBenchmark.nextCartesianList           thrpt   10   3543765.382 ±  19372.030  ops/s
c.s.b.cartesian.CartesianMapKeepOrderBenchmark.nextCartesianList  thrpt   10   3532589.782 ±  20227.118  ops/s
c.s.b.combination.CombinationListBenchmark.nextCombinationList    thrpt   10   5553247.849 ± 122736.874  ops/s
c.s.b.combination.CombinationMapBenchmark.nextCombinationMap      thrpt   10   2184342.995 ± 188794.436  ops/s
```

Feel free to run benchmarks by yourself:
```
./gradlew clean kombi-jmh:jmh
```
