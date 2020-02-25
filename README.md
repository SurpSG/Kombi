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
      <version>3.0.0</version>
    </dependency>
</dependencies>
```
### Gradle
```groovy
repositories {  
   jcenter()  
}

dependencies {
    compile 'com.sgnatiuk:kombi:3.0.0'
}
```
## Combinations
`Combination<Collection<T>` is an iterable object, so you can use it in 'for-each' loop.
To get items lazily you can use the iterator by calling `iterator()` function or you can get them as the stream by calling `stream()` function. 
### Usage for lists
```java
    List<String> inputData = Arrays.asList("A", "B", "C");
    
    Combination<List<String>> combinations = CombinationsBuilder.combinationsOf(inputData);
    combinations.stream().forEach(System.out::println);
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
```java
    Map<Integer, String> data = new HashMap<>();
    data.put(1, "1");
    data.put(2, "2");
    data.put(3, "3");
    
    Combination<Map<Integer, String>> cartesianProduct = CombinationsBuilder.combinationsOf(data);
    cartesianProduct.forEach(System.out::println);
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
```java
    List<List<Integer>> data = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5),
            Arrays.asList(6)
    );
    
    CartesianProduct<List<Integer>> cartesianProduct = CartesianBuilder.cartesianProductOf(data, false);
    cartesianProduct.forEach(System.out::println);
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
```java
    Map<Integer, List<Integer>> data = new HashMap<>();
    data.put(1, Arrays.asList(1, 2, 3));
    data.put(2, Arrays.asList(4, 5));
    data.put(3, Arrays.asList(6));
    
    CartesianProduct<Map<Integer, Integer>> cartesianProduct = CartesianBuilder.cartesianProductOf(data, true);
    cartesianProduct.forEach(System.out::println);
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
### Performance tip
There is an overloaded builder method `CartesianBuilder.cartesianProductOf(..., boolean keepOrder)` that accepts boolean parameter `keepOrder`. By default the parameter is `false` that provides a little bit better performance. See (see [Benchmarking](#benchmarking) section) to compare.

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
Ubuntu 18.04.4 LTS
Intel® Core™ i7-6500U CPU @ 2.50GHz × 4
JMH version: 1.19
VM version: JDK 1.8.0_242, VM 25.242-b08
VM invoker: /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java
VM options: -Xms4g -Xmx4g
Warmup: 10 iterations, 1 s each
Measurement: 10 iterations, 1 s each
Timeout: 10 min per iteration
Threads: 1 thread, will synchronize iterations
Benchmark mode: Average time, time/op

Benchmark                                                                         (itemsQuantity)  Mode  Cnt        Score        Error  Units
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Lists                             3  avgt   10        0.416 ±      0.004  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Lists                             5  avgt   10        8.983 ±      0.045  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Lists                             7  avgt   10      525.439 ±      2.649  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Lists                            11  avgt   10  6402100.731 ± 208391.654  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Sets                              3  avgt   10        0.835 ±      0.010  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Sets                              5  avgt   10       16.800 ±      0.203  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Sets                              7  avgt   10      745.600 ±     13.021  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Sets                             11  avgt   10  8956251.389 ±  54373.550  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists                             3  avgt   10        0.525 ±      0.002  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists                             5  avgt   10       10.777 ±      0.165  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists                             7  avgt   10      535.627 ±     44.617  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists                            11  avgt   10  5461936.892 ±  20583.561  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists_keepingOrder                3  avgt   10        0.608 ±      0.007  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists_keepingOrder                5  avgt   10       12.682 ±      1.236  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists_keepingOrder                7  avgt   10      578.047 ±      3.468  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists_keepingOrder               11  avgt   10  6385427.416 ± 271354.148  us/op
c.s.b.cartesian.CartesianMapBenchmark.Kombi_cartesianProduct_Maps                               7  avgt   10     1019.071 ±     15.560  us/op
c.s.b.cartesian.CartesianMapBenchmark.Kombi_cartesianProduct_Maps_keepingOrder                  7  avgt   10     1086.156 ±     10.034  us/op
c.s.b.combination.CombinationBenchmark.Kombi_combinations_list                                 11  avgt   10      220.303 ±      1.957  us/op
c.s.b.combination.CombinationBenchmark.Kombi_combinations_list                                 19  avgt   10    78645.589 ±   1509.309  us/op
c.s.b.combination.CombinationBenchmark.Kombi_combinations_map                                  11  avgt   10      463.854 ±      1.873  us/op
c.s.b.combination.CombinationBenchmark.Kombi_combinations_map                                  19  avgt   10   169522.011 ±    695.623  us/op

```

Feel free to run benchmarks by yourself:
```
./gradlew clean kombi-jmh:jmh
```
