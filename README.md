# Kombi[ ![Download](https://api.bintray.com/packages/sgnatiuk/kombi/kombi/images/download.svg) ](https://bintray.com/sgnatiuk/kombi/kombi/_latestVersion)
## Installation
### Maven 
```xml
<repositories>
    <repository>
        <id>sgnatiuk</id>
        <url>https://dl.bintray.com/sgnatiuk/kombi</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
      <groupId>com.sgnatiuk</groupId>
      <artifactId>kombi</artifactId>
      <version>2.0</version>
      <type>pom</type>
    </dependency>
</dependencies>
```
### Gradle
```groovy
repositories {
    maven {
        url 'https://dl.bintray.com/sgnatiuk/kombi'
    }
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
