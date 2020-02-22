package com.sgnatiuk;

import java.util.List;

public interface Splittable<T extends Splittable<T>> {
    List<T> split( int n);
}