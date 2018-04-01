# Kombi

## Cartesian product
###Usage for lists
```java
    List<List<Integer>> data = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5),
            Arrays.asList(6)
    );
    
    CartesianProduct<List<Integer>> cartesianProduct = CartesianBuilder.CartesianProduct(data, false);
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
    
    CartesianProduct<Map<Integer, Integer>> cartesianProduct = CartesianBuilder.CartesianProduct(data, true);
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