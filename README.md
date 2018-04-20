# Kombi
[ ![Download](https://api.bintray.com/packages/sgnatiuk/kombi/kombi/images/download.svg) ](https://bintray.com/sgnatiuk/kombi/kombi/_latestVersion)
[![Build Status](https://travis-ci.org/SurpSG/Kombi.svg?branch=master)](https://travis-ci.org/SurpSG/Kombi)
[![Coverage Status](https://coveralls.io/repos/github/SurpSG/Kombi/badge.svg)](https://coveralls.io/github/SurpSG/Kombi)
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
      <version>2.0</version>
    </dependency>
</dependencies>
```
### Gradle
```groovy
repositories {  
   jcenter()  
}

dependencies {
    compile 'com.sgnatiuk:kombi:2.0'
}
```
## Combinations
### Usage for lists
```java
    List<String> inputData = Arrays.asList("A", "B", "C");
    
    Combination<List<String>> combinations = CombinationsBuilder.combinationsOf(inputData);
    combinations.forEach(System.out::println);
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
