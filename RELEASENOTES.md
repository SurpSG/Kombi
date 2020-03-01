v3.0.1
* 'Cartesian product' from Collection<Collection<T>> performance tuning.(~40% speed-up)

v3.0.0
* The library is fully migrated from Kotlin to Java to avoid adding of Kotlin runtime dependency to Java-only projects.
* Small performance fixes in the generation of the cartesian product.

v2.2
* Provided stream support(java.util.stream.Stream)

v2.1
* Provided split functionality allowing to split the cartesian product or combinations generation into equals chunks, so cartesian product can be generated in few threads independently.  
