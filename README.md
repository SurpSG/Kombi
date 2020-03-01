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
      <version>3.0.1</version>
    </dependency>
</dependencies>
```
### Gradle
```groovy
repositories {  
   jcenter()  
}

dependencies {
    compile 'com.sgnatiuk:kombi:3.0.1'
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
Measured time of generation of combination/cartesian product items (microseconds to generate all items)

Feel free to run benchmarks by yourself:
```
./gradlew clean kombi-jmh:jmh
```


Benchmark results(less is better):
```
Ubuntu 18.04.4 LTS
Intel® Core™ i7-6500U CPU @ 2.50GHz × 4
# JMH version: 1.22
# VM version: JDK 1.8.0_242, OpenJDK 64-Bit Server VM, 25.242-b08
# VM invoker: /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java
# VM options: -Xms512m -Xmx1g
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op

Benchmark                                                                         (itemsQuantity)  Mode  Cnt        Score        Error  Units
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Lists                             3  avgt   10        0.396 ±      0.002  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Lists                             5  avgt   10        8.592 ±      0.190  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Lists                             7  avgt   10      507.613 ±      3.286  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Lists                            11  avgt   10  6047357.993 ±  11218.642  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists                             3  avgt   10        0.363 ±      0.005  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists                             5  avgt   10        6.838 ±      0.176  us/op      
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists                             7  avgt   10      374.914 ±     69.084  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists                            11  avgt   10  3360446.209 ±  45311.037  us/op

c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Sets                              3  avgt   10        0.815 ±      0.066  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Sets                              5  avgt   10       15.611 ±      0.578  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Sets                              7  avgt   10      645.309 ±     43.153  us/op
c.s.b.cartesian.CartesianListBenchmark.Guava_cartesianProduct_Sets                             11  avgt   10  7492806.803 ± 137744.113  us/op

c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists_keepingOrder                3  avgt   10        0.449 ±      0.059  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists_keepingOrder                5  avgt   10        8.432 ±      0.260  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists_keepingOrder                7  avgt   10      407.532 ±      1.454  us/op
c.s.b.cartesian.CartesianListBenchmark.Kombi_cartesianProduct_Lists_keepingOrder               11  avgt   10  4061078.368 ±  42057.603  us/op

c.s.b.cartesian.CartesianMapBenchmark.Kombi_cartesianProduct_Maps                               5  avgt   10       18.971 ±      1.902  us/op
c.s.b.cartesian.CartesianMapBenchmark.Kombi_cartesianProduct_Maps                               7  avgt   10     1050.295 ±     20.054  us/op
c.s.b.cartesian.CartesianMapBenchmark.Kombi_cartesianProduct_Maps_keepingOrder                  5  avgt   10       19.619 ±      2.603  us/op
c.s.b.cartesian.CartesianMapBenchmark.Kombi_cartesianProduct_Maps_keepingOrder                  7  avgt   10     1212.077 ±    139.650  us/op

c.s.b.combination.CombinationBenchmark.Kombi_combinations_list                                 11  avgt   10      216.704 ±      3.633  us/op
c.s.b.combination.CombinationBenchmark.Kombi_combinations_list                                 19  avgt   10    77641.630 ±   1561.054  us/op

c.s.b.combination.CombinationBenchmark.Kombi_combinations_map                                  11  avgt   10      467.513 ±      2.014  us/op
c.s.b.combination.CombinationBenchmark.Kombi_combinations_map                                  19  avgt   10   170390.506 ±   3108.922  us/op

```
Comparing performance with Guava(microseconds per generation, less is better):

![](kombi-jmh/charts/items_39916800.jpg =250x)

